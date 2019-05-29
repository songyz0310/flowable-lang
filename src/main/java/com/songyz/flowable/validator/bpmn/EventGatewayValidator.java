package com.songyz.flowable.validator.bpmn;

import java.util.List;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.EventGateway;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.IntermediateCatchEvent;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.SequenceFlow;
import org.flowable.validation.ValidationError;

import com.songyz.flowable.validator.i1stcs.AProcessLevelValidator;
import com.songyz.flowable.validator.i1stcs.ErrorDesc;
import com.songyz.flowable.validator.i1stcs.ErrorTitle;

/**
 * 事件网关校验器
 * 
 * @author songyz
 * @createTime 2019-01-05 03:37:38
 */
public class EventGatewayValidator extends AProcessLevelValidator {

    @Override
    protected void executeValidation(BpmnModel bpmnModel, Process process, List<ValidationError> errors) {
        List<EventGateway> eventGateways = process.findFlowElementsOfType(EventGateway.class);
        for (EventGateway eventGateway : eventGateways) {
            for (SequenceFlow sequenceFlow : eventGateway.getOutgoingFlows()) {
                FlowElement flowElement = process.getFlowElement(sequenceFlow.getTargetRef(), true);
                if (flowElement != null && !(flowElement instanceof IntermediateCatchEvent)) {
                    addError(errors, ErrorTitle.EVENT_GATEWAY_INVALID, process, eventGateway,
                            ErrorDesc.  EVENT_GATEWAY_ONLY_CONNECTED_TO_INTERMEDIATE_EVENTS);
                }
            }
        }
    }

}
