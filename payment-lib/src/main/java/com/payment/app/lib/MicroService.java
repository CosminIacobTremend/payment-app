package com.payment.app.lib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "com.payment.app.lib")
@EnableJpaRepositories(basePackages = "com.payment.app.lib.repository" )
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MicroService {

}
