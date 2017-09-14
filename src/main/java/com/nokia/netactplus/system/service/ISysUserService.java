package com.nokia.netactplus.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.nokia.netactplus.system.entity.SysUser;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author mpthink
 * @since 2017-04-17
 */
public interface ISysUserService extends IService<SysUser> {

	/**
	 * 分页查询用户
	 */
	Page<SysUser> selectUserPage(Page<SysUser> page, String search);

	/**
	 * 保存用户
	 */
	void insertUser(SysUser user, Long[] roleId);

	/**
	 * 更新用户
	 */
	void updateUser(SysUser sysUser, Long[] roleId);

	/**
	 * 登录
	 */
	SysUser login(String userName);

}
