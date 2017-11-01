package com.dell.petshow.vtas.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author mpthink
 * @since 2017-11-01
 */
public class ArrayInfo extends Model<ArrayInfo> {

    private static final long serialVersionUID = 1L;

	@TableId("ARRAY_NAME")
	private String arrayName;
	@TableField("OWNER")
	private String owner;
	@TableField("MODEL")
	private String model;
	@TableField("MGMT_IP")
	private String mgmtIp;
	@TableField("SPA_IP")
	private String spaIp;
	@TableField("SPB_IP")
	private String spbIp;
	@TableField("USAGE_TYPE")
	private String usageType;
	@TableField("ARRAY_STATUS")
	private String arrayStatus;
	@TableField("COMMENT")
	private String comment;


	public String getArrayName() {
		return arrayName;
	}

	public void setArrayName(String arrayName) {
		this.arrayName = arrayName;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getMgmtIp() {
		return mgmtIp;
	}

	public void setMgmtIp(String mgmtIp) {
		this.mgmtIp = mgmtIp;
	}

	public String getSpaIp() {
		return spaIp;
	}

	public void setSpaIp(String spaIp) {
		this.spaIp = spaIp;
	}

	public String getSpbIp() {
		return spbIp;
	}

	public void setSpbIp(String spbIp) {
		this.spbIp = spbIp;
	}

	public String getUsageType() {
		return usageType;
	}

	public void setUsageType(String usageType) {
		this.usageType = usageType;
	}

	public String getArrayStatus() {
		return arrayStatus;
	}

	public void setArrayStatus(String arrayStatus) {
		this.arrayStatus = arrayStatus;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	protected Serializable pkVal() {
		return this.arrayName;
	}


	@Override
	public String toString(){
		return "ArrayInfo [arrayName=" + arrayName + ", owner=" + owner + ", model=" + model + ", mgmtIp=" + mgmtIp + ", spaIp=" + spaIp + ", spbIp=" + spbIp + ", usageType=" + usageType + ", arrayStatus=" + arrayStatus + ", comment=" + comment + "]";
	}
}
