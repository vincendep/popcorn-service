package it.vincendep.popcorn.common;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Configuration
@ComponentScan
@Retention(RetentionPolicy.RUNTIME)
public @interface Module {}
