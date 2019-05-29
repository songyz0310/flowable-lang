package com.songyz.flowable.validator.bpmn;

import java.util.List;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.EventSubProcess;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.StartEvent;
import org.flowable.bpmn.model.SubProcess;
import org.flowable.validation.ValidationError;

import com.songyz.flowable.validator.i1stcs.AProcessLevelValidator;
import com.songyz.flowable.validator.i1stcs.ErrorDesc;
import com.songyz.flowable.validator.i1stcs.ErrorTitle;

/**
 * 子流程校验器
 * 
 * @author songyz
 * @createTime 2019-01-05 03:51:17
 */
public class SubprocessValidator extends AProcessLevelValidator {

    @Override
    protected void executeValidation(BpmnModel bpmnModel, Process process, List<ValidationError> errors) {
        List<SubProcess> subProcesses = process.findFlowElementsOfType(SubProcess.class);
        for (SubProcess subProcess : subProcesses) {

            if (!(subProcess instanceof EventSubProcess)) {

                // Verify start events
                List<StartEvent> startEvents = process.findFlowElementsInSubProcessOfType(subProcess, StartEvent.class,
                        false);
                if (startEvents.size() > 1) {
                    addError(errors, ErrorTitle.SUBPROCESS_INVALID, process, subProcess,
                            ErrorDesc.SUBPROCESS_MULTIPLE_START_EVENTS);
                }

                for (StartEvent startEvent : startEvents) {
                    if (!startEvent.getEventDefinitions().isEmpty()) {
                        addError(errors, ErrorTitle.SUBPROCESS_INVALID, process, startEvent,
                                ErrorDesc.SUBPROCESS_START_EVENT_EVENT_DEFINITION_NOT_ALLOWED);
                    }
                }

            }

        }

    }

}
