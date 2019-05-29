/**
 * 
 */
package com.songyz.flowable.validator.i1stcs;

import java.util.List;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.EndEvent;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.StartEvent;
import org.flowable.bpmn.model.UserTask;
import org.flowable.validation.ValidationError;

/**
 * 必要节点校验器
 * 
 * @author songyz<br>
 * @createTime 2019-01-09 01:26:44
 */
public class NecessaryNodeValidator extends AProcessLevelValidator {

    @Override
    protected void executeValidation(BpmnModel bpmnModel, Process process, List<ValidationError> errors) {

        if (process.findFlowElementsOfType(StartEvent.class, false).isEmpty()) {
            addError(errors, ErrorTitle.MISSING_NECESSARY_NODES, process, ErrorDesc.MISSING_NECESSARY_NODES_STARTEVENT);
        }

        if (process.findFlowElementsOfType(UserTask.class, false).isEmpty()) {
            addError(errors, ErrorTitle.MISSING_NECESSARY_NODES, process, ErrorDesc.MISSING_NECESSARY_NODES_USERTASK);
        }

        if (process.findFlowElementsOfType(EndEvent.class, false).isEmpty()) {
            addError(errors, ErrorTitle.MISSING_NECESSARY_NODES, process, ErrorDesc.MISSING_NECESSARY_NODES_ENDEVENT);
        }

    }

}
