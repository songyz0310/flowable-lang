/**
 * 
 */
package com.songyz.flowable.validator.i1stcs;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.EndEvent;
import org.flowable.bpmn.model.ExclusiveGateway;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.StartEvent;
import org.flowable.bpmn.model.UserTask;
import org.flowable.validation.ValidationError;

/**
 * 节点出入序列流校验器
 * 
 * @author songyz<br>
 * @createTime 2019-01-09 19:40:03
 */
public class NodeAssociationValidator extends AProcessLevelValidator {

    @Override
    protected void executeValidation(BpmnModel bpmnModel, Process process, List<ValidationError> errors) {

        validateStartEvent(errors, process);

        validateEndEvent(errors, process);

        validateUserTask(errors, process);

        validateExclusiveGateway(errors, process);

    }

    private void validateExclusiveGateway(List<ValidationError> errors, Process process) {
        process.findFlowElementsOfType(ExclusiveGateway.class, false).forEach(exclusiveGateway -> {
            // 排他网关必须包含传出和传入序列流
            if (exclusiveGateway.getIncomingFlows().isEmpty() || exclusiveGateway.getOutgoingFlows().isEmpty()) {
                addError(errors, ErrorTitle.EXCLUSIVE_GATEWAY_INVALID, process, exclusiveGateway,
                        ErrorDesc.EXCLUSIVE_GATEWAY_NO_SEQ_FLOW);
            }
            // 只包含一条输出流，不可以设置表达式
            else if (exclusiveGateway.getOutgoingFlows().size() == 1) {
                if (StringUtils.isNotEmpty(exclusiveGateway.getOutgoingFlows().get(0).getConditionExpression())) {
                    addError(errors, ErrorTitle.EXCLUSIVE_GATEWAY_INVALID, process, exclusiveGateway,
                            ErrorDesc.EXCLUSIVE_GATEWAY_CONDITION_NOT_ALLOWED_ON_SINGLE_SEQ_FLOW);
                }
            }
            else {
                // TODO 排他网关多分支暂不检验，设计器，屏蔽配置表达式入口

                // 多条输出流，表达式不能重复，默认序列流不能包含表达式

                // String defaultSequenceFlow = exclusiveGateway.getDefaultFlow();
                //
                // List<SequenceFlow> flowsWithoutCondition = new ArrayList<>();
                // for (SequenceFlow flow : exclusiveGateway.getOutgoingFlows()) {
                // String condition = flow.getConditionExpression();
                // boolean isDefaultFlow = flow.getId() != null && flow.getId().equals(defaultSequenceFlow);
                // boolean hasCondition = StringUtils.isNotEmpty(condition);
                //
                // if (!hasCondition && !isDefaultFlow) {
                // flowsWithoutCondition.add(flow);
                // }
                // if (hasCondition && isDefaultFlow) {
                // addError(errors, ErrorTitle.EXCLUSIVE_GATEWAY_INVALID, process, exclusiveGateway,
                // ErrorDesc.EXCLUSIVE_GATEWAY_CONDITION_ON_DEFAULT_SEQ_FLOW);
                // }
                // }
                //
                // if (!flowsWithoutCondition.isEmpty()) {
                // addWarning(errors, ErrorTitle.EXCLUSIVE_GATEWAY_INVALID, process, exclusiveGateway,
                // ErrorDesc.EXCLUSIVE_GATEWAY_SEQ_FLOW_WITHOUT_CONDITIONS);
                // }
            }

        });
    }

    protected void validateStartEvent(List<ValidationError> errors, Process process) {
        process.findFlowElementsOfType(StartEvent.class, false).forEach(startEvent -> {
            // 开始事件必须包含传出序列流
            if (startEvent.getOutgoingFlows().isEmpty()) {
                addError(errors, ErrorTitle.START_EVENT_INVALID, process, startEvent,
                        ErrorDesc.START_EVENT_NO_OUTGOING_SEQ_FLOW);
            }
            // 只包含一条输出流，不可以设置表达式
            else if (startEvent.getOutgoingFlows().size() == 1) {
                if (StringUtils.isNotEmpty(startEvent.getOutgoingFlows().get(0).getConditionExpression())) {
                    addError(errors, ErrorTitle.START_EVENT_INVALID, process, startEvent,
                            ErrorDesc.START_EVENT_CONDITION_NOT_ALLOWED_ON_SINGLE_SEQ_FLOW);
                }
            }
            // 开始节点不可以产生多条输出流
            else {
                addError(errors, ErrorTitle.START_EVENT_INVALID, process, startEvent,
                        ErrorDesc.START_EVENT_HAS_MORE_OUTGOING_SEQ_FLOW);
            }

        });
    }

    private void validateUserTask(List<ValidationError> errors, Process process) {
        process.findFlowElementsOfType(UserTask.class, false).forEach(userTask -> {
            // 人工任务必须包含传出和传入序列流
            if (userTask.getIncomingFlows().isEmpty() || userTask.getOutgoingFlows().isEmpty()) {
                addError(errors, ErrorTitle.USER_TASK_INVALID, process, userTask, ErrorDesc.USER_TASK_NO_SEQ_FLOW);
            }
            // 只包含一条输出流，不可以设置表达式
            else if (userTask.getOutgoingFlows().size() == 1) {
                if (StringUtils.isNotEmpty(userTask.getOutgoingFlows().get(0).getConditionExpression())) {
                    addError(errors, ErrorTitle.USER_TASK_INVALID, process, userTask,
                            ErrorDesc.USER_TASK_CONDITION_NOT_ALLOWED_ON_SINGLE_SEQ_FLOW);
                }
            }
            // TODO 多条输出流，表达式不能重复
            else {

            }
        });
    }

    private void validateEndEvent(List<ValidationError> errors, Process process) {
        process.findFlowElementsOfType(EndEvent.class, false).forEach(endEvent -> {
            // 结束事件必须包含传入序列流
            if (endEvent.getIncomingFlows().isEmpty()) {
                addError(errors, ErrorTitle.END_EVENT_INVALID, process, endEvent,
                        ErrorDesc.END_EVENT_NO_INGOING_SEQ_FLOW);
            }
        });
    }

}
