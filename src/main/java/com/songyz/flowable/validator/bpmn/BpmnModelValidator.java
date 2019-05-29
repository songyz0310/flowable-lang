package com.songyz.flowable.validator.bpmn;

import java.util.List;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.Process;
import org.flowable.validation.ValidationError;

import com.songyz.flowable.validator.i1stcs.AValidator;
import com.songyz.flowable.validator.i1stcs.ErrorDesc;
import com.songyz.flowable.validator.i1stcs.ErrorTitle;

/**
 * 流程模型校验器
 * 
 * @author songyz
 * @createTime 2019-01-05 03:32:27
 */
public class BpmnModelValidator extends AValidator {

    @Override
    public void validate(BpmnModel bpmnModel, List<ValidationError> errors) {

        // If all process definitions of this bpmnModel are not executable, raise an error
        boolean isAtLeastOneExecutable = validateAtLeastOneExecutable(bpmnModel, errors);

        // If at least one process definition is executable, show a warning for each of the none-executables
        if (isAtLeastOneExecutable) {
            for (Process process : bpmnModel.getProcesses()) {
                if (!process.isExecutable()) {
                    addWarning(errors, ErrorTitle.PROCESS_DEFINITION_INVALID, process, process,
                            ErrorDesc.PROCESS_DEFINITION_NOT_EXECUTABLE);
                }
                handleProcessConstraints(bpmnModel, process, errors);
            }
        }
        handleBPMNModelConstraints(bpmnModel, errors);
    }

    protected void handleProcessConstraints(BpmnModel bpmnModel, Process process, List<ValidationError> errors) {
        if (process.getId() != null && process.getId().length() > PROCESS_DEFINITION_ID_MAX_LENGTH) {
            addError(errors, ErrorTitle.PROCESS_DEFINITION_PARAM_TOO_LONG.getI18n(), process,
                    ErrorDesc.PROCESS_DEFINITION_ID_TOO_LONG.getI18n(PROCESS_DEFINITION_ID_MAX_LENGTH));
        }
        if (process.getName() != null && process.getName().length() > PROCESS_DEFINITION_NAME_MAX_LENGTH) {
            addError(errors, ErrorTitle.PROCESS_DEFINITION_PARAM_TOO_LONG.getI18n(), process,
                    ErrorDesc.PROCESS_DEFINITION_NAME_TOO_LONG.getI18n(PROCESS_DEFINITION_NAME_MAX_LENGTH));
        }
        if (process.getDocumentation() != null
                && process.getDocumentation().length() > PROCESS_DEFINITION_DOCUMENTATION_MAX_LENGTH) {
            addError(errors, ErrorTitle.PROCESS_DEFINITION_PARAM_TOO_LONG.getI18n(), process,
                    ErrorDesc.PROCESS_DEFINITION_DOCUMENTATION_TOO_LONG
                            .getI18n(PROCESS_DEFINITION_DOCUMENTATION_MAX_LENGTH));
        }
    }

    protected void handleBPMNModelConstraints(BpmnModel bpmnModel, List<ValidationError> errors) {
        if (bpmnModel.getTargetNamespace() != null
                && bpmnModel.getTargetNamespace().length() > BPMN_MODEL_TARGET_NAMESPACE_MAX_LENGTH) {
            addError(errors, ErrorTitle.PROCESS_DEFINITION_PARAM_TOO_LONG.getI18n(),
                    ErrorDesc.BPMN_MODEL_TARGET_NAMESPACE_TOO_LONG.getI18n(BPMN_MODEL_TARGET_NAMESPACE_MAX_LENGTH));
        }
    }

    /**
     * Returns 'true' if at least one process definition in the {@link BpmnModel} is executable.
     */
    protected boolean validateAtLeastOneExecutable(BpmnModel bpmnModel, List<ValidationError> errors) {
        int nrOfExecutableDefinitions = 0;
        for (Process process : bpmnModel.getProcesses()) {
            if (process.isExecutable()) {
                nrOfExecutableDefinitions++;
            }
        }

        if (nrOfExecutableDefinitions == 0) {
            addError(errors, ErrorTitle.PROCESS_DEFINITION_INVALID, ErrorDesc.ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE);
        }

        return nrOfExecutableDefinitions > 0;
    }

}
