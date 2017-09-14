package com.nokia.netactplus.common.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.ServletException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.kisso.SSOConfig;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.code.kaptcha.servlet.KaptchaExtend;
import com.nokia.netactplus.common.dto.NPResult;
import com.nokia.netactplus.system.entity.SysRole;
import com.nokia.netactplus.system.entity.SysUser;
import com.nokia.netactplus.system.entity.SysUserRole;
import com.nokia.netactplus.system.service.ISysRoleService;
import com.nokia.netactplus.system.service.ISysUserService;

@Controller
@RequestMapping("/login")
public class LoginController extends SuperController {

	@Autowired
	private ISysUserService sysUserService;

	@Autowired
	private ISysRoleService sysRoleService;

	/**
	 * 登录页面
	 * @throws UnsupportedEncodingException
	 */
	@Login(action = Action.Skip)
	@RequestMapping(value = {"/", "/index"})
	public String login(String ReturnURL, Model model) throws UnsupportedEncodingException {
		String index = "/index.html";
		model.addAttribute("returnURL", StringUtils.isNotBlank(ReturnURL) ? URLDecoder.decode(ReturnURL, "UTF-8") : index);
		return "login";
	}

	/**
	 * 执行登录
	 */
	@Login(action = Action.Skip)
	@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	public String doLogin(String userName, String password, String captcha, String returnURL, Model model) {

		SysUser sysUser = sysUserService.login(userName);
		//当用户不存在，创建用户，用户名为邮件, 用户role为普通会员
		if (sysUser == null) {
			sysUser = new SysUser();
			sysUser.setUserName(userName);
			sysUser.setGmtCreate(new Date());
			sysUser.setEmail(userName);
			sysUser.setPassword("123456");
			sysUser.setAvatar("/AdminLTE/img/Dearest.jpg");
			sysUser.insert();

			sysUser = sysUserService.selectOne(new EntityWrapper<SysUser>().eq("user_name", userName));
			SysRole generalRole = sysRoleService.selectOne(new EntityWrapper<SysRole>().eq("role_name", "普通会员"));
			if (generalRole != null) {
				SysUserRole ur = new SysUserRole();
				ur.setRoleId(generalRole.getId());
				ur.setUserId(sysUser.getId());
				ur.insert();
			}
		}
		/**
		 * 登录成功
		 */
		SSOToken st = new SSOToken();
		st.setId(sysUser.getId());
		st.setData(sysUser.getUserName());
		SSOHelper.setSSOCookie(request, response, st, true);
		return redirectTo("/index.html");
	}

	/**
	 * 退出系统
	 * @return
	 * @throws IOException
	 */
	@Login(action = Action.Skip)
	@RequestMapping(value = "/logout")
	public String logout() throws IOException {

		SSOHelper.clearLogin(request, response);
		return redirectTo(SSOConfig.getInstance().getLoginUrl());
	}

	/**
	 * 验证码
	 */
	@Login(action = Action.Skip)
	@RequestMapping("/captcha")
	@ResponseBody
	public NPResult captcha() throws ServletException, IOException {
		KaptchaExtend kaptchaExtend = new KaptchaExtend();
		kaptchaExtend.captcha(request, response);
		return new NPResult().success();
	}
}
