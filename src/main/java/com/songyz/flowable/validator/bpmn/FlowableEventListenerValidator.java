package com.songyz.flowable.validator.bpmn;

import java.util.List;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.EventListener;
import org.flowable.bpmn.model.ImplementationType;
import org.flowable.bpmn.model.Process;
import org.flowable.validation.ValidationError;

import com.songyz.flowable.validator.i1stcs.AProcessLevelValidator;
import com.songyz.flowable.validator.i1stcs.ErrorDesc;
import com.songyz.flowable.validator.i1stcs.ErrorTitle;

/**
 * 流程事件监听校验器
 * 
 * @author songyz
 * @createTime 2019-01-05 03:41:30
 */
public class FlowableEventListenerValidator extends AProcessLevelValidator {

    @Override
    protected void executeValidation(BpmnModel bpmnModel, Process process, List<ValidationError> errors) {
        List<EventListener> eventListeners = process.getEventListeners();
        if (eventListeners != null) {
            for (EventListener eventListener : eventListeners) {

                if (eventListener.getImplementationType() != null && eventListener.getImplementationType()
                        .equals(ImplementationType.IMPLEMENTATION_TYPE_INVALID_THROW_EVENT)) {

                    addError(errors, ErrorTitle.EVENT_LISTENER_INVALID, process, eventListener,
                            ErrorDesc.EVENT_LISTENER_INVALID_THROW_EVENT_TYPE);

                }
                else if (eventListener.getImplementationType() == null
                        || eventListener.getImplementationType().length() == 0) {

                    addError(errors, ErrorTitle.EVENT_LISTENER_INVALID, process, eventListener,
                            ErrorDesc.EVENT_LISTENER_IMPLEMENTATION_MISSING);

                }
                else if (eventListener.getImplementationType() != null) {

                    if (!ImplementationType.IMPLEMENTATION_TYPE_CLASS.equals(eventListener.getImplementationType())
                            && !ImplementationType.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION
                                    .equals(eventListener.getImplementationType())
                            && !ImplementationType.IMPLEMENTATION_TYPE_THROW_SIGNAL_EVENT
                                    .equals(eventListener.getImplementationType())
                            && !ImplementationType.IMPLEMENTATION_TYPE_THROW_GLOBAL_SIGNAL_EVENT
                                    .equals(eventListener.getImplementationType())
                            && !ImplementationType.IMPLEMENTATION_TYPE_THROW_MESSAGE_EVENT
                                    .equals(eventListener.getImplementationType())
                            && !ImplementationType.IMPLEMENTATION_TYPE_THROW_ERROR_EVENT
                                    .equals(eventListener.getImplementationType())) {
                        addError(errors, ErrorTitle.EVENT_LISTENER_INVALID, process, eventListener,
                                ErrorDesc.EVENT_LISTENER_INVALID_IMPLEMENTATION);
                    }
                }
            }
        }
    }

}
