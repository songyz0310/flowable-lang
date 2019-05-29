package com.songyz.flowable.validator.bpmn;

import java.util.List;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.CompensateEventDefinition;
import org.flowable.bpmn.model.EventDefinition;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.SignalEventDefinition;
import org.flowable.bpmn.model.ThrowEvent;
import org.flowable.validation.ValidationError;

import com.songyz.flowable.validator.i1stcs.AProcessLevelValidator;
import com.songyz.flowable.validator.i1stcs.ErrorDesc;
import com.songyz.flowable.validator.i1stcs.ErrorTitle;

/**
 * 中间投掷事件校验器
 * 
 * @author songyz
 * @createTime 2019-01-05 03:44:19
 */
public class IntermediateThrowEventValidator extends AProcessLevelValidator {

    @Override
    protected void executeValidation(BpmnModel bpmnModel, Process process, List<ValidationError> errors) {
        List<ThrowEvent> throwEvents = process.findFlowElementsOfType(ThrowEvent.class);
        for (ThrowEvent throwEvent : throwEvents) {
            EventDefinition eventDefinition = null;
            if (!throwEvent.getEventDefinitions().isEmpty()) {
                eventDefinition = throwEvent.getEventDefinitions().get(0);
            }

            if (eventDefinition != null && !(eventDefinition instanceof SignalEventDefinition)
                    && !(eventDefinition instanceof CompensateEventDefinition)) {
                addError(errors, ErrorTitle.THROW_EVENT_INVALID, process, throwEvent,
                        ErrorDesc.THROW_EVENT_INVALID_EVENTDEFINITION);
            }
        }
    }

}
