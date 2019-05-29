/**
 * 
 */
package com.songyz.flowable.validator.i1stcs;

import java.util.List;

import org.flowable.bpmn.model.BaseElement;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.Process;
import org.flowable.validation.ValidationError;
import org.flowable.validation.validator.Validator;

/**
 * 关于添加异常的公共方法
 * 
 * @author songyz<br>
 * @createTime 2019-01-09 01:42:17
 */
public abstract class AValidator extends I1stcsConstraints implements Validator {

    public void addError(List<ValidationError> validationErrors, ValidationError error) {
        validationErrors.add(error);
    }

    protected void addError(List<ValidationError> validationErrors, String problem, String description) {
        addError(validationErrors, problem, null, null, description, false);
    }

    protected void addError(List<ValidationError> validationErrors, String problem, BaseElement baseElement,
            String description) {
        addError(validationErrors, problem, null, baseElement, description);
    }

    protected void addError(List<ValidationError> validationErrors, String problem, Process process,
            BaseElement baseElement, String description) {
        addError(validationErrors, problem, process, baseElement, description, false);
    }

    protected void addWarning(List<ValidationError> validationErrors, String problem, Process process,
            BaseElement baseElement, String description) {
        addError(validationErrors, problem, process, baseElement, description, true);
    }

    protected void addError(List<ValidationError> validationErrors, String problem, Process process,
            BaseElement baseElement, String description, boolean isWarning) {
        ValidationError error = new ValidationError();
        error.setWarning(isWarning);

        if (process != null) {
            error.setProcessDefinitionId(process.getId());
            error.setProcessDefinitionName(process.getName());
        }

        if (baseElement != null) {
            error.setXmlLineNumber(baseElement.getXmlRowNumber());
            error.setXmlColumnNumber(baseElement.getXmlColumnNumber());
        }
        error.setProblem(problem);
        error.setDefaultDescription(description);

        if (baseElement instanceof FlowElement) {
            FlowElement flowElement = (FlowElement) baseElement;
            error.setActivityId(flowElement.getId());
            error.setActivityName(flowElement.getName());
        }

        addError(validationErrors, error);
    }

    protected void addError(List<ValidationError> validationErrors, String problem, Process process, String id,
            String description) {
        ValidationError error = new ValidationError();

        if (process != null) {
            error.setProcessDefinitionId(process.getId());
            error.setProcessDefinitionName(process.getName());
        }

        error.setProblem(problem);
        error.setDefaultDescription(description);
        error.setActivityId(id);

        addError(validationErrors, error);
    }

    protected void addError(List<ValidationError> validationErrors, ErrorTitle problem, ErrorDesc description) {
        addError(validationErrors, problem, null, null, description, false);
    }

    protected void addError(List<ValidationError> validationErrors, ErrorTitle problem, BaseElement baseElement,
            ErrorDesc description) {
        addError(validationErrors, problem, null, baseElement, description);
    }

    protected void addError(List<ValidationError> validationErrors, ErrorTitle problem, Process process,
            BaseElement baseElement, ErrorDesc description) {
        addError(validationErrors, problem, process, baseElement, description, false);
    }

    protected void addWarning(List<ValidationError> validationErrors, ErrorTitle problem, Process process,
            BaseElement baseElement, ErrorDesc description) {
        addError(validationErrors, problem, process, baseElement, description, true);
    }

    protected void addError(List<ValidationError> validationErrors, ErrorTitle problem, Process process,
            BaseElement baseElement, ErrorDesc description, boolean isWarning) {
        ValidationError error = new ValidationError();
        error.setWarning(isWarning);

        if (process != null) {
            error.setProcessDefinitionId(process.getId());
            error.setProcessDefinitionName(process.getName());
        }

        if (baseElement != null) {
            error.setXmlLineNumber(baseElement.getXmlRowNumber());
            error.setXmlColumnNumber(baseElement.getXmlColumnNumber());
        }
        error.setProblem(problem.getI18n());
        error.setDefaultDescription(description.getI18n());

        if (baseElement instanceof FlowElement) {
            FlowElement flowElement = (FlowElement) baseElement;
            error.setActivityId(flowElement.getId());
            error.setActivityName(flowElement.getName());
        }

        addError(validationErrors, error);
    }

    protected void addError(List<ValidationError> validationErrors, ErrorTitle problem, Process process, String id,
            ErrorDesc description) {
        ValidationError error = new ValidationError();

        if (process != null) {
            error.setProcessDefinitionId(process.getId());
            error.setProcessDefinitionName(process.getName());
        }

        error.setProblem(problem.getI18n());
        error.setDefaultDescription(description.getI18n());
        error.setActivityId(id);

        addError(validationErrors, error);
    }

}
