package com.xiaohui.ioc.beans.factory.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)// 作用在字段上面
@Documented
public @interface Autowired {
    String value() default "";
}
