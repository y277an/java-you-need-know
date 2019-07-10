package com.xiaohui.minimybatis.annotations;

import java.lang.annotation.Annotation;

/**
 * @Author: xiaohui
 * @Description:
 */
public class AnnotationUtil {

    /**
     * 获取指定类型注解的value值
     * @param annotation
     * @return
     */
    public static String getAnnotationValue(Annotation annotation) {
        String value = null;
        if (annotation instanceof Select) {
            value = ((Select) annotation).value();
        } else if (annotation instanceof Insert) {
            value = ((Insert) annotation).value();
        } else if (annotation instanceof Table) {
            value = ((Table) annotation).value();
        }
        return value;
    }

}
