package com.nokia.netactplus.system.entity.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.nokia.netactplus.system.entity.SysPermission;

/**
 * 菜单+是否有权限访问
 * @author p1ma
 *
 */
public class MenuVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private SysPermission perm;

	private boolean allowAccess = false;

	private List<MenuVO> childrenPerm = new ArrayList<>();

	public SysPermission getPerm() {
		return perm;
	}

	public void setPerm(SysPermission perm) {
		this.perm = perm;
	}

	public boolean isAllowAccess() {
		return allowAccess;
	}

	public void setAllowAccess(boolean allowAccess) {
		this.allowAccess = allowAccess;
	}

	public List<MenuVO> getChildrenPerm() {
		return childrenPerm;
	}

	public void setChildrenPerm(List<MenuVO> childrenPerm) {
		this.childrenPerm = childrenPerm;
	}


}
