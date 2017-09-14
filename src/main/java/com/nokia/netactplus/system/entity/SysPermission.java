package com.nokia.netactplus.system.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import java.util.Date;
import java.io.Serializable;


/**
 * <p>
 * 权限表
 * </p>
 *
 * @author mpthink
 * @since 2017-04-17
 */
public class SysPermission extends Model<SysPermission> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	private Long id;
    /**
     * 上级ID
     */
	private Long pid;
    /**
     * 权限名称
     */
	private String permName;
    /**
     * 类型 0:菜单 1:功能
     */
	private Integer permType;
    /**
     * 状态 0:正常 1:禁用
     */
	private Integer permStatus;
    /**
     * 排序
     */
	private Integer sort;
    /**
     * 地址
     */
	private String url;
    /**
     * 权限编码
     */
	private String permCode;
    /**
     * 图标
     */
	private String icon;
    /**
     * 权限描述
     */
	private String permDesc;
    /**
     * 创建时间
     */
	private Date gmtCreate;
    /**
     * 修改时间
     */
	private Date gmtModified;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getPermName() {
		return permName;
	}

	public void setPermName(String permName) {
		this.permName = permName;
	}

	public Integer getPermType() {
		return permType;
	}

	public void setPermType(Integer permType) {
		this.permType = permType;
	}

	public Integer getPermStatus() {
		return permStatus;
	}

	public void setPermStatus(Integer permStatus) {
		this.permStatus = permStatus;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPermCode() {
		return permCode;
	}

	public void setPermCode(String permCode) {
		this.permCode = permCode;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPermDesc() {
		return permDesc;
	}

	public void setPermDesc(String permDesc) {
		this.permDesc = permDesc;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}


	@Override
	public String toString(){
		return "SysPermission [id=" + id + ", pid=" + pid + ", permName=" + permName + ", permType=" + permType + ", permStatus=" + permStatus + ", sort=" + sort + ", url=" + url + ", permCode=" + permCode + ", icon=" + icon + ", permDesc=" + permDesc + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified + "]";
	}
}
