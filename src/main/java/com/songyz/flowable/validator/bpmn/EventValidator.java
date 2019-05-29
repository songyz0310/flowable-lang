package com.songyz.flowable.validator.bpmn;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.CompensateEventDefinition;
import org.flowable.bpmn.model.Event;
import org.flowable.bpmn.model.EventDefinition;
import org.flowable.bpmn.model.MessageEventDefinition;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.SignalEventDefinition;
import org.flowable.bpmn.model.TimerEventDefinition;
import org.flowable.validation.ValidationError;

import com.songyz.flowable.validator.i1stcs.AProcessLevelValidator;
import com.songyz.flowable.validator.i1stcs.ErrorDesc;
import com.songyz.flowable.validator.i1stcs.ErrorTitle;

/**
 * 事件校验器
 * 
 * @author songyz
 * @createTime 2019-01-05 03:38:46
 */
public class EventValidator extends AProcessLevelValidator {

    @Override
    protected void executeValidation(BpmnModel bpmnModel, Process process, List<ValidationError> errors) {
        List<Event> events = process.findFlowElementsOfType(Event.class);
        for (Event event : events) {
            if (event.getEventDefinitions() != null) {
                for (EventDefinition eventDefinition : event.getEventDefinitions()) {

                    if (eventDefinition instanceof MessageEventDefinition) {
                        handleMessageEventDefinition(bpmnModel, process, event, eventDefinition, errors);
                    }
                    else if (eventDefinition instanceof SignalEventDefinition) {
                        handleSignalEventDefinition(bpmnModel, process, event, eventDefinition, errors);
                    }
                    else if (eventDefinition instanceof TimerEventDefinition) {
                        handleTimerEventDefinition(process, event, eventDefinition, errors);
                    }
                    else if (eventDefinition instanceof CompensateEventDefinition) {
                        handleCompensationEventDefinition(bpmnModel, process, event, eventDefinition, errors);
                    }

                }
            }
        }
    }

    protected void handleMessageEventDefinition(BpmnModel bpmnModel, Process process, Event event,
            EventDefinition eventDefinition, List<ValidationError> errors) {
        MessageEventDefinition messageEventDefinition = (MessageEventDefinition) eventDefinition;

        if (StringUtils.isEmpty(messageEventDefinition.getMessageRef())) {

            if (StringUtils.isEmpty(messageEventDefinition.getMessageExpression())) {
                // message ref should be filled in
                addError(errors, ErrorTitle.MESSAGE_INVALID, process, event,
                        ErrorDesc.MESSAGE_EVENT_MISSING_MESSAGE_REF);
            }

        }
        else if (!bpmnModel.containsMessageId(messageEventDefinition.getMessageRef())) {
            // message ref should exist
            addError(errors, ErrorTitle.MESSAGE_INVALID, process, event, ErrorDesc.MESSAGE_EVENT_INVALID_MESSAGE_REF);
        }
    }

    protected void handleSignalEventDefinition(BpmnModel bpmnModel, Process process, Event event,
            EventDefinition eventDefinition, List<ValidationError> errors) {
        SignalEventDefinition signalEventDefinition = (SignalEventDefinition) eventDefinition;

        if (StringUtils.isEmpty(signalEventDefinition.getSignalRef())) {

            if (StringUtils.isEmpty(signalEventDefinition.getSignalExpression())) {
                addError(errors, ErrorTitle.SIGNAL_INVALID, process, event, ErrorDesc.SIGNAL_EVENT_MISSING_SIGNAL_REF);
            }

        }
        else if (!bpmnModel.containsSignalId(signalEventDefinition.getSignalRef())) {
            addError(errors, ErrorTitle.SIGNAL_INVALID, process, event, ErrorDesc.SIGNAL_EVENT_INVALID_SIGNAL_REF);
        }
    }

    protected void handleTimerEventDefinition(Process process, Event event, EventDefinition eventDefinition,
            List<ValidationError> errors) {
        TimerEventDefinition timerEventDefinition = (TimerEventDefinition) eventDefinition;
        if (StringUtils.isEmpty(timerEventDefinition.getTimeDate())
                && StringUtils.isEmpty(timerEventDefinition.getTimeCycle())
                && StringUtils.isEmpty(timerEventDefinition.getTimeDuration())) {
            // neither date, cycle or duration configured
            addError(errors, ErrorTitle.SIGNAL_INVALID, process, event, ErrorDesc.EVENT_TIMER_MISSING_CONFIGURATION);
        }
    }

    protected void handleCompensationEventDefinition(BpmnModel bpmnModel, Process process, Event event,
            EventDefinition eventDefinition, List<ValidationError> errors) {
        CompensateEventDefinition compensateEventDefinition = (CompensateEventDefinition) eventDefinition;

        // Check activityRef
        if ((StringUtils.isNotEmpty(compensateEventDefinition.getActivityRef())
                && process.getFlowElement(compensateEventDefinition.getActivityRef(), true) == null)) {
            addError(errors, ErrorTitle.SIGNAL_INVALID, process, event,
                    ErrorDesc.COMPENSATE_EVENT_INVALID_ACTIVITY_REF);
        }
    }

}
