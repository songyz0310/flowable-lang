package com.songyz.flowable.validator.bpmn;

import java.util.List;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowableListener;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.UserTask;
import org.flowable.validation.ValidationError;

import com.songyz.flowable.validator.i1stcs.AProcessLevelValidator;
import com.songyz.flowable.validator.i1stcs.ErrorDesc;
import com.songyz.flowable.validator.i1stcs.ErrorTitle;

/**
 * 人工任务校验器
 * 
 * @author songyz
 * @createTime 2019-01-05 03:49:21
 */
public class UserTaskValidator extends AProcessLevelValidator {

    @Override
    protected void executeValidation(BpmnModel bpmnModel, Process process, List<ValidationError> errors) {
        List<UserTask> userTasks = process.findFlowElementsOfType(UserTask.class);
        for (UserTask userTask : userTasks) {

            if (userTask.getTaskListeners() != null) {
                for (FlowableListener listener : userTask.getTaskListeners()) {
                    if (listener.getImplementation() == null || listener.getImplementationType() == null) {
                        addError(errors, ErrorTitle.USER_TASK_INVALID, process, userTask,
                                ErrorDesc.USER_TASK_LISTENER_IMPLEMENTATION_MISSING);
                    }
                }
            }
        }
    }

}
