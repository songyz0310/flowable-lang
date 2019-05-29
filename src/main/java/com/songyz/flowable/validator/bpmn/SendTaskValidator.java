package com.songyz.flowable.validator.bpmn;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.ImplementationType;
import org.flowable.bpmn.model.Interface;
import org.flowable.bpmn.model.Operation;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.SendTask;
import org.flowable.validation.ValidationError;

import com.songyz.flowable.validator.i1stcs.ErrorDesc;
import com.songyz.flowable.validator.i1stcs.ErrorTitle;

/**
 * 发送任务校验器
 * 
 * @author songyz
 * @createTime 2019-01-05 03:46:09
 */
public class SendTaskValidator extends ExternalInvocationTaskValidator {

    @Override
    protected void executeValidation(BpmnModel bpmnModel, Process process, List<ValidationError> errors) {
        List<SendTask> sendTasks = process.findFlowElementsOfType(SendTask.class);
        for (SendTask sendTask : sendTasks) {

            // Verify implementation
            if (StringUtils.isEmpty(sendTask.getType()) && !ImplementationType.IMPLEMENTATION_TYPE_WEBSERVICE
                    .equalsIgnoreCase(sendTask.getImplementationType())) {
                addError(errors, ErrorTitle.SEND_TASK_INVALID, process, sendTask,
                        ErrorDesc.SEND_TASK_INVALID_IMPLEMENTATION);
            }

            // Verify type
            if (StringUtils.isNotEmpty(sendTask.getType())) {

                if (!sendTask.getType().equalsIgnoreCase("mail") && !sendTask.getType().equalsIgnoreCase("mule")
                        && !sendTask.getType().equalsIgnoreCase("camel")) {
                    addError(errors, ErrorTitle.SEND_TASK_INVALID, process, sendTask, ErrorDesc.SEND_TASK_INVALID_TYPE);
                }

                if (sendTask.getType().equalsIgnoreCase("mail")) {
                    validateFieldDeclarationsForEmail(process, sendTask, sendTask.getFieldExtensions(), errors);
                }

            }

            // Web service
            verifyWebservice(bpmnModel, process, sendTask, errors);
        }
    }

    protected void verifyWebservice(BpmnModel bpmnModel, Process process, SendTask sendTask,
            List<ValidationError> errors) {
        if (ImplementationType.IMPLEMENTATION_TYPE_WEBSERVICE.equalsIgnoreCase(sendTask.getImplementationType())
                && StringUtils.isNotEmpty(sendTask.getOperationRef())) {

            boolean operationFound = false;
            if (bpmnModel.getInterfaces() != null && !bpmnModel.getInterfaces().isEmpty()) {
                for (Interface bpmnInterface : bpmnModel.getInterfaces()) {
                    if (bpmnInterface.getOperations() != null && !bpmnInterface.getOperations().isEmpty()) {
                        for (Operation operation : bpmnInterface.getOperations()) {
                            if (operation.getId() != null && operation.getId().equals(sendTask.getOperationRef())) {
                                operationFound = true;
                            }
                        }
                    }
                }
            }

            if (!operationFound) {
                addError(errors, ErrorTitle.SEND_TASK_INVALID, process, sendTask,
                        ErrorDesc.SEND_TASK_WEBSERVICE_INVALID_OPERATION_REF);
            }

        }
    }

}
