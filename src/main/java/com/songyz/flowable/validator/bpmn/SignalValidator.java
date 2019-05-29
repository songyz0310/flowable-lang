package com.songyz.flowable.validator.bpmn;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.Signal;
import org.flowable.validation.ValidationError;

import com.songyz.flowable.validator.i1stcs.AValidator;
import com.songyz.flowable.validator.i1stcs.ErrorDesc;
import com.songyz.flowable.validator.i1stcs.ErrorTitle;

/**
 * 信号校验器
 * 
 * @author Administrator<br>
 * @createTime 2019-01-05 01:22:44
 */
public class SignalValidator extends AValidator {

    @Override
    public void validate(BpmnModel bpmnModel, List<ValidationError> errors) {
        Collection<Signal> signals = bpmnModel.getSignals();
        if (signals != null && !signals.isEmpty()) {

            for (Signal signal : signals) {
                if (StringUtils.isEmpty(signal.getId())) {
                    addError(errors, ErrorTitle.SIGNAL_INVALID, signal, ErrorDesc.SIGNAL_MISSING_ID);
                }

                if (StringUtils.isEmpty(signal.getName())) {
                    addError(errors, ErrorTitle.SIGNAL_INVALID, signal, ErrorDesc.SIGNAL_MISSING_NAME);
                }

                if (StringUtils.isNotEmpty(signal.getName())
                        && duplicateName(signals, signal.getId(), signal.getName())) {
                    addError(errors, ErrorTitle.SIGNAL_INVALID.getI18n(), signal,
                            ErrorDesc.SIGNAL_DUPLICATE_NAME.getI18n(signal.getName()));
                }

                if (signal.getScope() != null && !signal.getScope().equals(Signal.SCOPE_GLOBAL)
                        && !signal.getScope().equals(Signal.SCOPE_PROCESS_INSTANCE)) {
                    addError(errors, ErrorTitle.SIGNAL_INVALID, signal, ErrorDesc.SIGNAL_INVALID_SCOPE);
                }
            }

        }
    }

    protected boolean duplicateName(Collection<Signal> signals, String id, String name) {
        for (Signal signal : signals) {
            if (id != null && signal.getId() != null) {
                if (name.equals(signal.getName()) && !id.equals(signal.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

}
