package com.songyz.flowable.validator.bpmn;

import java.util.List;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.EventDefinition;
import org.flowable.bpmn.model.IntermediateCatchEvent;
import org.flowable.bpmn.model.MessageEventDefinition;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.SignalEventDefinition;
import org.flowable.bpmn.model.TimerEventDefinition;
import org.flowable.validation.ValidationError;

import com.songyz.flowable.validator.i1stcs.AProcessLevelValidator;
import com.songyz.flowable.validator.i1stcs.ErrorDesc;
import com.songyz.flowable.validator.i1stcs.ErrorTitle;

/**
 * 中间捕获事件校验器
 * 
 * @author songyz
 * @createTime 2019-01-05 03:43:01
 */
public class IntermediateCatchEventValidator extends AProcessLevelValidator {

    @Override
    protected void executeValidation(BpmnModel bpmnModel, Process process, List<ValidationError> errors) {
        List<IntermediateCatchEvent> intermediateCatchEvents = process
                .findFlowElementsOfType(IntermediateCatchEvent.class);
        for (IntermediateCatchEvent intermediateCatchEvent : intermediateCatchEvents) {
            EventDefinition eventDefinition = null;
            if (!intermediateCatchEvent.getEventDefinitions().isEmpty()) {
                eventDefinition = intermediateCatchEvent.getEventDefinitions().get(0);
            }

            if (eventDefinition == null) {
                addError(errors, ErrorTitle.INTERMEDIATE_CATCH_EVENT_INVALID, process, intermediateCatchEvent,
                        ErrorDesc.INTERMEDIATE_CATCH_EVENT_NO_EVENTDEFINITION);
            }
            else {
                if (!(eventDefinition instanceof TimerEventDefinition)
                        && !(eventDefinition instanceof SignalEventDefinition)
                        && !(eventDefinition instanceof MessageEventDefinition)) {
                    addError(errors, ErrorTitle.INTERMEDIATE_CATCH_EVENT_INVALID, process, intermediateCatchEvent,
                            ErrorDesc.INTERMEDIATE_CATCH_EVENT_INVALID_EVENTDEFINITION);
                }
            }
        }
    }

}