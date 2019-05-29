package com.songyz.flowable.validator.bpmn;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.ScriptTask;
import org.flowable.validation.ValidationError;

import com.songyz.flowable.validator.i1stcs.AProcessLevelValidator;
import com.songyz.flowable.validator.i1stcs.ErrorDesc;
import com.songyz.flowable.validator.i1stcs.ErrorTitle;

/**
 * 脚本任务校验器
 * 
 * @author songyz
 * @createTime 2019-01-05 03:45:39
 */
public class ScriptTaskValidator extends AProcessLevelValidator {

    @Override
    protected void executeValidation(BpmnModel bpmnModel, Process process, List<ValidationError> errors) {
        List<ScriptTask> scriptTasks = process.findFlowElementsOfType(ScriptTask.class);
        for (ScriptTask scriptTask : scriptTasks) {
            if (StringUtils.isEmpty(scriptTask.getScript())) {
                addError(errors, ErrorTitle.SCRIPT_TASK_INVALID, process, scriptTask,
                        ErrorDesc.SCRIPT_TASK_MISSING_SCRIPT);
            }
        }
    }

}