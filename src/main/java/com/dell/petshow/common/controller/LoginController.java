package com.dell.petshow.common.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.ServletException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import com.baomidou.kisso.common.encrypt.MD5;
import com.dell.petshow.common.dto.NPResult;
import com.dell.petshow.system.entity.SysUser;
import com.dell.petshow.system.service.ISysRoleService;
import com.dell.petshow.system.service.ISysUserService;
import com.google.code.kaptcha.servlet.KaptchaExtend;

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
		System.err.println(MD5.toMD5(password));
		if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
			model.addAttribute("error", "username/password must not be empty");
			return "login";
		}

		SysUser sysUser = sysUserService.login(userName, password);
		if (sysUser == null) {
			model.addAttribute("error", "username/password is wrong");
			return "login";
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
		return redirectTo("/index.html");
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
