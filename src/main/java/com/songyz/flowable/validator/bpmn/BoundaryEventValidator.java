package com.songyz.flowable.validator.bpmn;

import java.util.HashMap;
import java.util.List;

import org.flowable.bpmn.model.BoundaryEvent;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.CancelEventDefinition;
import org.flowable.bpmn.model.CompensateEventDefinition;
import org.flowable.bpmn.model.ErrorEventDefinition;
import org.flowable.bpmn.model.EventDefinition;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.MessageEventDefinition;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.SignalEventDefinition;
import org.flowable.bpmn.model.TimerEventDefinition;
import org.flowable.bpmn.model.Transaction;
import org.flowable.validation.ValidationError;

import com.songyz.flowable.validator.i1stcs.AProcessLevelValidator;
import com.songyz.flowable.validator.i1stcs.ErrorDesc;
import com.songyz.flowable.validator.i1stcs.ErrorTitle;

/**
 * 边界校验器
 * 
 * @author songyz
 * @createTime 2019-01-05 03:30:31
 */
public class BoundaryEventValidator extends AProcessLevelValidator {

    @Override
    protected void executeValidation(BpmnModel bpmnModel, Process process, List<ValidationError> errors) {
        List<BoundaryEvent> boundaryEvents = process.findFlowElementsOfType(BoundaryEvent.class);

        // Only one boundary event of type 'cancel' can be attached to the same
        // element, so we store the count temporarily here
        HashMap<String, Integer> cancelBoundaryEventsCounts = new HashMap<>();

        // Only one boundary event of type 'compensate' can be attached to the
        // same element, so we store the count temporarily here
        HashMap<String, Integer> compensateBoundaryEventsCounts = new HashMap<>();

        for (int i = 0; i < boundaryEvents.size(); i++) {

            BoundaryEvent boundaryEvent = boundaryEvents.get(i);

            if (boundaryEvent.getEventDefinitions() != null && !boundaryEvent.getEventDefinitions().isEmpty()) {

                EventDefinition eventDefinition = boundaryEvent.getEventDefinitions().get(0);
                if (!(eventDefinition instanceof TimerEventDefinition)
                        && !(eventDefinition instanceof ErrorEventDefinition)
                        && !(eventDefinition instanceof SignalEventDefinition)
                        && !(eventDefinition instanceof CancelEventDefinition)
                        && !(eventDefinition instanceof MessageEventDefinition)
                        && !(eventDefinition instanceof CompensateEventDefinition)) {

                    addError(errors, ErrorTitle.BOUNDARY_EVENT_INVALID, process, boundaryEvent,
                            ErrorDesc.BOUNDARY_EVENT_INVALID_EVENT_DEFINITION);

                }

                if (eventDefinition instanceof CancelEventDefinition) {

                    FlowElement attachedToFlowElement = bpmnModel.getFlowElement(boundaryEvent.getAttachedToRefId());
                    if (!(attachedToFlowElement instanceof Transaction)) {
                        addError(errors, ErrorTitle.BOUNDARY_EVENT_INVALID, process, boundaryEvent,
                                ErrorDesc.BOUNDARY_EVENT_CANCEL_ONLY_ON_TRANSACTION);
                    }
                    else {
                        if (!cancelBoundaryEventsCounts.containsKey(attachedToFlowElement.getId())) {
                            cancelBoundaryEventsCounts.put(attachedToFlowElement.getId(), new Integer(0));
                        }
                        cancelBoundaryEventsCounts.put(attachedToFlowElement.getId(),
                                new Integer(cancelBoundaryEventsCounts.get(attachedToFlowElement.getId()) + 1));
                    }

                }
                else if (eventDefinition instanceof CompensateEventDefinition) {

                    if (!compensateBoundaryEventsCounts.containsKey(boundaryEvent.getAttachedToRefId())) {
                        compensateBoundaryEventsCounts.put(boundaryEvent.getAttachedToRefId(), new Integer(0));
                    }
                    compensateBoundaryEventsCounts.put(boundaryEvent.getAttachedToRefId(),
                            compensateBoundaryEventsCounts.get(boundaryEvent.getAttachedToRefId()) + 1);

                }
                else if (eventDefinition instanceof MessageEventDefinition) {

                    // Check if other message boundary events with same message
                    // id
                    for (int j = 0; j < boundaryEvents.size(); j++) {
                        if (j != i) {
                            BoundaryEvent otherBoundaryEvent = boundaryEvents.get(j);
                            if (otherBoundaryEvent.getAttachedToRefId() != null && otherBoundaryEvent
                                    .getAttachedToRefId().equals(boundaryEvent.getAttachedToRefId())) {
                                if (otherBoundaryEvent.getEventDefinitions() != null
                                        && !otherBoundaryEvent.getEventDefinitions().isEmpty()) {
                                    EventDefinition otherEventDefinition = otherBoundaryEvent.getEventDefinitions()
                                            .get(0);
                                    if (otherEventDefinition instanceof MessageEventDefinition) {
                                        MessageEventDefinition currentMessageEventDefinition = (MessageEventDefinition) eventDefinition;
                                        MessageEventDefinition otherMessageEventDefinition = (MessageEventDefinition) otherEventDefinition;
                                        if (otherMessageEventDefinition.getMessageRef() != null
                                                && otherMessageEventDefinition.getMessageRef()
                                                        .equals(currentMessageEventDefinition.getMessageRef())) {
                                            addError(errors, ErrorTitle.BOUNDARY_EVENT_INVALID, process, boundaryEvent,
                                                    ErrorDesc.MESSAGE_EVENT_MULTIPLE_ON_BOUNDARY_SAME_MESSAGE_ID);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else {
                addError(errors, ErrorTitle.BOUNDARY_EVENT_INVALID, process, boundaryEvent,
                        ErrorDesc.BOUNDARY_EVENT_NO_EVENT_DEFINITION);
            }
        }

        for (String elementId : cancelBoundaryEventsCounts.keySet()) {
            if (cancelBoundaryEventsCounts.get(elementId) > 1) {
                addError(errors, ErrorTitle.BOUNDARY_EVENT_INVALID, process, bpmnModel.getFlowElement(elementId),
                        ErrorDesc.BOUNDARY_EVENT_MULTIPLE_CANCEL_ON_TRANSACTION);
            }
        }

        for (String elementId : compensateBoundaryEventsCounts.keySet()) {
            if (compensateBoundaryEventsCounts.get(elementId) > 1) {
                addError(errors, ErrorTitle.BOUNDARY_EVENT_INVALID, process, bpmnModel.getFlowElement(elementId),
                        ErrorDesc.COMPENSATE_EVENT_MULTIPLE_ON_BOUNDARY);
            }
        }

    }
}
