package com.songyz.flowable.validator.bpmn;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.FlowElementsContainer;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.SequenceFlow;
import org.flowable.validation.ValidationError;

import com.songyz.flowable.validator.i1stcs.AProcessLevelValidator;
import com.songyz.flowable.validator.i1stcs.ErrorDesc;
import com.songyz.flowable.validator.i1stcs.ErrorTitle;

/**
 * 顺序流校验器
 * 
 * @author songyz
 * @createTime 2019-01-05 03:47:09
 */
public class SequenceflowValidator extends AProcessLevelValidator {

    @Override
    protected void executeValidation(BpmnModel bpmnModel, Process process, List<ValidationError> errors) {
        List<SequenceFlow> sequenceFlows = process.findFlowElementsOfType(SequenceFlow.class);
        for (SequenceFlow sequenceFlow : sequenceFlows) {

            String sourceRef = sequenceFlow.getSourceRef();
            String targetRef = sequenceFlow.getTargetRef();

            if (StringUtils.isEmpty(sourceRef)) {
                addError(errors, ErrorTitle.SEQ_FLOW_INVALID, process, sequenceFlow, ErrorDesc.SEQ_FLOW_INVALID_SRC);
            }
            if (StringUtils.isEmpty(targetRef)) {
                addError(errors, ErrorTitle.SEQ_FLOW_INVALID, process, sequenceFlow, ErrorDesc.SEQ_FLOW_INVALID_TARGET);
            }

            // Implicit check: sequence flow cannot cross (sub) process boundaries, hence we check the parent
            // and not the process
            // (could be subprocess for example)
            FlowElement source = process.getFlowElement(sourceRef, true);
            FlowElement target = process.getFlowElement(targetRef, true);

            // Src and target validation
            if (source == null) {
                addError(errors, ErrorTitle.SEQ_FLOW_INVALID, process, sequenceFlow, ErrorDesc.SEQ_FLOW_INVALID_SRC);
            }

            if (target == null) {
                addError(errors, ErrorTitle.SEQ_FLOW_INVALID, process, sequenceFlow, ErrorDesc.SEQ_FLOW_INVALID_TARGET);
            }

            if (source != null && target != null) {
                FlowElementsContainer sourceContainer = process.getFlowElementsContainer(source.getId());
                FlowElementsContainer targetContainer = process.getFlowElementsContainer(target.getId());

                if (sourceContainer == null) {
                    addError(errors, ErrorTitle.SEQ_FLOW_INVALID, process, sequenceFlow,
                            ErrorDesc.SEQ_FLOW_INVALID_SRC);
                }

                if (targetContainer == null) {
                    addError(errors, ErrorTitle.SEQ_FLOW_INVALID, process, sequenceFlow,
                            ErrorDesc.SEQ_FLOW_INVALID_TARGET);
                }

                if (sourceContainer != null && targetContainer != null && !sourceContainer.equals(targetContainer)) {
                    addError(errors, ErrorTitle.SEQ_FLOW_INVALID, process, sequenceFlow,
                            ErrorDesc.SEQ_FLOW_INVALID_TARGET_SOURCE);
                }
            }
        }
    }

}
