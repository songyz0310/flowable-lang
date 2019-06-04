package com.songyz.flowable.validator.i1stcs;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.SequenceFlow;
import org.flowable.validation.ValidationError;

/**
 * 流程序列流校验（流程连线校验）
 * 
 * @author songyz<br>
 * @createTime 2019-06-03 15:49:50
 */
public class SequenceValidator extends AProcessLevelValidator {

    @Override
    protected void executeValidation(BpmnModel bpmnModel, Process process, List<ValidationError> errors) {

        List<SequenceFlow> sequences = process.findFlowElementsOfType(SequenceFlow.class, false);
        sequences.forEach(sequence -> {

            if (duplicateSequence(sequences, sequence.getId(), sequence.getSourceRef(), sequence.getTargetRef())) {
                addError(errors, ErrorTitle.SEQ_FLOW_INVALID.getI18n(), sequence,
                        ErrorDesc.SEQ_FLOW_DUPLICATE.getI18n());
            }

        });

    }

    // 判断是否重复（来源和目标一致视为重复）
    protected boolean duplicateSequence(Collection<SequenceFlow> sequences, String id, String source, String target) {
        for (SequenceFlow sequence : sequences) {

            if (Objects.equals(sequence.getId(), id) == false //
                    && Objects.equals(sequence.getSourceRef(), source)//
                    && Objects.equals(sequence.getTargetRef(), target)) {
                return true;
            }
        }
        return false;
    }

}
