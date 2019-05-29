package com.songyz.flowable.validator.bpmn;

import java.util.List;

import org.flowable.bpmn.model.FieldExtension;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.TaskWithFieldExtensions;
import org.flowable.validation.ValidationError;

import com.songyz.flowable.validator.i1stcs.AProcessLevelValidator;
import com.songyz.flowable.validator.i1stcs.ErrorDesc;
import com.songyz.flowable.validator.i1stcs.ErrorTitle;

/**
 * 外部调用任务校验器
 * 
 * @author songyz
 * @createTime 2019-01-05 03:40:59
 */
public abstract class ExternalInvocationTaskValidator extends AProcessLevelValidator {

    protected void validateFieldDeclarationsForEmail(Process process, TaskWithFieldExtensions task,
            List<FieldExtension> fieldExtensions, List<ValidationError> errors) {
        boolean toDefined = false;
        boolean textOrHtmlDefined = false;

        for (FieldExtension fieldExtension : fieldExtensions) {
            if (fieldExtension.getFieldName().equals("to")) {
                toDefined = true;
            }
            if (fieldExtension.getFieldName().equals("html")) {
                textOrHtmlDefined = true;
            }
            if (fieldExtension.getFieldName().equals("htmlVar")) {
                textOrHtmlDefined = true;
            }
            if (fieldExtension.getFieldName().equals("text")) {
                textOrHtmlDefined = true;
            }
            if (fieldExtension.getFieldName().equals("textVar")) {
                textOrHtmlDefined = true;
            }
        }

        if (!toDefined) {
            addError(errors, ErrorTitle.MAIL_TASK_INVALID, process, task, ErrorDesc.MAIL_TASK_NO_RECIPIENT);
        }
        if (!textOrHtmlDefined) {
            addError(errors, ErrorTitle.MAIL_TASK_INVALID, process, task, ErrorDesc.MAIL_TASK_NO_CONTENT);
        }
    }

    protected void validateFieldDeclarationsForShell(Process process, TaskWithFieldExtensions task,
            List<FieldExtension> fieldExtensions, List<ValidationError> errors) {
        boolean shellCommandDefined = false;

        for (FieldExtension fieldExtension : fieldExtensions) {
            String fieldName = fieldExtension.getFieldName();
            String fieldValue = fieldExtension.getStringValue();

            if (fieldName.equals("command")) {
                shellCommandDefined = true;
            }

            if ((fieldName.equals("wait") || fieldName.equals("redirectError") || fieldName.equals("cleanEnv"))
                    && !fieldValue.toLowerCase().equals("true") && !fieldValue.toLowerCase().equals("false")) {
                addError(errors, ErrorTitle.SHELL_TASK_INVALID, process, task, ErrorDesc.SHELL_TASK_INVALID_PARAM);
            }

        }

        if (!shellCommandDefined) {
            addError(errors, ErrorTitle.SHELL_TASK_INVALID, process, task, ErrorDesc.SHELL_TASK_NO_COMMAND);
        }
    }

    protected void validateFieldDeclarationsForDmn(Process process, TaskWithFieldExtensions task,
            List<FieldExtension> fieldExtensions, List<ValidationError> errors) {
        boolean keyDefined = false;

        for (FieldExtension fieldExtension : fieldExtensions) {
            String fieldName = fieldExtension.getFieldName();
            String fieldValue = fieldExtension.getStringValue();

            if (fieldName.equals("decisionTableReferenceKey") && fieldValue != null && fieldValue.length() > 0) {
                keyDefined = true;
            }
        }

        if (!keyDefined) {
            addError(errors, ErrorTitle.DMN_TASK_INVALID, process, task, ErrorDesc.DMN_TASK_NO_KEY);
        }
    }

    protected void validateFieldDeclarationsForHttp(Process process, TaskWithFieldExtensions task,
            List<FieldExtension> fieldExtensions, List<ValidationError> errors) {
        boolean requestMethodDefined = false;
        boolean requestUrlDefined = false;

        for (FieldExtension fieldExtension : fieldExtensions) {

            String fieldName = fieldExtension.getFieldName();
            String fieldValue = fieldExtension.getStringValue();
            String fieldExpression = fieldExtension.getExpression();

            if (fieldName.equals("requestMethod") && ((fieldValue != null && fieldValue.length() > 0)
                    || (fieldExpression != null && fieldExpression.length() > 0))) {
                requestMethodDefined = true;
            }

            if (fieldName.equals("requestUrl") && ((fieldValue != null && fieldValue.length() > 0)
                    || (fieldExpression != null && fieldExpression.length() > 0))) {
                requestUrlDefined = true;
            }
        }

        if (!requestMethodDefined) {
            addError(errors, ErrorTitle.HTTP_TASK_INVALID, process, task, ErrorDesc.HTTP_TASK_NO_REQUEST_METHOD);
        }

        if (!requestUrlDefined) {
            addError(errors, ErrorTitle.HTTP_TASK_INVALID, process, task, ErrorDesc.HTTP_TASK_NO_REQUEST_URL);
        }

    }

}
