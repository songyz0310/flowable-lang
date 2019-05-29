package com.songyz.flowable.validator.bpmn;

import java.util.List;

import org.flowable.bpmn.model.BaseElement;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.FlowableListener;
import org.flowable.bpmn.model.ImplementationType;
import org.flowable.bpmn.model.Process;
import org.flowable.validation.ValidationError;

import com.songyz.flowable.validator.i1stcs.AProcessLevelValidator;
import com.songyz.flowable.validator.i1stcs.ErrorDesc;
import com.songyz.flowable.validator.i1stcs.ErrorTitle;

/**
 * 执行监听器校验
 * 
 * @author songyz
 * @createTime 2019-01-05 03:40:08
 */
public class ExecutionListenerValidator extends AProcessLevelValidator {

    @Override
    protected void executeValidation(BpmnModel bpmnModel, Process process, List<ValidationError> errors) {

        validateListeners(process, process, process.getExecutionListeners(), errors);

        for (FlowElement flowElement : process.getFlowElements()) {
            validateListeners(process, flowElement, flowElement.getExecutionListeners(), errors);
        }
    }

    protected void validateListeners(Process process, BaseElement baseElement, List<FlowableListener> listeners,
            List<ValidationError> errors) {
        if (listeners != null) {
            for (FlowableListener listener : listeners) {
                if (listener.getImplementation() == null || listener.getImplementationType() == null) {
                    addError(errors, ErrorTitle.EXECUTION_LISTENER_INVALID, process, baseElement,
                            ErrorDesc.EXECUTION_LISTENER_IMPLEMENTATION_MISSING);
                }
                if (listener.getOnTransaction() != null
                        && ImplementationType.IMPLEMENTATION_TYPE_EXPRESSION.equals(listener.getImplementationType())) {
                    addError(errors, ErrorTitle.EXECUTION_LISTENER_INVALID, process, baseElement,
                            ErrorDesc.EXECUTION_LISTENER_INVALID_IMPLEMENTATION_TYPE);
                }
            }
        }
    }
}
