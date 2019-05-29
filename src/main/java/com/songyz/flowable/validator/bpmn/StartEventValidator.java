package com.songyz.flowable.validator.bpmn;

import java.util.ArrayList;
import java.util.List;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.EventDefinition;
import org.flowable.bpmn.model.MessageEventDefinition;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.SignalEventDefinition;
import org.flowable.bpmn.model.StartEvent;
import org.flowable.bpmn.model.TimerEventDefinition;
import org.flowable.validation.ValidationError;

import com.songyz.flowable.validator.i1stcs.AProcessLevelValidator;
import com.songyz.flowable.validator.i1stcs.ErrorDesc;
import com.songyz.flowable.validator.i1stcs.ErrorTitle;

/**
 * 开始事件校验器
 * 
 * @author songyz
 * @createTime 2019-01-05 03:51:04
 */
public class StartEventValidator extends AProcessLevelValidator {

    @Override
    protected void executeValidation(BpmnModel bpmnModel, Process process, List<ValidationError> errors) {
        List<StartEvent> startEvents = process.findFlowElementsOfType(StartEvent.class, false);
        validateEventDefinitionTypes(startEvents, process, errors);
        validateMultipleStartEvents(startEvents, process, errors);
    }

    protected void validateEventDefinitionTypes(List<StartEvent> startEvents, Process process,
            List<ValidationError> errors) {
        for (StartEvent startEvent : startEvents) {
            if (startEvent.getEventDefinitions() != null && !startEvent.getEventDefinitions().isEmpty()) {
                EventDefinition eventDefinition = startEvent.getEventDefinitions().get(0);
                if (!(eventDefinition instanceof MessageEventDefinition)
                        && !(eventDefinition instanceof TimerEventDefinition)
                        && !(eventDefinition instanceof SignalEventDefinition)) {
                    addError(errors, ErrorTitle.START_EVENT_INVALID, process, startEvent,
                            ErrorDesc.START_EVENT_INVALID_EVENT_DEFINITION);
                }
            }

        }
    }

    protected void validateMultipleStartEvents(List<StartEvent> startEvents, Process process,
            List<ValidationError> errors) {

        // Multiple none events are not supported
        List<StartEvent> noneStartEvents = new ArrayList<>();
        for (StartEvent startEvent : startEvents) {
            if (startEvent.getEventDefinitions() == null || startEvent.getEventDefinitions().isEmpty()) {
                noneStartEvents.add(startEvent);
            }
        }

        if (noneStartEvents.size() > 1) {
            for (StartEvent startEvent : noneStartEvents) {
                addError(errors, ErrorTitle.FOUND_NODE_MULTIPLE, process, startEvent,
                        ErrorDesc.MULTIPLE_START_EVENTS_NOT_SUPPORTED);
            }
        }
    }

}
