package com.dell.petshow.system.service.impl;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dell.petshow.system.entity.SysRolePermission;
import com.dell.petshow.system.mapper.SysRolePermissionMapper;
import com.dell.petshow.system.service.ISysRolePermissionService;

/**
 * <p>
 * 角色权限表 服务实现类
 * </p>
 *
 * @author mpthink
 * @since 2017-04-17
 */
@Service
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements ISysRolePermissionService {

	@Override
	public void addAuth(Long roleId, Long[] permIds) {
		/**
		 * 删除原有权限
		 */
		this.delete(new EntityWrapper<SysRolePermission>().eq("role_id", roleId));
		/**
		 * 重新授权
		 */
		if (ArrayUtils.isNotEmpty(permIds)) {
			for (Long permId : permIds) {
				SysRolePermission sysRolePermission = new SysRolePermission();
				sysRolePermission.setRoleId(roleId);
				sysRolePermission.setPermId(permId);;
				sysRolePermission.insert();
			}
		}

	}

}
