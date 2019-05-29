package com.songyz.flowable.validator.bpmn;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.Artifact;
import org.flowable.bpmn.model.Association;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.Process;
import org.flowable.validation.ValidationError;

import com.songyz.flowable.validator.i1stcs.AValidator;
import com.songyz.flowable.validator.i1stcs.ErrorDesc;
import com.songyz.flowable.validator.i1stcs.ErrorTitle;

/**
 * 注释或者分组校验器
 * 
 * @author Administrator<br>
 * @createTime 2019-01-05 01:17:11
 */
public class AssociationValidator extends AValidator {

    public void validate(BpmnModel bpmnModel, List<ValidationError> errors) {

        // Global associations
        Collection<Artifact> artifacts = bpmnModel.getGlobalArtifacts();
        if (artifacts != null) {
            for (Artifact artifact : artifacts) {
                if (artifact instanceof Association) {
                    validate(null, (Association) artifact, errors);
                }
            }
        }

        // Process associations
        for (Process process : bpmnModel.getProcesses()) {
            artifacts = process.getArtifacts();
            for (Artifact artifact : artifacts) {
                if (artifact instanceof Association) {
                    validate(process, (Association) artifact, errors);
                }
            }
        }

    }

    protected void validate(Process process, Association association, List<ValidationError> errors) {

        if (StringUtils.isEmpty(association.getSourceRef())) {
            addError(errors, ErrorTitle.ASSOCIATION_INVALID, process, association,
                    ErrorDesc.ASSOCIATION_INVALID_SOURCE_REFERENCE);
        }
        if (StringUtils.isEmpty(association.getTargetRef())) {
            addError(errors, ErrorTitle.ASSOCIATION_INVALID, process, association,
                    ErrorDesc.ASSOCIATION_INVALID_TARGET_REFERENCE);
        }
    }
}
