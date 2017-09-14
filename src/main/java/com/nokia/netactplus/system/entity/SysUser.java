package com.nokia.netactplus.system.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import java.io.Serializable;


/**
 * <p>
 * 用户表
 * </p>
 *
 * @author mpthink
 * @since 2017-04-17
 */
public class SysUser extends Model<SysUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
	private Long id;
    /**
     * 部门主键
     */
	@TableField("deptId")
	private Long deptId;
    /**
     * 用户名
     */
	private String userName;
    /**
     * 密码
     */
	private String password;
    /**
     * 用户状态,0:启用,1:禁用
     */
	private Integer userStatus;
    /**
     * 描述
     */
	private String userDesc;
    /**
     * 头像
     */
	private String avatar;
    /**
     * 邮箱
     */
	private String email;
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

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}

	public String getUserDesc() {
		return userDesc;
	}

	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
		return "SysUser [id=" + id + ", deptId=" + deptId + ", userName=" + userName + ", password=" + password + ", userStatus=" + userStatus + ", userDesc=" + userDesc + ", avatar=" + avatar + ", email=" + email + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified + "]";
	}
}
