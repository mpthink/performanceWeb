package com.dell.petshow.common.interceptor;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.baomidou.kisso.SSOConfig;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.Token;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.dell.petshow.common.util.SpringUtil;
import com.dell.petshow.system.entity.SysSetting;
import com.dell.petshow.system.entity.SysUser;
import com.dell.petshow.system.entity.vo.MenuVO;
import com.dell.petshow.system.service.ISysPermissionService;
import com.dell.petshow.system.service.ISysSettingService;
import com.dell.petshow.system.service.ISysUserService;

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
			 * 资源和当前选中菜单
			 */
			String res = request.getParameter("p");
			if (StringUtils.isNotBlank(res)) {
				request.getSession().setAttribute("res", res);
			}
			String cur = request.getParameter("t");
			if (StringUtils.isNotBlank(cur)) {
				request.getSession().setAttribute("cur", cur);
			}

			/**
			 * 正常执行
			 */
			Token token = SSOHelper.getToken(request);
			if (token == null) {
				SysUser visitor = SpringUtil.getBean(ISysUserService.class).selectById(999999999999999999L);
				request.setAttribute("currentUser", visitor);
				List<MenuVO> menuList = SpringUtil.getBean(ISysPermissionService.class).selectMenuVOByRoleName("Member");
				request.setAttribute("menuList", menuList);
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
