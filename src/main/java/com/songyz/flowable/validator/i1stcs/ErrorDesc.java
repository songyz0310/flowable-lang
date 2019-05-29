/**
 * 
 */
package com.songyz.flowable.validator.i1stcs;

import java.util.Locale;

import com.songyz.flowable.common.ThreadContext;
import com.songyz.flowable.common.WorkflowI18n;

/**
 * 
 * @author songyz<br>
 * @createTime 2019-01-09 11:37:04
 */
public enum ErrorDesc {

    // 1001xxx 节点重复异常
    MULTIPLE_START_EVENTS_NOT_SUPPORTED(1001_001), // 不支持多个启动事件
    // 1002xxx 缺少必要节点
    MISSING_NECESSARY_NODES_STARTEVENT(1002_001), // 完整的流程必须包含开始事件
    MISSING_NECESSARY_NODES_USERTASK(1002_002), // 完整的流程必须包含任务节点
    MISSING_NECESSARY_NODES_ENDEVENT(1002_003), // 完整的流程必须包含结束事件
    // 1003xxx 流程定义校验
    PROCESS_DEFINITION_NOT_EXECUTABLE(1003_001), // 流程定义不可执行。请确认这是故意的。
    ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE(1003_002), // "所有流程定义都设置为不可执行（流程的属性“IsExecutable”）。这是不允许的。"
    ERROR_MISSING_ERROR_CODE(1003_003), // 无效错误代码：空错误代码
    OPERATION_INVALID_IN_MESSAGE_REFERENCE(1003_004), // 操作的inMessageRef无效
    MULTI_INSTANCE_MISSING_COLLECTION(1003_005), // 必须设置loopcardinality或loopdatainputref/flowable:collection
    MULTI_INSTANCE_MISSING_COLLECTION_FUNCTION_PARAMETERS(1003_006), // flowable:collection元素字符串值需要函数参数flowable:delegateexpression或flowable:class。
    DATA_ASSOCIATION_MISSING_TARGETREF(1004_007), // 数据关联上需要TargetRef
    // 1004xxx 流程存在参数过长
    PROCESS_DEFINITION_ID_TOO_LONG(1004_001), // 流程定义的ID不能包含超过 {0} 个字符
    PROCESS_DEFINITION_NAME_TOO_LONG(1004_002), // 流程定义的名称不能包含超过 {0} 个字符
    PROCESS_DEFINITION_DOCUMENTATION_TOO_LONG(1004_003), // 流程定义的文档不得包含超过 {0} 个字符
    BPMN_MODEL_TARGET_NAMESPACE_TOO_LONG(1004_004), // bpmn模型的命名空间不能包含超过 {0} 个字符
    FLOW_ELEMENT_ID_TOO_LONG(1004_005), // 流元素的ID不能包含超过 {0} 个字符 
    USER_TASK_NAME_TOO_LONG(1004_006), // 任务的名称不能包含超过 {0} 个字符 

    // 2001xxx 关联校验失败
    ASSOCIATION_INVALID_SOURCE_REFERENCE(2001_001), // 关联元素缺少属性“sourceref”
    ASSOCIATION_INVALID_TARGET_REFERENCE(2001_002), // 关联元素缺少属性“targetref”

    // 3001xxx 数据对象异常
    DATA_OBJECT_MISSING_NAME(3001_001), // 数据对象的名称是必需的

    // 4001xxx 流程图异常
    DI_INVALID_REFERENCE(4001_001), // 关系图交换定义中的引用无效：找不到 {0}
    DI_DOES_NOT_REFERENCE_FLOWNODE(4001_002), // 图表交换定义中的引用无效：{0} 不引用流节点
    DI_DOES_NOT_REFERENCE_SEQ_FLOW(4001_003), // 图表交换定义中的引用无效：{0} 不引用序列流

    // 5001xxx 网关校验
    // 5002xxx 互斥网关校验
    EXCLUSIVE_GATEWAY_NO_SEQ_FLOW(5002_001), // 排他网关没有传出序列流或传入序列流
    EXCLUSIVE_GATEWAY_CONDITION_NOT_ALLOWED_ON_SINGLE_SEQ_FLOW(5002_002), // 排他网关只有一个传出序列流。这不允许有条件。
    EXCLUSIVE_GATEWAY_CONDITION_ON_DEFAULT_SEQ_FLOW(5002_003), // 默认SequenceFlow具有不允许的条件
    EXCLUSIVE_GATEWAY_SEQ_FLOW_WITHOUT_CONDITIONS(5002_004), // 排他网关至少有一个没有条件的传出序列流（不是默认的）
    // 5003xxx 并行网关校验
    // 5004xxx 相容网关校验
    // 5005xxx 事件网关校验
    EVENT_GATEWAY_ONLY_CONNECTED_TO_INTERMEDIATE_EVENTS(5005_001), // 基于事件的网关只能连接到IntermediateCatchEvent类型的元素
    // 5006xxx 复杂网关校验

    // 6001xxx 事件校验
    // 6002xxx 开始事件校验
    START_EVENT_INVALID_EVENT_DEFINITION(6002_001), // 启动事件上不支持的事件定义
    START_EVENT_NO_OUTGOING_SEQ_FLOW(6002_002), // 开始事件没有传出序列流
    START_EVENT_HAS_MORE_OUTGOING_SEQ_FLOW(6002_003), // 开始事件只能有一个传出序列流。
    START_EVENT_CONDITION_NOT_ALLOWED_ON_SINGLE_SEQ_FLOW(6002_004), // 开始事件只有一个传出序列流。这不允许有条件。
    START_EVENT_NOT_ALLOWED_ADJACENT_TO_EXCLUSIVEGATEWAY (6002_005), // 开始事件后不能紧邻互斥网关。
    
    // 6003xxx 边界事件校验
    BOUNDARY_EVENT_INVALID_EVENT_DEFINITION(6003_001), // 事件定义无效或不受支持
    BOUNDARY_EVENT_CANCEL_ONLY_ON_TRANSACTION(6003_002), // 只有事务子流程支持具有CancelEventDefinition的边界事件
    BOUNDARY_EVENT_NO_EVENT_DEFINITION(6003_003), // 边界事件中缺少事件定义
    BOUNDARY_EVENT_MULTIPLE_CANCEL_ON_TRANSACTION(6003_004), // 同一事务子流程不支持具有CancelEventDefinition的多个边界事件。
    COMPENSATE_EVENT_MULTIPLE_ON_BOUNDARY(6003_005), // “compensate”类型的多个边界事件无效
    MESSAGE_EVENT_MULTIPLE_ON_BOUNDARY_SAME_MESSAGE_ID(6003_006), // 不支持具有相同消息ID的多个消息事件
    // 6004xxx 结束事件校验
    END_EVENT_CANCEL_ONLY_INSIDE_TRANSACTION(6004_001), // 只有事务子流程内部支持带CancelEventDefinition的结束事件
    END_EVENT_NO_INGOING_SEQ_FLOW(6004_002), // 结束事件没有传入序列流
    // 6005xxx 中间捕获事件校验
    INTERMEDIATE_CATCH_EVENT_NO_EVENTDEFINITION(6005_001), // 中间catch事件没有事件定义
    INTERMEDIATE_CATCH_EVENT_INVALID_EVENTDEFINITION(6005_002), // 不支持的中间catch事件类型
    // 6006xxx 中间投掷事件
    THROW_EVENT_INVALID_EVENTDEFINITION(6006_001), // 不支持的中间引发事件类型

    // 7001xxx 任务校验
    // 7002xxx 人工任务校验
    USER_TASK_MISSING_NAME(7002_001), // 任务名称不能为空
    USER_TASK_LISTENER_IMPLEMENTATION_MISSING(7002_002), // 元素“class”或“expression”在ExecutionListener上是必需的
    USER_TASK_NO_SEQ_FLOW(7002_003), // 任务节点没有传出序列流或传入序列流
    USER_TASK_CONDITION_NOT_ALLOWED_ON_SINGLE_SEQ_FLOW(7002_004), // 任务节点只有一个传出序列流。这不允许有条件。
    USER_TASK_MISSING_ID(7002_005), // 任务ID不能为空
    USER_TASK_DUPLICATE_NAME(7002_006), // 找到重复的任务名称“{0}”
    // 7003xxx 邮件任务校验
    MAIL_TASK_NO_RECIPIENT(7003_001), // 邮件任务中未定义收件人
    MAIL_TASK_NO_CONTENT(7003_002), // 应提供text、html、textvar或htmlvar字段
    // 7004xxx Shell脚本任务校验
    SHELL_TASK_INVALID_PARAM(7004_001), // Shell字段的未定义参数值
    SHELL_TASK_NO_COMMAND(7004_002), // 没有在shell任务上定义shell命令
    // 7005xxx DMN任务校验
    DMN_TASK_NO_KEY(7005_001), // 没有在DMN任务上定义决策表引用键
    // 7006xxx HTTP任务校验
    HTTP_TASK_NO_REQUEST_METHOD(7006_001), // 没有在HTTP任务上定义请求方法
    HTTP_TASK_NO_REQUEST_URL(7006_002), // 没有在HTTP任务上定义请求URL
    // 7007xxx Script 脚本任务校验
    SCRIPT_TASK_MISSING_SCRIPT(7007_001), // 没有为脚本任务提供脚本
    // 7008xxx 发送任务校验
    SEND_TASK_INVALID_IMPLEMENTATION(7008_001), // sendtask的属性'type'或'operation'之一是必需的
    SEND_TASK_INVALID_TYPE(7008_002), // 发送任务的类型无效或不受支持
    SEND_TASK_WEBSERVICE_INVALID_OPERATION_REF(7008_003), // 发送任务的操作引用无效
    // 7009xxx 服务任务校验
    SERVICE_TASK_MISSING_IMPLEMENTATION(7009_001), // “class”、“delegateexpression”、“type”、“operation”或“expression”属性之一对服务任务是必需的。
    SERVICE_TASK_INVALID_TYPE(7009_002), // 服务任务类型无效或不受支持
    SERVICE_TASK_RESULT_VAR_NAME_WITH_DELEGATE(7009_003), // 使用“class”或“delegateExpression”的服务任务不支持“resultvariablename”
    SERVICE_TASK_USE_LOCAL_SCOPE_FOR_RESULT_VAR_WITHOUT_RESULT_VARIABLE_NAME(7009_004), // 已设置“useLocalScopeForResultVariable”，但未设置“ResultVariableName”。不使用该属性
    SERVICE_TASK_WEBSERVICE_INVALID_OPERATION_REF(7009_005), // 操作引用无效

    // 8001xxx 顺序流校验
    SEQ_FLOW_INVALID_SRC(8001_001), // 序列流的源无效
    SEQ_FLOW_INVALID_TARGET(8001_002), // 序列流的目标无效
    SEQ_FLOW_INVALID_TARGET_SOURCE(8001_003), // 序列流的目标无效，未在与源相同的范围内定义该目标

    // 9001xxx 流程信号校验
    SIGNAL_EVENT_MISSING_SIGNAL_REF(9001_001), // SignalEventDefinition没有强制属性“SignalRef”
    SIGNAL_EVENT_INVALID_SIGNAL_REF(9001_002), // “signalRef”无效：在模型中找不到具有该ID的信号
    EVENT_TIMER_MISSING_CONFIGURATION(9001_003), // 计时器需要配置（需要TimeDate、TimeCycle或TimeDuration）
    COMPENSATE_EVENT_INVALID_ACTIVITY_REF(9001_004), // “ActivityRef”的属性值无效：没有具有给定ID的活动
    SIGNAL_MISSING_ID(9001_005), // 信号必须有ID
    SIGNAL_MISSING_NAME(9001_006), // 型号必须有名称
    SIGNAL_DUPLICATE_NAME(9001_007), // 找到重复的信号名称“{0}”
    SIGNAL_INVALID_SCOPE(9001_008), // “scope”的值无效。仅支持值“global”和“processInstance”
    // 9002xxx 子流程校验
    EVENT_SUBPROCESS_INVALID_START_EVENT_DEFINITION(9002_001), // 事件子流程的开始事件必须是“错误”、“计时器”、“消息”或“信号”类型
    SUBPROCESS_MULTIPLE_START_EVENTS(9002_002), // 子流程不支持多个启动事件
    SUBPROCESS_START_EVENT_EVENT_DEFINITION_NOT_ALLOWED(9002_003), // 仅当子流程是事件子流程时，才允许在Start事件上使用事件定义
    // 9003xxx 事件监听器校验
    EVENT_LISTENER_INVALID_THROW_EVENT_TYPE(9003_001), // 事件侦听器上的引发事件类型无效或不受支持
    EVENT_LISTENER_IMPLEMENTATION_MISSING(9003_002), // 元素“class”、“delegateExpression”或“throwEvent”在EventListener上是必需的
    EVENT_LISTENER_INVALID_IMPLEMENTATION(9003_003), // 不支持事件侦听器的实现类型
    // 9004xxx 执行监听器校验
    EXECUTION_LISTENER_IMPLEMENTATION_MISSING(9004_001), // 元素“class”或“expression”在ExecutionListener上是必需的
    EXECUTION_LISTENER_INVALID_IMPLEMENTATION_TYPE(9004_002), // 使用“onTransaction”时不能使用表达式
    // 9005xxx 流程消息校验
    MESSAGE_EVENT_MISSING_MESSAGE_REF(9005_001), // 需要属性“messageref”
    MESSAGE_EVENT_INVALID_MESSAGE_REF(9005_002), // “messageref”无效：在模型中找不到具有该ID的消息
    MESSAGE_INVALID_ITEM_REF(9005_003), // 项引用无效：未找到

    ;

    private final int code;

    private ErrorDesc(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getI18n() {
        return WorkflowI18n.getInstance().getMessage(ThreadContext.getLanguage().getLocale(),
                WorkflowI18n.VALIDATE_DESCRIPTION_PREFIX + code);
    }

    public String getI18n(Object... args) {
        return WorkflowI18n.getInstance().getMessage(ThreadContext.getLanguage().getLocale(),
                WorkflowI18n.VALIDATE_DESCRIPTION_PREFIX + code, args);
    }

    public String getI18n(Locale lang, Object... args) {
        return WorkflowI18n.getInstance().getMessage(lang, WorkflowI18n.VALIDATE_DESCRIPTION_PREFIX + code, args);
    }
}
