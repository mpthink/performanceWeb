package com.dell.petshow.system.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import java.util.Date;
import java.io.Serializable;


/**
 * <p>
 * CPU 性能原始数据表
 * </p>
 *
 * @author mpthink
 * @since 2017-09-17
 */
public class PetCpuRaw extends Model<PetCpuRaw> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	private Long id;
    /**
     * 测试对象
     */
	private Long productId;
    /**
     * 产生时间
     */
	private Date gmtGenerate;
    /**
     * CPU利用率
     */
	private Double cpuUtilization;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Date getGmtGenerate() {
		return gmtGenerate;
	}

	public void setGmtGenerate(Date gmtGenerate) {
		this.gmtGenerate = gmtGenerate;
	}

	public Double getCpuUtilization() {
		return cpuUtilization;
	}

	public void setCpuUtilization(Double cpuUtilization) {
		this.cpuUtilization = cpuUtilization;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}


	@Override
	public String toString(){
		return "PetCpuRaw [id=" + id + ", productId=" + productId + ", gmtGenerate=" + gmtGenerate + ", cpuUtilization=" + cpuUtilization + "]";
	}
}
