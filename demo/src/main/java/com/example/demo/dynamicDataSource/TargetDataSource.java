package com.example.demo.dynamicDataSource;

import java.lang.annotation.*;

/**
 * 注解在类或者方法上都行
 * @author Administrator
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
    String name();
}
