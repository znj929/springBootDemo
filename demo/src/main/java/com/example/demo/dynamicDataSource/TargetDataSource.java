package com.example.demo.dynamicDataSource;

import java.lang.annotation.*;

/**
 * 数据源切换自定义注解
 * 注解在类或者方法上都行
 * @author znj
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
    String name();
}
