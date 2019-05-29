/**
 * 
 */
package com.songyz.flowable.validator.i1stcs;

import java.util.List;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.Process;
import org.flowable.validation.ValidationError;

/**
 * 
 * @author songyz<br>
 * @createTime 2019-01-09 01:43:42
 */
public abstract class AProcessLevelValidator extends AValidator {

   
    @Override
    public void validate(BpmnModel bpmnModel, List<ValidationError> errors) {
        for (Process process : bpmnModel.getProcesses()) {
            executeValidation(bpmnModel, process, errors);
        }
    }

    protected abstract void executeValidation(BpmnModel bpmnModel, Process process, List<ValidationError> errors);

}
