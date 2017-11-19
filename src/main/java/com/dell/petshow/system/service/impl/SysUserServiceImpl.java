package com.dell.petshow.system.service.impl;

import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.kisso.common.encrypt.MD5;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dell.petshow.system.entity.SysUser;
import com.dell.petshow.system.entity.SysUserRole;
import com.dell.petshow.system.mapper.SysUserMapper;
import com.dell.petshow.system.mapper.SysUserRoleMapper;
import com.dell.petshow.system.service.ISysUserService;


/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author mpthink
 * @since 2017-04-17
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

	@Autowired
	private SysUserMapper sysUserMapper;

	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;

	@Override
	public Page<SysUser> selectUserPage(Page<SysUser> page, String search) {
		page.setRecords(baseMapper.selectUserList(search));
		return page;
	}

	@Override
	public void insertUser(SysUser user, Long[] roleIds) {

		user.setGmtCreate(new Date());
		//保存用户
		user.insert();
		//绑定角色
		if (ArrayUtils.isNotEmpty(roleIds)) {
			for (Long roleId : roleIds) {
				SysUserRole sysUserRole = new SysUserRole();
				sysUserRole.setUserId(user.getId());
				sysUserRole.setRoleId(roleId);
				sysUserRole.insert();
			}
		}

	}

	@Override
	public void updateUser(SysUser sysUser, Long[] roleIds) {
		//更新用户
		sysUserMapper.updateById(sysUser);
		//删除已有权限
		sysUserRoleMapper.delete(new EntityWrapper<SysUserRole>().eq("user_id", sysUser.getId()));
		//重新绑定角色
		if (ArrayUtils.isNotEmpty(roleIds)) {
			for (Long roleId : roleIds) {
				SysUserRole sysUserRole = new SysUserRole();
				sysUserRole.setUserId(sysUser.getId());
				sysUserRole.setRoleId(roleId);
				sysUserRole.insert();
			}
		}

	}

	@Override
	public SysUser login(String userName) {
		Wrapper<SysUser> ew = new EntityWrapper<>();
		ew.eq("user_name", userName);
		return this.selectOne(ew);
	}

	@Override
	public SysUser login(String userName, String password) {
		return this.selectOne(new EntityWrapper<SysUser>().eq("user_name", userName).eq("password", MD5.toMD5(password)));
	}


}
