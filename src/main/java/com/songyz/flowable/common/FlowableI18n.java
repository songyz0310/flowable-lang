/**
 * 
 */
package com.songyz.flowable.common;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 
 * @author songyz<br>
 * @createTime 2019-01-07 03:35:14
 */
public class FlowableI18n {

    private static final Object[] empty = new Object[] {};
    private static final String baseName = "com.songyz.flowable.i18n.message";

    public static final String VALIDATE_PROBLEM_PREFIX = "validate.problem.";// 流程校验异常名称
    public static final String VALIDATE_DESCRIPTION_PREFIX = "validate.description.";// 流程校验异常描述

    private static FlowableI18n self = new FlowableI18n();

    private FlowableI18n() {
    }

    public static FlowableI18n getInstance() {
        return self;
    }

    public String getMessage(String code) {
        return getMessage(Locale.CHINESE, code, empty);
    }

    public String getMessage(Locale lang, String code) {
        return getMessage(lang, code, empty);
    }

    public String getMessage(Locale lang, String code, Object... args) {
        ResourceBundle bundle = ResourceBundle.getBundle(baseName, lang);
        return MessageFormat.format(bundle.getString(code), args);
    }

}
