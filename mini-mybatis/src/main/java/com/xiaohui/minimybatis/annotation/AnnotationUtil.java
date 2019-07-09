package com.xiaohui.minimybatis.annotation;

import java.lang.annotation.Annotation;

/**
 * @Author: xiaohui
 * @Description:
 */
public class AnnotationUtil {

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
