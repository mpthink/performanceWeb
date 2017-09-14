package com.nokia.netactplus.common.interceptor;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.baomidou.kisso.SSOConfig;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.Token;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import com.baomidou.kisso.common.util.HttpUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.nokia.netactplus.common.util.SpringUtil;
import com.nokia.netactplus.system.entity.SysSetting;
import com.nokia.netactplus.system.entity.SysUser;
import com.nokia.netactplus.system.entity.vo.MenuVO;
import com.nokia.netactplus.system.service.ISysPermissionService;
import com.nokia.netactplus.system.service.ISysSettingService;
import com.nokia.netactplus.system.service.ISysUserService;

/**
 * 登录拦截器
 * @author p1ma
 *
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		if (handler instanceof HandlerMethod) {
			/**
			 * 加载全局非登录访问常量
			 */
			List<SysSetting> settingList =
				SpringUtil.getBean(ISysSettingService.class).selectList(new EntityWrapper<SysSetting>().orderBy("sort", true));
			for (SysSetting setting : settingList) {
				request.setAttribute(setting.getSysKey(), setting.getSysValue());
			}

			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			Login login = method.getAnnotation(Login.class);
			if (login != null) {
				if (login.action() == Action.Skip) {
					/**
					 * 忽略拦截
					 */
					return true;
				}
			}

			/**
			 * 正常执行
			 */
			Token token = SSOHelper.getToken(request);
			if (token == null) {
				if (HttpUtil.isAjax(request)) {
					HttpUtil.ajaxStatus(response, 302, "session expires.");
					return false;
				} else {
					SSOHelper.clearRedirectLogin(request, response);
					return false;
				}
			} else {
				/**
				 * 正常请求，request 设置 token 减少二次解密
				 */
				request.setAttribute(SSOConfig.SSO_TOKEN_ATTR, token);
				/**
				 * 保存登录信息
				 */
				SysUser currentUser = SpringUtil.getBean(ISysUserService.class).selectById(token.getId());
				currentUser.setPassword("");
				request.setAttribute("currentUser", currentUser);
				/**
				 * 获取当前用户的菜单
				 */
				List<MenuVO> menuList = SpringUtil.getBean(ISysPermissionService.class).selectMenuVOByUserId(token.getId());
				request.setAttribute("menuList", menuList);

				/**
				 * 获取当前用户的权限列表,用于控制页面功能按钮是否显示
				 */
				List<String> list2 = SpringUtil.getBean(ISysPermissionService.class).selectPermCodesByuserId(token.getId());
				String[] permissions = list2.toArray(new String[list2.size()]);
				request.setAttribute("permissions", permissions);
			}
		}
		/**
		 * 通过拦截
		 */
		return true;
	}

}
