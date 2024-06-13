package org.springframework.samples.petclinic.chat;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class TraceAspect {

	@Pointcut("execution(* dev.langchain4j.model.chat.ChatLanguageModel.generate*(..))")
	public void callModel() {
	}

	@Around("callModel()")
	public Object aroundChatLanguageModelGenerateMethods(ProceedingJoinPoint joinPoint) throws Throwable {

		Object[] args=joinPoint.getArgs();
		System.out.println("*****************************************");
		System.out.println("Call the model: " + Arrays.toString(args));

		Object result = joinPoint.proceed();

		System.out.println("*****************************************");
		System.out.println("Model Result: " + result);

		return result;
	}

	@Pointcut("execution(* dev.langchain4j.rag.content.retriever.ContentRetriever.retrieve*(..))")
	public void contentRetriever() {
	}

	@Around("contentRetriever()")
	public Object aroundContentRetrieverRetrieveMethods(ProceedingJoinPoint joinPoint) throws Throwable {

		Object[] args=joinPoint.getArgs();

		System.out.println("*****************************************");
		System.out.println("Query String: " + Arrays.toString(args));

		Object result = joinPoint.proceed();

		System.out.println("*****************************************");
		System.out.println("Query Result: " + result);

		return result;
	}
}
