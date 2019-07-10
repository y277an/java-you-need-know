package com.xiaohui.minimybatis.annotations;

import java.lang.annotation.*;

/**
 * @Author: xiaohui
 * @Description: sql的select注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Select{

    String value();

}
