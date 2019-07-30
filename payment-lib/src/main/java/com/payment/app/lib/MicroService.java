package com.payment.app.lib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.payment.app.lib")
@EntityScan(basePackages = "com.payment.app.lib")
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MicroService {

}
