package com.songyz.flowable.validator.bpmn;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.ImplementationType;
import org.flowable.bpmn.model.Interface;
import org.flowable.bpmn.model.Operation;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.ServiceTask;
import org.flowable.validation.ValidationError;

import com.songyz.flowable.validator.i1stcs.ErrorDesc;
import com.songyz.flowable.validator.i1stcs.ErrorTitle;

/**
 * 服务任务校验器
 * 
 * @author songyz
 * @createTime 2019-01-05 03:49:56
 */
public class ServiceTaskValidator extends ExternalInvocationTaskValidator {

    @Override
    protected void executeValidation(BpmnModel bpmnModel, Process process, List<ValidationError> errors) {
        List<ServiceTask> serviceTasks = process.findFlowElementsOfType(ServiceTask.class);
        for (ServiceTask serviceTask : serviceTasks) {
            verifyImplementation(process, serviceTask, errors);
            verifyType(process, serviceTask, errors);
            verifyResultVariableName(process, serviceTask, errors);
            verifyWebservice(bpmnModel, process, serviceTask, errors);
        }

    }

    protected void verifyImplementation(Process process, ServiceTask serviceTask, List<ValidationError> errors) {
        if (!ImplementationType.IMPLEMENTATION_TYPE_CLASS.equalsIgnoreCase(serviceTask.getImplementationType())
                && !ImplementationType.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION
                        .equalsIgnoreCase(serviceTask.getImplementationType())
                && !ImplementationType.IMPLEMENTATION_TYPE_EXPRESSION
                        .equalsIgnoreCase(serviceTask.getImplementationType())
                && !ImplementationType.IMPLEMENTATION_TYPE_WEBSERVICE
                        .equalsIgnoreCase(serviceTask.getImplementationType())
                && StringUtils.isEmpty(serviceTask.getType())) {
            addError(errors, ErrorTitle.SERVICE_TASK_INVALID, process, serviceTask,
                    ErrorDesc.SERVICE_TASK_MISSING_IMPLEMENTATION);
        }
    }

    protected void verifyType(Process process, ServiceTask serviceTask, List<ValidationError> errors) {
        if (StringUtils.isNotEmpty(serviceTask.getType())) {

            if (!serviceTask.getType().equalsIgnoreCase("mail") && !serviceTask.getType().equalsIgnoreCase("mule")
                    && !serviceTask.getType().equalsIgnoreCase("camel")
                    && !serviceTask.getType().equalsIgnoreCase("shell")
                    && !serviceTask.getType().equalsIgnoreCase("dmn")
                    && !serviceTask.getType().equalsIgnoreCase("http")) {

                addError(errors, ErrorTitle.SERVICE_TASK_INVALID, process, serviceTask,
                        ErrorDesc.SERVICE_TASK_INVALID_TYPE);
            }

            if (serviceTask.getType().equalsIgnoreCase("mail")) {
                validateFieldDeclarationsForEmail(process, serviceTask, serviceTask.getFieldExtensions(), errors);
            }
            else if (serviceTask.getType().equalsIgnoreCase("shell")) {
                validateFieldDeclarationsForShell(process, serviceTask, serviceTask.getFieldExtensions(), errors);
            }
            else if (serviceTask.getType().equalsIgnoreCase("dmn")) {
                validateFieldDeclarationsForDmn(process, serviceTask, serviceTask.getFieldExtensions(), errors);
            }
            else if (serviceTask.getType().equalsIgnoreCase("http")) {
                validateFieldDeclarationsForHttp(process, serviceTask, serviceTask.getFieldExtensions(), errors);
            }

        }
    }

    protected void verifyResultVariableName(Process process, ServiceTask serviceTask, List<ValidationError> errors) {
        if (StringUtils.isNotEmpty(serviceTask.getResultVariableName())
                && (ImplementationType.IMPLEMENTATION_TYPE_CLASS.equals(serviceTask.getImplementationType())
                        || ImplementationType.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION
                                .equals(serviceTask.getImplementationType()))) {
            addError(errors, ErrorTitle.SERVICE_TASK_INVALID, process, serviceTask,
                    ErrorDesc.SERVICE_TASK_RESULT_VAR_NAME_WITH_DELEGATE);
        }

        if (serviceTask.isUseLocalScopeForResultVariable()
                && StringUtils.isEmpty(serviceTask.getResultVariableName())) {
            addWarning(errors, ErrorTitle.SERVICE_TASK_INVALID, process, serviceTask,
                    ErrorDesc.SERVICE_TASK_USE_LOCAL_SCOPE_FOR_RESULT_VAR_WITHOUT_RESULT_VARIABLE_NAME);
        }
    }

    protected void verifyWebservice(BpmnModel bpmnModel, Process process, ServiceTask serviceTask,
            List<ValidationError> errors) {
        if (ImplementationType.IMPLEMENTATION_TYPE_WEBSERVICE.equalsIgnoreCase(serviceTask.getImplementationType())
                && StringUtils.isNotEmpty(serviceTask.getOperationRef())) {

            boolean operationFound = false;
            if (bpmnModel.getInterfaces() != null && !bpmnModel.getInterfaces().isEmpty()) {
                for (Interface bpmnInterface : bpmnModel.getInterfaces()) {
                    if (bpmnInterface.getOperations() != null && !bpmnInterface.getOperations().isEmpty()) {
                        for (Operation operation : bpmnInterface.getOperations()) {
                            if (operation.getId() != null && operation.getId().equals(serviceTask.getOperationRef())) {
                                operationFound = true;
                            }
                        }
                    }
                }
            }

            if (!operationFound) {
                addError(errors, ErrorTitle.SERVICE_TASK_INVALID, process, serviceTask,
                        ErrorDesc.SERVICE_TASK_WEBSERVICE_INVALID_OPERATION_REF);
            }

        }
    }

}
