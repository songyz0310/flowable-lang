package com.songyz.flowable.common;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.songyz.flowable.common.Constants.Language;

/**
 * 当前线程上下文的状态参数，主要是外部请求携带的头部用户配置和身份信息 注意：如果在一个请求处理线程中又开启新的线程，上下文信息将拷贝到新线程中，但在子线程中加入或修改的数据不会复制回父线程
 * 
 * @author wujin CreateTime: 2018-07-08 11:16
 */
public class ThreadContext {
    private static ThreadLocal<Context> tlEnv = new InheritableThreadLocal<Context>();

    private static Context getContext() {
        Context context = tlEnv.get();
        if (Objects.isNull(context)) {
            context = new Context();
            tlEnv.set(context);
        }

        return context;
    }

    static void clean() {
        tlEnv.remove();
    }

    public static Context get() {
        return getContext();
    }

    public static void setTimeZone(int timeZone) {
        Context context = getContext();
        context.timeZone = timeZone;
    }

    public static Language getLanguage() {
        return getContext().language;
    }

    public static void setLanguage(String lang) {
        Context context = getContext();
        context.language = Objects.isNull(Language.valueOf(lang)) ? Language.ZH : Language.valueOf(lang);
    }

    public static int getTimeZone() {
        return getContext().timeZone;
    }

    public static long getTraceTime() {
        return getContext().traceTime;
    }

    public static class Context {
        int timeZone = -8;
        long traceTime = System.currentTimeMillis();

        Language language = Language.ZH;

        void setLang(String lang) {
            if (StringUtils.isEmpty(lang)) {
                language = Language.ZH;
            }
            else {
                lang = lang.toUpperCase();
                try {
                    language = Objects.isNull(Language.valueOf(lang)) ? Language.ZH : Language.valueOf(lang);
                }
                catch (Exception exp) {
                    language = Language.ZH;
                }
            }
        }

        void setTimeZone(String timeZone) {
            if (StringUtils.isEmpty(timeZone)) {
                this.timeZone = -8;
            }
            else {
                try {
                    this.timeZone = Integer.parseInt(timeZone);
                }
                catch (Exception exp) {
                    this.timeZone = -8;
                }
            }
        }

    }
}
