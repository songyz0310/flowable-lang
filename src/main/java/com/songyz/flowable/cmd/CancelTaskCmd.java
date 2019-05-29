package com.songyz.flowable.cmd;

import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.Process;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityManager;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.engine.impl.util.ProcessDefinitionUtil;
import org.flowable.identitylink.service.IdentityLinkService;
import org.flowable.task.service.HistoricTaskService;
import org.flowable.task.service.impl.persistence.entity.HistoricTaskInstanceEntity;
import org.flowable.task.service.impl.persistence.entity.TaskEntityManager;

/**
 * 撤销当前步骤，回退到制定步骤
 * 
 * @author songyz CreateTime: 2018-07-13 12:58
 */
public class CancelTaskCmd implements Command<Void> {

    protected String currentTaskId;

    protected String targetTaskId;

    public CancelTaskCmd(String currentTaskId, String targetTaskId) {
        this.currentTaskId = currentTaskId;
        this.targetTaskId = targetTaskId;

    }

    public Void execute(CommandContext commandContext) {

        HistoricTaskService historicTaskService = CommandContextUtil.getHistoricTaskService();
        ExecutionEntityManager executionEntityManager = CommandContextUtil.getExecutionEntityManager();
        TaskEntityManager taskEntityManager = org.flowable.task.service.impl.util.CommandContextUtil
                .getTaskEntityManager();
        IdentityLinkService identityLinkService = CommandContextUtil.getIdentityLinkService();

        HistoricTaskInstanceEntity targetTaskEntity = historicTaskService.getHistoricTask(targetTaskId);

        String targetExecutionId = targetTaskEntity.getExecutionId();
        ExecutionEntity targetExecutionEntity = executionEntityManager.findById(targetExecutionId);

        String processDefinitionId = targetExecutionEntity.getProcessDefinitionId();
        Process process = ProcessDefinitionUtil.getProcess(processDefinitionId);

        String flowElementId = targetTaskEntity.getTaskDefinitionKey();
        FlowElement flowElement = process.getFlowElement(flowElementId);
        targetExecutionEntity.setCurrentFlowElement(flowElement);
        CommandContextUtil.getAgenda().planContinueProcessInCompensation(targetExecutionEntity);

        identityLinkService.deleteIdentityLinksByTaskId(currentTaskId);
        identityLinkService.deleteIdentityLinksByTaskId(targetTaskId);
        historicTaskService.deleteHistoricTask(historicTaskService.getHistoricTask(currentTaskId));
        historicTaskService.deleteHistoricTask(targetTaskEntity);
        taskEntityManager.delete(currentTaskId);

        return null;
    }

}
