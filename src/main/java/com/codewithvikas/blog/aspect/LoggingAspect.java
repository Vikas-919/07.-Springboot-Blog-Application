package com.codewithvikas.blog.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LoggingAspect {

	// Setup Logger
	private Logger myLogger = Logger.getLogger(getClass().getName());
	
	// Setup Pointcut Declarations
	@Pointcut("execution(* com.codewithvikas.blog.controller.*.*(..))")
	private void forControllerPackage() {}
	
	// For Service Package
	@Pointcut("execution(* com.codewithvikas.blog.service.impl.*.*(..))")
	private void forServicePackage() {}
	
	// For Repository Package...
	@Pointcut("execution(* com.codewithvikas.blog.repository.*.*(..))")
	private void forRepositoryPackage() {}
	
	// Combine pointcut expressions
	@Pointcut("forControllerPackage() || forServicePackage() || forRepositoryPackage()")
	private void forAppFlow() {}
	
	// Add @Before Advice
	@Before("forAppFlow()")
	public void before(JoinPoint theJoinPoint) {
		
		//Display method, client is calling
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("=====>> in @Before: calling method: " + theMethod);
		
		//Display the arguments to the method
		//Get the arguments
		Object[] args = theJoinPoint.getArgs();
		
		//Loop thru and display arguments
		for(Object tempArg : args) {
			myLogger.info("=====>> argument: " + tempArg);
		}	
	}
	
	// Add @AfterReturning Advice
	@AfterReturning(
			pointcut = "forAppFlow()",
			returning = "theResult")
	public void afterReturning(JoinPoint theJoinPoint, Object theResult) {
		
		//Display method we are returning from
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("=====>> in @AfterReturning: from method: " + theMethod);
		
		//Display data returned
		myLogger.info("=====>> result: " + theResult);
		
	}
}
