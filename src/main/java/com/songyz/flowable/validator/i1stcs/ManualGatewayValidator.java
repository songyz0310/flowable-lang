package com.songyz.flowable.validator.i1stcs;

import java.util.List;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.ExclusiveGateway;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.StartEvent;
import org.flowable.validation.ValidationError;

/**
 * 手动版网关，校验器<br>
 * 由于目前版本，排他网关是由自己控制的，并不是通过计算表达式，当后期通过自动生成表达式时，需要移除次校验器
 * 
 * @author songyz<br>
 * @createTime 2019-04-02 16:02:06
 */
public class ManualGatewayValidator extends AProcessLevelValidator {

    @Override
    protected void executeValidation(BpmnModel bpmnModel, Process process, List<ValidationError> errors) {

        // 开始节点后不能直接跟网关，
        process.findFlowElementsOfType(StartEvent.class, false).forEach(startEvent -> {
            startEvent.getOutgoingFlows().forEach(flow -> {
                if (ExclusiveGateway.class.isInstance(flow.getTargetFlowElement())) {
                    addError(errors, ErrorTitle.START_EVENT_INVALID, process, startEvent,
                            ErrorDesc.START_EVENT_NOT_ALLOWED_ADJACENT_TO_EXCLUSIVEGATEWAY);
                }
            });
        });

    }

}
