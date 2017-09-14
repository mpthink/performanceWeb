package com.nokia.netactplus.system.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.kisso.SSOHelper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.nokia.netactplus.common.annotation.Log;
import com.nokia.netactplus.common.annotation.Permission;
import com.nokia.netactplus.common.controller.SuperController;
import com.nokia.netactplus.common.dto.NPResult;
import com.nokia.netactplus.system.entity.SysRole;
import com.nokia.netactplus.system.entity.SysUser;
import com.nokia.netactplus.system.entity.SysUserRole;
import com.nokia.netactplus.system.service.ISysDeptService;
import com.nokia.netactplus.system.service.ISysRoleService;
import com.nokia.netactplus.system.service.ISysUserRoleService;
import com.nokia.netactplus.system.service.ISysUserService;


/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author mpthink
 * @since 2017-04-17
 */
@Controller
@RequestMapping("/system/user")
public class SysUserController extends SuperController {
	@Autowired
	private ISysUserService sysUserService;
	@Autowired
	private ISysRoleService sysRoleService;
	@Autowired
	private ISysUserRoleService sysUserRoleService;
	@Autowired
	private ISysDeptService sysDeptService;

	/**
	 * 分页查询用户
	 */
	@Permission("010101")
	@RequestMapping("/list/{pageNumber}")
	public String list(@PathVariable Integer pageNumber, String search, Model model) {
		if (StringUtils.isNotBlank(search)) {
			model.addAttribute("search", search);
		}
		Page<SysUser> page = getPage(pageNumber);
		Page<SysUser> pageData = sysUserService.selectUserPage(page, search);
		model.addAttribute("pageData", pageData);
		return "system/user/list";
	}

	/**
	 * 新增用户
	 */
	@Permission("010102")
	@RequestMapping("/add")
	public String add(Model model) {
		model.addAttribute("roleList", sysRoleService.selectList(null));
		model.addAttribute("deptList", sysDeptService.selectList(null));
		return "system/user/add";
	}

	/**
	 * 执行新增
	 */
	@Log("创建用户")
	@Permission("010102")
	@RequestMapping("/doAdd")
	public String doAdd(SysUser user, Long[] roleId) {

		sysUserService.insertUser(user, roleId);
		return redirectTo("/system/user/list/1.html");
	}

	/**
	 * 删除用户
	 */
	@Log("删除用户")
	@Permission("010103")
	@RequestMapping("/delete")
	@ResponseBody
	public NPResult delete(String id) {
		sysUserService.deleteById(id);
		return new NPResult().success();
	}

	/**
	 * 编辑用户
	 */
	@RequestMapping("/edit/{id}")
	@Permission("010104")
	public String edit(@PathVariable String id, Model model) {
		SysUser sysUser = sysUserService.selectById(id);

		List<SysRole> sysRoles = sysRoleService.selectList(null);
		EntityWrapper<SysUserRole> ew = new EntityWrapper<SysUserRole>();
		ew.addFilter("user_id = {0} ", id);
		List<SysUserRole> mySysUserRoles = sysUserRoleService.selectList(ew);
		List<String> myRolds = Lists.transform(mySysUserRoles, new Function<SysUserRole, String>() {
			@Override
			public String apply(SysUserRole input) {
				return input.getRoleId().toString();
			}
		});

		model.addAttribute("sysUser", sysUser);
		model.addAttribute("sysRoles", sysRoles);
		model.addAttribute("myRolds", myRolds);
		return "system/user/edit";
	}

	/**
	 * 执行编辑
	 */
	@Log("编辑用户")
	@Permission("010104")
	@RequestMapping("/doEdit")
	public String doEdit(SysUser sysUser, Long[] roleId, Model model) {
		sysUserService.updateUser(sysUser, roleId);
		return redirectTo("/system/user/list/1.html");
	}

	/**
	 * 验证用户名是否已存在
	 */
	@RequestMapping("/checkName")
	@ResponseBody
	public String checkName(String userName) {

		List<SysUser> list = sysUserService.selectList(new EntityWrapper<SysUser>().addFilter("user_name = {0}", userName));
		if (list.size() > 0) {
			return "{\"error\":\" " + userName + " 用户名已存在,请换一个尝试.\"}";
		}
		return "{\"ok\":\"用户名很棒.\"}";
	}

	/**
	 * User setting
	 */
	@Permission("010104")
	@RequestMapping("/page")
	public String userSetting(Model model) {
		SysUser sysUser = sysUserService.selectById(SSOHelper.getToken(request).getId());
		model.addAttribute("sysUser", sysUser);
		return "system/user/page";
	}

	@Permission("010104")
	@RequestMapping("/doSetting")
	public String userSetting(SysUser sysUser, Model model) {
		sysUserService.updateById(sysUser);
		return redirectTo("/system/user/page");
	}

	@Permission("010104")
	@RequestMapping("/uploadFile")
	@ResponseBody
	public NPResult uploadFile(MultipartFile uploadAvatar) {

		//存储图片的物理路径
		String filePath = request.getServletContext().getRealPath("/WEB-INF/static/upload");
		String oldFileName = null;
		if (uploadAvatar != null) {
			//获取上传文件的原名
			oldFileName = uploadAvatar.getOriginalFilename();
		} else {
			return new NPResult().failure("获取上传文件名失败");
		}
		//上传图片
		if (oldFileName != null && oldFileName.length() > 0) {
			//新的图片名称
			String newFileName = UUID.randomUUID() + oldFileName.substring(oldFileName.lastIndexOf("."));
			//新图片
			File newFile = new File(filePath + "/" + newFileName);
			try {
				//将内存中的数据写入磁盘
				uploadAvatar.transferTo(newFile);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
				return new NPResult().failure("写入内存失败");
			}
			//将新图片名称返回到前端
			return new NPResult().success("/upload/" + newFileName);
		}
		return new NPResult().failure("上传文件失败");
	}

}
