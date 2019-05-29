package com.songyz.flowable.validator.bpmn;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.Activity;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.DataAssociation;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.MultiInstanceLoopCharacteristics;
import org.flowable.bpmn.model.Process;
import org.flowable.validation.ValidationError;

import com.songyz.flowable.validator.i1stcs.AProcessLevelValidator;
import com.songyz.flowable.validator.i1stcs.ErrorDesc;
import com.songyz.flowable.validator.i1stcs.ErrorTitle;

/**
 * 流程节点校验器
 * 
 * @author songyz
 * @createTime 2019-01-05 03:42:09
 */
public class FlowElementValidator extends AProcessLevelValidator {

    @Override
    protected void executeValidation(BpmnModel bpmnModel, Process process, List<ValidationError> errors) {
        for (FlowElement flowElement : process.getFlowElements()) {
            if (flowElement instanceof Activity) {
                Activity activity = (Activity) flowElement;
                handleConstraints(process, activity, errors);
                handleMultiInstanceLoopCharacteristics(process, activity, errors);
                handleDataAssociations(process, activity, errors);
            }
        }
    }

    protected void handleConstraints(Process process, Activity activity, List<ValidationError> errors) {
        if (activity.getId() != null && activity.getId().length() > PROCESS_ELEMENT_ID_MAX_LENGTH) {
            addError(errors, ErrorTitle.PROCESS_DEFINITION_PARAM_TOO_LONG.getI18n(), process, activity,
                    ErrorDesc.FLOW_ELEMENT_ID_TOO_LONG.getI18n(PROCESS_ELEMENT_ID_MAX_LENGTH));
        }
    }

    protected void handleMultiInstanceLoopCharacteristics(Process process, Activity activity,
            List<ValidationError> errors) {
        MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics = activity.getLoopCharacteristics();
        if (multiInstanceLoopCharacteristics != null) {

            if (StringUtils.isEmpty(multiInstanceLoopCharacteristics.getLoopCardinality())
                    && StringUtils.isEmpty(multiInstanceLoopCharacteristics.getInputDataItem())
                    && StringUtils.isEmpty(multiInstanceLoopCharacteristics.getCollectionString())) {

                addError(errors, ErrorTitle.PROCESS_DEFINITION_INVALID, process, activity,
                        ErrorDesc.MULTI_INSTANCE_MISSING_COLLECTION);
            }

            if (!StringUtils.isEmpty(multiInstanceLoopCharacteristics.getCollectionString())) {

                if (multiInstanceLoopCharacteristics.getHandler() == null) {
                    // verify string parsing function attributes
                    addError(errors, ErrorTitle.PROCESS_DEFINITION_INVALID, process, activity,
                            ErrorDesc.MULTI_INSTANCE_MISSING_COLLECTION_FUNCTION_PARAMETERS);
                }
            }

        }
    }

    protected void handleDataAssociations(Process process, Activity activity, List<ValidationError> errors) {
        if (activity.getDataInputAssociations() != null) {
            for (DataAssociation dataAssociation : activity.getDataInputAssociations()) {
                if (StringUtils.isEmpty(dataAssociation.getTargetRef())) {
                    addError(errors, ErrorTitle.PROCESS_DEFINITION_INVALID, process, activity,
                            ErrorDesc.DATA_ASSOCIATION_MISSING_TARGETREF);
                }
            }
        }
        if (activity.getDataOutputAssociations() != null) {
            for (DataAssociation dataAssociation : activity.getDataOutputAssociations()) {
                if (StringUtils.isEmpty(dataAssociation.getTargetRef())) {
                    addError(errors, ErrorTitle.PROCESS_DEFINITION_INVALID, process, activity,
                            ErrorDesc.DATA_ASSOCIATION_MISSING_TARGETREF);
                }
            }
        }
    }

}
