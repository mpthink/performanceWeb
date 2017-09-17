package com.dell.petshow.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dell.petshow.system.entity.SysPermission;
import com.dell.petshow.system.entity.vo.MenuVO;
import com.dell.petshow.system.mapper.SysPermissionMapper;
import com.dell.petshow.system.service.ISysPermissionService;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

/**
 * <p>
 *
 * 权限表 服务实现类
 * </p>
 *
 * @author mpthink
 * @since 2017-04-17
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

	@Override
	public List<String> selectPermCodesByuserId(Long userId) {
		return baseMapper.selectPermCodesByuserId(userId);
	}

	@Cacheable(value = "permissionCache", key = "#userId")
	@Override
	public List<MenuVO> selectMenuVOByUserId(Long userId) {
		List<SysPermission> permList = baseMapper.selectMenuByuserIdAndPid(userId, 0L);
		if (permList == null || permList.isEmpty()) {
			return null;
		}
		List<MenuVO> mvList = new ArrayList<>();
		for (SysPermission perm : permList) {
			MenuVO mv = new MenuVO();
			mv.setPerm(perm);
			List<SysPermission> childList = baseMapper.selectMenuByuserIdAndPid(userId, perm.getId());
			List<MenuVO> childMVList = Lists.transform(childList, new Function<SysPermission, MenuVO>() {
				@Override
				public MenuVO apply(SysPermission permission) {
					MenuVO menuVO = new MenuVO();
					menuVO.setPerm(permission);
					return menuVO;
				}
			});
			mv.setChildrenPerm(childMVList);
			mvList.add(mv);
		}
		return mvList;
	}

	@Override
	public List<MenuVO> selectTreeMenuAllowAccessByMenuIdsAndPid(final List<Long> permissionIds, Long pid) {

		EntityWrapper<SysPermission> ew = new EntityWrapper<SysPermission>();
		ew.where("pid = {0} ", pid);
		List<SysPermission> permissions = baseMapper.selectList(ew);
		List<MenuVO> menuVOs = Lists.transform(permissions, new Function<SysPermission, MenuVO>() {
			@Override
			public MenuVO apply(SysPermission permission) {
				MenuVO menuVO = new MenuVO();
				menuVO.setPerm(permission);
				/**
				 * 是否有权限
				 */
				if (permissionIds.contains(permission.getId())) {
					menuVO.setAllowAccess(true);
				}
				/**
				 * 子节点
				 */
				if (permission.getPermType() == 0) {
					menuVO.setChildrenPerm(selectTreeMenuAllowAccessByMenuIdsAndPid(permissionIds, permission.getId()));
				}
				return menuVO;
			}

		});
		return menuVOs;

	}

}
