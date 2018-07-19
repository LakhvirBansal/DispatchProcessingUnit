//package com.dpu.interceptor;
//
//import java.io.IOException;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//@Component
//@Aspect
//public class AuthenticationInterceptor {
//
//	private static final String EXECUTION_PACKAGES = "execution(* com.dpu.controller.web.*.*(..))";
//
//	@Before(EXECUTION_PACKAGES)
//	public boolean auth(JoinPoint joinPoint) {
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//
//		if(request.getSession() != null) {
//			if(request.getSession().getAttribute("un") == null) {
//				System.out.println("logBefore() is running!");
//				response.setHeader("Cache-Control","no-cache,no-store,must-revalidate"); 
//				response.setHeader("Pragma","no-cache"); 
//				response.setDateHeader ("Expires", 0);
//				try {
//					response.sendRedirect("login");
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			} else {
//				
//			}
//		}
//		return true;
//	}
//	
//	/*@Before("execution(* com.dpu.controller.web.*.*(..))")
//	public void logBefore(JoinPoint joinPoint) {
//
//		System.out.println("logBefore() is running!");
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//		System.out.println("hijacked : " + joinPoint.getSignature().getName());
//		System.out.println("******");
//	}*/
//
//}