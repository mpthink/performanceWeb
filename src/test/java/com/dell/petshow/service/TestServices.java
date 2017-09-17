package com.dell.petshow.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.dell.petshow.system.entity.SysRolePermission;
import com.dell.petshow.system.entity.vo.MenuVO;
import com.dell.petshow.system.service.ISysPermissionService;
import com.dell.petshow.system.service.ISysRolePermissionService;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/applicationContext-dao.xml", "classpath:spring/applicationContext-ehcache.xml",
	"classpath:spring/applicationContext-service.xml"})
public class TestServices {

	@Autowired
	private ISysPermissionService sysPermissionService;

	@Autowired
	private ISysRolePermissionService sysRolePermissionService;

	@Test
	public void testSysPermission() {
		//List<MenuVO> mvList = sysPermissionService.selectMenuVOByUserId(854684173532778496L);
		//mvList.stream().forEach(mv -> System.err.println(mv.getPerm() + "\n" + mv.getChildrenPerm()));

		Long roleId = 855033115289108480L;
		List<SysRolePermission> rolePermissions =
			sysRolePermissionService.selectList(new EntityWrapper<SysRolePermission>().where("role_id={0}", roleId));

		List<Long> permissionIds = Lists.transform(rolePermissions, new Function<SysRolePermission, Long>() {

			@Override
			public Long apply(SysRolePermission rolePermission) {
				return rolePermission.getPermId();
			}
		});

		System.err.println(permissionIds);

		List<MenuVO> menuVOs = sysPermissionService.selectTreeMenuAllowAccessByMenuIdsAndPid(permissionIds, 0L);

		for (MenuVO mVo : menuVOs) {
			System.err.println(mVo.getPerm());
			System.err.println(mVo.getChildrenPerm());
		}

	}

}
