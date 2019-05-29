package com.songyz.flowable.validator.bpmn;

import java.util.List;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.EventDefinition;
import org.flowable.bpmn.model.EventSubProcess;
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
 * 事件子流程校验器
 * 
 * @author songyz
 * @createTime 2019-01-05 03:38:15
 */
public class EventSubprocessValidator extends AProcessLevelValidator {

    @Override
    protected void executeValidation(BpmnModel bpmnModel, Process process, List<ValidationError> errors) {
        List<EventSubProcess> eventSubprocesses = process.findFlowElementsOfType(EventSubProcess.class);
        for (EventSubProcess eventSubprocess : eventSubprocesses) {

            List<StartEvent> startEvents = process.findFlowElementsInSubProcessOfType(eventSubprocess,
                    StartEvent.class);
            for (StartEvent startEvent : startEvents) {
                if (startEvent.getEventDefinitions() != null && !startEvent.getEventDefinitions().isEmpty()) {
                    EventDefinition eventDefinition = startEvent.getEventDefinitions().get(0);
                    if (!(eventDefinition instanceof org.flowable.bpmn.model.ErrorEventDefinition)
                            && !(eventDefinition instanceof MessageEventDefinition)
                            && !(eventDefinition instanceof SignalEventDefinition)
                            && !(eventDefinition instanceof TimerEventDefinition)) {

                        addError(errors, ErrorTitle.SUBPROCESS_INVALID, process, eventSubprocess,
                                ErrorDesc.EVENT_SUBPROCESS_INVALID_START_EVENT_DEFINITION);
                    }
                }
            }

        }
    }

}