package com.xiaohui.ioc.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)// 作用在字段上面
@Documented
public @interface Autowired {
    String value() default "";
}
