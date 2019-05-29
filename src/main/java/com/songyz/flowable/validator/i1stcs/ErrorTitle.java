/**
 * 
 */
package com.songyz.flowable.validator.i1stcs;

import java.util.Locale;

import com.songyz.flowable.common.ThreadContext;
import com.songyz.flowable.common.WorkflowI18n;

/**
 * 异常分类
 * 
 * @author songyz<br>
 * @createTime 2019-01-09 11:36:45
 */
public enum ErrorTitle {

    // 1xxx基础异常
    FOUND_NODE_MULTIPLE(1001), // 节点重复异常
    MISSING_NECESSARY_NODES(1002), // 缺少必要节点
    PROCESS_DEFINITION_INVALID(1003), // 流程定义校验
    PROCESS_DEFINITION_PARAM_TOO_LONG(1004), // 流程存在参数过长

    // 2xxx关联异常
    ASSOCIATION_INVALID(2001), //
    // 3xxx数据对象异常
    DATA_OBJECT_INVALID(3001), //
    // 4xxx流程图异常
    DI_INVALID(4001),
    // 5xxx网关异常
    GATEWAY_INVALID(5001), // 网关校验
    EXCLUSIVE_GATEWAY_INVALID(5002), // 互斥网关校验
    PARALLEL_GATEWAY_INVALID(5003), // 并行网关校验
    INCLUSIVE_GATEWAY_INVALID(5004), // 相容网关校验
    EVENT_GATEWAY_INVALID(5005), // 事件网关校验
    COMPLEX_GATEWAY_INVALID(5006), // 复杂网关校验

    // 6xxx事件异常
    EVENT_INVALID(6001), // 事件校验
    START_EVENT_INVALID(6002), // 开始事件校验
    BOUNDARY_EVENT_INVALID(6003), // 边界事件校验
    END_EVENT_INVALID(6004), // 结束事件校验
    INTERMEDIATE_CATCH_EVENT_INVALID(6005), // 中间捕获事件
    THROW_EVENT_INVALID(6006), // 中间投掷事件

    // 7xxx任务异常
    TASK_INVALID(7001), // 任务校验
    USER_TASK_INVALID(7002), // 任务校验
    MAIL_TASK_INVALID(7003), // 邮件任务校验
    SHELL_TASK_INVALID(7004), // Shell脚本任务校验
    DMN_TASK_INVALID(7005), // DMN任务校验
    HTTP_TASK_INVALID(7006), // HTTP任务校验
    SCRIPT_TASK_INVALID(7007), // Script 脚本任务校验
    SEND_TASK_INVALID(7008), // 发送任务校验
    SERVICE_TASK_INVALID(7009), // 服务任务校验

    // 8xxx顺序流异常
    SEQ_FLOW_INVALID(8001), //
    // 9xxx其他异常
    SIGNAL_INVALID(9001), // 流程信号校验
    SUBPROCESS_INVALID(9002), // 子流程校验
    EVENT_LISTENER_INVALID(9003), // 事件监听器校验
    EXECUTION_LISTENER_INVALID(9004), // 执行监听器校验
    MESSAGE_INVALID(9005), // 流程消息校验
    ;

    private final int code;

    private ErrorTitle(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getI18n() {
        return WorkflowI18n.getInstance().getMessage(ThreadContext.getLanguage().getLocale(),
                WorkflowI18n.VALIDATE_PROBLEM_PREFIX + code);
    }

    public String getI18n(Object... args) {
        return WorkflowI18n.getInstance().getMessage(ThreadContext.getLanguage().getLocale(),
                WorkflowI18n.VALIDATE_PROBLEM_PREFIX + code, args);
    }

    public String getI18n(Locale lang, Object... args) {
        return WorkflowI18n.getInstance().getMessage(lang, WorkflowI18n.VALIDATE_PROBLEM_PREFIX + code, args);
    }
}
