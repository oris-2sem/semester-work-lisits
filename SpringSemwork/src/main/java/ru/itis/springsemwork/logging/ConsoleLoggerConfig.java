package ru.itis.springsemwork.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.UUID;

@Aspect
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "logger", value = "enabled", havingValue = "false", matchIfMissing = true)
public class ConsoleLoggerConfig {
    @Bean
    public MainLoggerClass loggingAspect() {
        return new MainLoggerClass();
    }

    @Before(value = "ru.itis.springsemwork.logging.MainLoggerClass.logController()")
    public void controllerLog(JoinPoint joinPoint) {
        String requestId = Optional.ofNullable(MDC.get("requestId"))
                .orElse(UUID.randomUUID().toString());
        log.info("Get request with id {} \n Method used: {}", requestId, joinPoint.getSignature());
    }

    @Before(value = "ru.itis.springsemwork.logging.MainLoggerClass.logMethods()")
    public void methodStart(JoinPoint joinPoint) {
        log.info("method [{}] call args: [{}]", joinPoint.getSignature(), joinPoint.getArgs());
    }

    @AfterThrowing(value = "ru.itis.springsemwork.logging.MainLoggerClass.logMethods()", throwing = "exception")
    public void exceptionLog(JoinPoint joinPoint, Throwable exception) {
        String requestId = Optional.ofNullable(MDC.get("requestId"))
                .orElse(UUID.randomUUID().toString());
        log.error("method, called by request {}, [{}] throw exception", requestId, joinPoint.getSignature(), exception);
    }
}
