package com.dell.petshow.system.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import java.util.Date;
import java.io.Serializable;


/**
 * <p>
 * 产品表
 * </p>
 *
 * @author mpthink
 * @since 2017-09-17
 */
public class PetProduct extends Model<PetProduct> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	private Long id;
    /**
     * 产品名称
     */
	private String productName;
    /**
     * 产品版本
     */
	private String productRelease;
    /**
     * 产品描述
     */
	private String productDesc;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductRelease() {
		return productRelease;
	}

	public void setProductRelease(String productRelease) {
		this.productRelease = productRelease;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
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
		return "PetProduct [id=" + id + ", productName=" + productName + ", productRelease=" + productRelease + ", productDesc=" + productDesc + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified + "]";
	}
}
