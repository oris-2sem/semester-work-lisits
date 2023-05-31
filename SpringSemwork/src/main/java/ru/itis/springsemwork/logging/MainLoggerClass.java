package ru.itis.springsemwork.logging;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class MainLoggerClass {
    @Pointcut("within(ru.itis.springsemwork..*)&& !execution(* ru.itis.springsemwork..security..*(..)) && !execution(* ru.itis.springsemwork..dto.converters..*(..))")
    public void logMethods() {

    }

    @Pointcut("bean(*Controller) && !execution(* ru.itis.springsemwork..security..*(..))")
    public void logController() {

    }
}
