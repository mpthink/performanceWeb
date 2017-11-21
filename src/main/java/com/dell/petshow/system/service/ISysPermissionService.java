package com.dell.petshow.system.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.dell.petshow.system.entity.SysPermission;
import com.dell.petshow.system.entity.vo.MenuVO;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author mpthink
 * @since 2017-04-17
 */
public interface ISysPermissionService extends IService<SysPermission> {

	List<String> selectPermCodesByuserId(Long userId);

	List<MenuVO> selectMenuVOByUserId(Long userId);

	List<MenuVO> selectTreeMenuAllowAccessByMenuIdsAndPid(List<Long> permissionIds, Long pid);

	List<MenuVO> selectMenuVOByRoleName(String roleName);

}
