package com.songyz.flowable.common;

import java.util.Arrays;
import java.util.List;

/**
 * 流程定义扩展属性
 * 
 * @author songyz<br>
 * @createTime 2019-01-07 02:59:30
 */
public final class FlowableStencil {

    public static final String NAMESPACE = "http://www.1stcs.cn";
    public static final String NAMESPACE_PREFIX = "i1stcs";

    public static final String NEXT_STEPS = "next_steps";// 临近的下一级元素
    public static final String NEXT_GATEWAY = "next_gateway";// 临近的下一级网关
    public static final String STEP_TIP = "step_tip";// 步骤提示
    public static final String STEP_SLA_CODE = "step_sla_code";// 步骤sla标签code
    public static final String STEP_ICON_PATH = "step_icon_path";// 步骤图标路径

    // flowable扩展属性
    public static final List<String> KEYS = Arrays.asList(//
            NEXT_STEPS, //
            NEXT_GATEWAY, //
            STEP_TIP, //
            STEP_SLA_CODE, //
            STEP_ICON_PATH);

}
