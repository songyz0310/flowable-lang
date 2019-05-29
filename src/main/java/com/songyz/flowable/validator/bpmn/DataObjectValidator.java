package com.songyz.flowable.validator.bpmn;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.SubProcess;
import org.flowable.bpmn.model.ValuedDataObject;
import org.flowable.validation.ValidationError;

import com.songyz.flowable.validator.i1stcs.AProcessLevelValidator;
import com.songyz.flowable.validator.i1stcs.ErrorDesc;
import com.songyz.flowable.validator.i1stcs.ErrorTitle;

/**
 * 数据实体校验器
 * 
 * @author songyz
 * @createTime 2019-01-05 03:34:33
 */
public class DataObjectValidator extends AProcessLevelValidator {

    @Override
    protected void executeValidation(BpmnModel bpmnModel, Process process, List<ValidationError> errors) {

        // Gather data objects
        List<ValuedDataObject> allDataObjects = new ArrayList<>();
        allDataObjects.addAll(process.getDataObjects());
        List<SubProcess> subProcesses = process.findFlowElementsOfType(SubProcess.class, true);
        for (SubProcess subProcess : subProcesses) {
            allDataObjects.addAll(subProcess.getDataObjects());
        }

        // Validate
        for (ValuedDataObject dataObject : allDataObjects) {
            if (StringUtils.isEmpty(dataObject.getName())) {
                addError(errors, ErrorTitle.DATA_OBJECT_INVALID, process, dataObject,
                        ErrorDesc.DATA_OBJECT_MISSING_NAME);
            }
        }

    }

}
