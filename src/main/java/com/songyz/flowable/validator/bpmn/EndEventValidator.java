package com.songyz.flowable.validator.bpmn;

import java.util.List;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.CancelEventDefinition;
import org.flowable.bpmn.model.EndEvent;
import org.flowable.bpmn.model.EventDefinition;
import org.flowable.bpmn.model.FlowElementsContainer;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.Transaction;
import org.flowable.validation.ValidationError;

import com.songyz.flowable.validator.i1stcs.AProcessLevelValidator;
import com.songyz.flowable.validator.i1stcs.ErrorDesc;
import com.songyz.flowable.validator.i1stcs.ErrorTitle;

/**
 * 结束事件校验器
 * 
 * @author songyz
 * @createTime 2019-01-05 03:36:27
 */
public class EndEventValidator extends AProcessLevelValidator {

    @Override
    protected void executeValidation(BpmnModel bpmnModel, Process process, List<ValidationError> errors) {
        List<EndEvent> endEvents = process.findFlowElementsOfType(EndEvent.class);
        for (EndEvent endEvent : endEvents) {
            if (endEvent.getEventDefinitions() != null && !endEvent.getEventDefinitions().isEmpty()) {

                EventDefinition eventDefinition = endEvent.getEventDefinitions().get(0);

                // Error end event
                if (eventDefinition instanceof CancelEventDefinition) {

                    FlowElementsContainer parent = process.findParent(endEvent);
                    if (!(parent instanceof Transaction)) {
                        addError(errors, ErrorTitle.END_EVENT_INVALID, process, endEvent,
                                ErrorDesc.END_EVENT_CANCEL_ONLY_INSIDE_TRANSACTION);
                    }
                }
            }
        }
    }

}
