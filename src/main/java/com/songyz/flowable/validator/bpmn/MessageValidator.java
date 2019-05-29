package com.songyz.flowable.validator.bpmn;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.Message;
import org.flowable.validation.ValidationError;

import com.songyz.flowable.validator.i1stcs.AValidator;
import com.songyz.flowable.validator.i1stcs.ErrorDesc;
import com.songyz.flowable.validator.i1stcs.ErrorTitle;

/**
 * 消息校验器
 * 
 * @author songyz
 * @createTime 2019-01-05 03:44:41
 */
public class MessageValidator extends AValidator {

    @Override
    public void validate(BpmnModel bpmnModel, List<ValidationError> errors) {
        if (bpmnModel.getMessages() != null && !bpmnModel.getMessages().isEmpty()) {
            for (Message message : bpmnModel.getMessages()) {

                // Item ref
                if (StringUtils.isNotEmpty(message.getItemRef())) {
                    if (!bpmnModel.getItemDefinitions().containsKey(message.getItemRef())) {
                        addError(errors, ErrorTitle.MESSAGE_INVALID, null, message, ErrorDesc.MESSAGE_INVALID_ITEM_REF);
                    }
                }

            }
        }
    }

}
