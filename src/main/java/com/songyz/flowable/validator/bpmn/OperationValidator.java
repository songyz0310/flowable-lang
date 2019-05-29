package com.songyz.flowable.validator.bpmn;

import java.util.List;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.Interface;
import org.flowable.bpmn.model.Operation;
import org.flowable.validation.ValidationError;

import com.songyz.flowable.validator.i1stcs.AValidator;
import com.songyz.flowable.validator.i1stcs.ErrorDesc;
import com.songyz.flowable.validator.i1stcs.ErrorTitle;

/**
 * 操作校验器
 * 
 * @author songyz<br>
 * @createTime 2019-01-05 02:29:48
 */
public class OperationValidator extends AValidator {

    @Override
    public void validate(BpmnModel bpmnModel, List<ValidationError> errors) {
        if (bpmnModel.getInterfaces() != null) {
            for (Interface bpmnInterface : bpmnModel.getInterfaces()) {
                if (bpmnInterface.getOperations() != null) {
                    for (Operation operation : bpmnInterface.getOperations()) {
                        if (bpmnModel.getMessage(operation.getInMessageRef()) == null) {
                            addError(errors, ErrorTitle.PROCESS_DEFINITION_INVALID, null, operation,
                                    ErrorDesc.OPERATION_INVALID_IN_MESSAGE_REFERENCE);
                        }
                    }
                }
            }
        }
    }

}
