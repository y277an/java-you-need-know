package com.xiaohui.minimybatis.annotation;

import java.lang.annotation.*;

/**
 * @Author: xiaohui
 * @Description: sql的table注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Table {

    String value();

}
