package com.songyz.flowable.validator.bpmn;

import java.util.List;
import java.util.Objects;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.validation.ValidationError;

import com.songyz.flowable.validator.i1stcs.AValidator;
import com.songyz.flowable.validator.i1stcs.ErrorDesc;
import com.songyz.flowable.validator.i1stcs.ErrorTitle;

/**
 * 异常校验器
 * 
 * @author songyz<br>
 * @createTime 2019-01-05 02:30:53
 */
public class ErrorValidator extends AValidator {

    @Override
    public void validate(BpmnModel bpmnModel, List<ValidationError> errors) {
        if (bpmnModel.getErrors() != null) {
            for (String errorRef : bpmnModel.getErrors().keySet()) {
                if (Objects.equals("", bpmnModel.getErrors().get(errorRef))) {
                    addError(errors, ErrorTitle.PROCESS_DEFINITION_INVALID, null, errorRef,
                            ErrorDesc.ERROR_MISSING_ERROR_CODE);
                }
            }
        }
    }

}
