package com.nokia.netactplus.common.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.Token;
import com.nokia.netactplus.common.annotation.Permission;
import com.nokia.netactplus.common.util.SpringUtil;
import com.nokia.netactplus.system.service.ISysPermissionService;

/**
 * 资源拦截器
 * @author mpthink
 */
public class PermissionInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Token token = SSOHelper.getToken(request);
			if (token != null) {
				List<String> permissions = SpringUtil.getBean(ISysPermissionService.class).selectPermCodesByuserId(token.getId());
				Permission permissionSecurity = handlerMethod.getMethodAnnotation(Permission.class);
				if (permissionSecurity != null) {
					if (permissions.contains(permissionSecurity.value())) {
						return true;
					}
					request.setAttribute("url", request.getRequestURL());
					request.getRequestDispatcher("/error/illegalAccess").forward(request, response);
					return false;
				}
			}
		}
		return true;
	}
}
