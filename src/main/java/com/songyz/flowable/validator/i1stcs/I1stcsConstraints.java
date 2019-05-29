/**
 * 
 */
package com.songyz.flowable.validator.i1stcs;

/**
 * 约束常量
 * 
 * @author songyz<br>
 * @createTime 2019-01-10 17:59:02
 */
public class I1stcsConstraints {

    // Max length database field ACT_RE_PROCDEF.CATEGORY
    protected static final int BPMN_MODEL_TARGET_NAMESPACE_MAX_LENGTH = 255;

    // Max length database field ACT_RE_PROCDEF.KEY
    protected static final int PROCESS_DEFINITION_ID_MAX_LENGTH = 255;

    // Max length database field ACT_RE_PROCDEF.NAME
    protected static final int PROCESS_DEFINITION_NAME_MAX_LENGTH = 255;

    // Max length of database field ACT_RE_PROCDEF.DESCRIPTION
    protected static final int PROCESS_DEFINITION_DOCUMENTATION_MAX_LENGTH = 2000;

    // FlowElement.id
    protected static final int PROCESS_ELEMENT_ID_MAX_LENGTH = 255;
    
    // user task name
    protected static final int PROCESS_USER_TASK_NAME_MAX_LENGTH = 32;
}
