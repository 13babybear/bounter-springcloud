package cn.bounter.common.model;

import java.lang.annotation.*;


/**
 * 应用日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AppLog {

    /**
     * 日志主题
     * @return
     */
    AppLogSubjectEnum subject();

    /**
     * 日志内容
     * @return
     */
    String content() default "";
}
