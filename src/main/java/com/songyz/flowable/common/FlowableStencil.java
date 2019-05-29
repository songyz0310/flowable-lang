package com.songyz.flowable.common;

import java.util.Arrays;
import java.util.List;

import com.songyz.flowable.enums.WorkflowConst.FlowStepExt;

/**
 * 流程定义扩展属性
 * 
 * @author songyz<br>
 * @createTime 2019-01-07 02:59:30
 */
public final class FlowableStencil {

    public static final String NAMESPACE = "http://www.1stcs.cn";
    public static final String NAMESPACE_PREFIX = "i1stcs";

    // flowable扩展属性
    public static final List<String> KEYS = Arrays.asList(//
            FlowStepExt.NEXT_STEPS, //
            FlowStepExt.STEP_TIP, //
            FlowStepExt.STEP_SLA_CODE, //
            FlowStepExt.STEP_ICON_PATH//
    );

}
