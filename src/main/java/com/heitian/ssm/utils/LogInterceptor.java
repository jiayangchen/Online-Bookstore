package com.heitian.ssm.utils;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogInterceptor {
    private final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);
    @Before(value = "execution(* com.heitian.ssm.controller.*.*(..))")
    public void before(){
        logger.info("login start!");
    }
    @After(value = "execution(* com.heitian.ssm.controller.*.*(..))")
    public void after(){
        logger.info("login end!");
    }
}
