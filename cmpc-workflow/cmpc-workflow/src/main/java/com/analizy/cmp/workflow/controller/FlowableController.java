package com.analizy.cmp.workflow.controller;


import cn.hutool.core.map.MapUtil;
import com.analizy.cmp.core.error.CheckErrorCode;
import com.analizy.cmp.core.excp.CmpException;
import com.analizy.cmp.core.resp.GetCmpResponse;
import com.analizy.cmp.workflow.util.FlowableImgUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.*;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wangjian030
 * @since 2021-01-13
 */
@Slf4j
@Controller
@Api(value="工作流",tags = "工作流flowable")
@RequestMapping("/api/v1/flowable")
public class FlowableController {

    @Autowired
    private FlowableImgUtil flowableImgUtil;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RepositoryService repositoryService;

    /**
     * 添加报销
     *
     * @param userId    用户Id
     * @param money     报销金额
     * @param descption 描述
     */
    @ResponseBody
    @GetMapping(value = "/add")
    @ApiOperation(value = "addExpense")
    public GetCmpResponse<ProcessInstance> addExpense(String userId, Integer money, String descption) {
        //启动流程
        HashMap<String, Object> map = new HashMap<>();
        map.put("taskUser", userId);
        map.put("money", money);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Expense", map);
        log.info("{}==={}",processInstance.getBusinessKey(),processInstance.getProcessDefinitionId());
        return new GetCmpResponse(processInstance.getProcessInstanceId());
    }

    /**
     * 获取审批管理列表
     */
    @GetMapping(value = "/list")
    @ResponseBody
    @ApiOperation(value = "list")
    public GetCmpResponse<List> list(String userId) {
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).orderByTaskCreateTime().desc().list();
        return new GetCmpResponse<>(tasks.stream().map(Task::toString).collect(Collectors.toList()));
    }

    /**
     * 批准
     *
     * @param taskId 任务ID
     */
    @GetMapping(value = "/apply")
    @ResponseBody
    @ApiOperation(value = "apply")
    public GetCmpResponse apply(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new CmpException(CheckErrorCode.FLOWABLE_NOT_EXISTS);
        }
        //通过审核
        HashMap<String, Object> map = new HashMap<>();
        map.put("outcome", "通过");
        taskService.complete(taskId, map);
        return new GetCmpResponse();
    }

    /**
     * 生成流程图
     *
     * @param processId 任务ID
     */
    @GetMapping("/{processId}")
    @ApiOperation(value = "genProcessDiagram")
    public void genProcessDiagram(HttpServletResponse response, @PathVariable("processId") String processId) throws Exception {
        byte[] bytes = flowableImgUtil.generatorImage(processId);
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            response.setContentType("image/png");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.addHeader("Content-Length", String.valueOf(bytes.length));
            out.write(bytes);
            out.flush();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}

