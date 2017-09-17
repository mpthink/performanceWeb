package com.dell.petshow.system.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import java.util.Date;
import java.io.Serializable;


/**
 * <p>
 * 系统配置表
 * </p>
 *
 * @author mpthink
 * @since 2017-09-17
 */
public class SysSetting extends Model<SysSetting> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	private Long id;
    /**
     * KEY
     */
	private String sysKey;
    /**
     * 名称
     */
	private String sysName;
    /**
     * 值
     */
	private String sysValue;
    /**
     * 排序
     */
	private Integer sort;
    /**
     * 说明
     */
	private String sysDesc;
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

	public String getSysKey() {
		return sysKey;
	}

	public void setSysKey(String sysKey) {
		this.sysKey = sysKey;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getSysValue() {
		return sysValue;
	}

	public void setSysValue(String sysValue) {
		this.sysValue = sysValue;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getSysDesc() {
		return sysDesc;
	}

	public void setSysDesc(String sysDesc) {
		this.sysDesc = sysDesc;
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
		return "SysSetting [id=" + id + ", sysKey=" + sysKey + ", sysName=" + sysName + ", sysValue=" + sysValue + ", sort=" + sort + ", sysDesc=" + sysDesc + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified + "]";
	}
}
