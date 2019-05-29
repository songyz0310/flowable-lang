/**
 * 
 */
package com.songyz.flowable.validator.i1stcs;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.UserTask;
import org.flowable.validation.ValidationError;

/**
 * 必要节点参数校验器<br>
 * userTask ID 必填<br>
 * userTask name 必填<br>
 * userTask name 唯一<br>
 * userTask name 长度不大于32<br>
 * 
 * @author songyz<br>
 * @createTime 2019-01-09 16:38:52
 */
public class NecessaryNodeParamValidator extends AProcessLevelValidator {

    @Override
    protected void executeValidation(BpmnModel bpmnModel, Process process, List<ValidationError> errors) {

        List<UserTask> userTasks = process.findFlowElementsOfType(UserTask.class);
        for (UserTask userTask : userTasks) {
            if (StringUtils.isEmpty(userTask.getId())) {
                addError(errors, ErrorTitle.USER_TASK_INVALID, userTask, ErrorDesc.USER_TASK_MISSING_ID);
            }

            if (StringUtils.isEmpty(userTask.getName())) {
                addError(errors, ErrorTitle.USER_TASK_INVALID, process, userTask, ErrorDesc.USER_TASK_MISSING_NAME);
            }

            if (StringUtils.isNotEmpty(userTask.getName()) && StringUtils.isNotEmpty(userTask.getId())
                    && userTask.getName().length() > PROCESS_USER_TASK_NAME_MAX_LENGTH) {
                addError(errors, ErrorTitle.USER_TASK_INVALID.getI18n(), userTask,
                        ErrorDesc.USER_TASK_NAME_TOO_LONG.getI18n(PROCESS_USER_TASK_NAME_MAX_LENGTH));
            }

            if (StringUtils.isNotEmpty(userTask.getName())
                    && duplicateName(userTasks, userTask.getId(), userTask.getName())) {
                addError(errors, ErrorTitle.USER_TASK_INVALID.getI18n(), userTask,
                        ErrorDesc.USER_TASK_DUPLICATE_NAME.getI18n(userTask.getName()));
            }
        }
    }

    protected boolean duplicateName(Collection<UserTask> userTasks, String id, String name) {
        for (UserTask userTask : userTasks) {
            if (id != null && userTask.getId() != null) {
                if (name.equals(userTask.getName()) && !id.equals(userTask.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

}
