package com.analizy.cmp.workflow.util;

import com.analizy.cmp.core.constant.FileType;
import com.analizy.cmp.core.excp.CmpException;
import com.analizy.cmp.workflow.config.FlowableProcessDiagramGenerator;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.impl.util.IoUtil;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.impl.DefaultProcessDiagramCanvas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: wangjian
 * @date: 2021/01/13 14:56
 */
@Service
public class FlowableImgUtil {

    private static final String INPUT_STREAM_NAME = "image inputStream name";

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RepositoryService repositoryService;

    private static FlowableProcessDiagramGenerator flowableProcessDiagramGenerator;

    static{
        flowableProcessDiagramGenerator = new FlowableProcessDiagramGenerator();
    }

    public byte[] generatorImage(String processInstanceId) throws Exception{
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().finished()
                .processInstanceId(processInstanceId).singleResult();

        BpmnModel bpmnModel;
        List<String> activeActivityIds;

        if(historicProcessInstance!=null){
            bpmnModel = repositoryService.getBpmnModel(historicProcessInstance.getProcessDefinitionId());
            activeActivityIds = new ArrayList<>();
        }else{
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            if(processInstance==null){
                throw new CmpException();
            }
            bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
            activeActivityIds = runtimeService.getActiveActivityIds(processInstanceId);
        }

        List<String> historyActiveActivityIds = new ArrayList<>();

        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
                .processDefinitionId(processInstanceId).list();

        list.forEach(historicActivityInstance -> historyActiveActivityIds.add(historicActivityInstance.getActivityId()));

        DefaultProcessDiagramCanvas flowableProcessDiagramCanvas = flowableProcessDiagramGenerator.generateProcessDiagram(bpmnModel,
                FileType.IMAGE_PNG,historyActiveActivityIds,activeActivityIds,null,null);

        InputStream inputStream = flowableProcessDiagramCanvas.generateImage(FileType.IMAGE_PNG);
        return IoUtil.readInputStream(inputStream,INPUT_STREAM_NAME);
    }
}
