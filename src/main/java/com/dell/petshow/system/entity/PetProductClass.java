package com.dell.petshow.system.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import java.util.Date;
import java.io.Serializable;


/**
 * <p>
 * 产品分类表
 * </p>
 *
 * @author mpthink
 * @since 2017-09-17
 */
public class PetProductClass extends Model<PetProductClass> {

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
     * 产品分类名称
     */
	private String className;
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

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
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
		return "PetProductClass [id=" + id + ", pid=" + pid + ", className=" + className + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified + "]";
	}
}
