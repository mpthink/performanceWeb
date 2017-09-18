package com.dell.petshow.common.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;
import com.dell.petshow.common.annotation.Log;
import com.dell.petshow.system.entity.SysLog;
import com.google.gson.Gson;

@Aspect
@Component
public class LogAdvice {

	public static final Logger LOG = LoggerFactory.getLogger(LogAdvice.class);

	@Pointcut("@annotation(com.dell.petshow.common.annotation.Log)")
	public void logAfterReturningAspect() {}

	/**
	 * 当方法正常返回是执行
	 * @param joinPoint
	 */
	@AfterReturning("logAfterReturningAspect()")
	public void logAfterReturning(JoinPoint joinPoint) {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		Log log = method.getAnnotation(Log.class);
		SSOToken st = SSOHelper.getToken(request);
		if (log != null) {
			SysLog sysLog = new SysLog();
			sysLog.setGmtCreate(new Date());
			sysLog.setTitle(log.value());
			sysLog.setUserName((st != null) ? st.getData() : "system");
			sysLog.setUrl(request.getRequestURI().toString());
			sysLog.setParams(new Gson().toJson(request.getParameterMap()));
			sysLog.insert();
			LOG.debug("记录日志:" + sysLog.toString());
		}
	}
}
