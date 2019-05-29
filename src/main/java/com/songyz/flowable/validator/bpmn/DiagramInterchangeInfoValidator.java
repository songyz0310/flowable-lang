package com.songyz.flowable.validator.bpmn;

import java.util.List;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowNode;
import org.flowable.bpmn.model.SequenceFlow;
import org.flowable.validation.ValidationError;

import com.songyz.flowable.validator.i1stcs.AValidator;
import com.songyz.flowable.validator.i1stcs.ErrorDesc;
import com.songyz.flowable.validator.i1stcs.ErrorTitle;

/**
 * 流程图校验器
 * 
 * @author songyz
 * @createTime 2019-01-05 03:36:04
 */
public class DiagramInterchangeInfoValidator extends AValidator {

    @Override
    public void validate(BpmnModel bpmnModel, List<ValidationError> errors) {
        if (!bpmnModel.getLocationMap().isEmpty()) {

            // Location map
            for (String bpmnReference : bpmnModel.getLocationMap().keySet()) {
                if (bpmnModel.getFlowElement(bpmnReference) == null) {
                    // ACT-1625: don't warn when artifacts are referenced from
                    // DI
                    if (bpmnModel.getArtifact(bpmnReference) == null) {
                        // check if it's a Pool or Lane, then DI is ok
                        if (bpmnModel.getPool(bpmnReference) == null && bpmnModel.getLane(bpmnReference) == null) {
                            addWarning(errors, ErrorTitle.DI_INVALID.getI18n(), null,
                                    bpmnModel.getFlowElement(bpmnReference),
                                    ErrorDesc.DI_INVALID_REFERENCE.getI18n(bpmnReference));
                        }
                    }
                }
                else if (!(bpmnModel.getFlowElement(bpmnReference) instanceof FlowNode)) {
                    addWarning(errors, ErrorTitle.DI_INVALID.getI18n(), null, bpmnModel.getFlowElement(bpmnReference),
                            ErrorDesc.DI_DOES_NOT_REFERENCE_FLOWNODE.getI18n(bpmnReference));
                }
            }

        }

        if (!bpmnModel.getFlowLocationMap().isEmpty()) {
            // flowlocation map
            for (String bpmnReference : bpmnModel.getFlowLocationMap().keySet()) {
                if (bpmnModel.getFlowElement(bpmnReference) == null) {
                    // ACT-1625: don't warn when artifacts are referenced from
                    // DI
                    if (bpmnModel.getArtifact(bpmnReference) == null) {
                        addWarning(errors, ErrorTitle.DI_INVALID, null, bpmnModel.getFlowElement(bpmnReference),
                                ErrorDesc.DI_INVALID_REFERENCE);
                    }
                }
                else if (!(bpmnModel.getFlowElement(bpmnReference) instanceof SequenceFlow)) {
                    addWarning(errors, ErrorTitle.DI_INVALID.getI18n(), null, bpmnModel.getFlowElement(bpmnReference),
                            ErrorDesc.DI_DOES_NOT_REFERENCE_SEQ_FLOW.getI18n(bpmnReference));
                }
            }
        }
    }
}
