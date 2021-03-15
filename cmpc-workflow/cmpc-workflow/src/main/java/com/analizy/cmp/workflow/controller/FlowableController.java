package com.analizy.cmp.workflow.controller;


import cn.hutool.core.map.MapUtil;
import com.analizy.cmp.core.resp.GetCmpResponse;
import com.analizy.cmp.workflow.util.FlowableImgUtil;
import org.flowable.engine.*;
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

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wangjian030
 * @since 2021-01-13
 */
@Controller
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
    @RequestMapping(value = "/add")
    public GetCmpResponse<String> addExpense(String userId, Integer money, String descption) {
        //启动流程
        HashMap<String, Object> map = new HashMap<>();
        map.put("taskUser", userId);
        map.put("money", money);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Expense", map);
        return new GetCmpResponse<>(null);
    }

    /**
     * 获取审批管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public GetCmpResponse<Map> list(String userId) {
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).orderByTaskCreateTime().desc().list();
        Map<String,String> map = MapUtil.newHashMap();
        for (Task task : tasks) {
            map.put(task.getId(),task.toString());
        }
        return new GetCmpResponse<>(map);
    }

    /**
     * 批准
     *
     * @param taskId 任务ID
     */
    @RequestMapping(value = "/apply")
    @ResponseBody
    public String apply(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new RuntimeException("流程不存在");
        }
        //通过审核
        HashMap<String, Object> map = new HashMap<>();
        map.put("outcome", "通过");
        taskService.complete(taskId, map);
        return "processed ok!";
    }

    /**
     * 生成流程图
     *
     * @param processId 任务ID
     */
    @GetMapping("/{processId}")
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

