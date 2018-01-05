package com.dell.petshow.vtas.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author mpthink
 * @since 2018-01-02
 */
public class ArrayHosts extends Model<ArrayHosts> {

    private static final long serialVersionUID = 1L;

	@TableId("ID")
	private Integer id;
	@TableField("ARRAY_NAME")
	private String arrayName;
	private Integer fcHostCount;
	private Integer iscsiHostCount;
	private Integer cifsNfsHostCount;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getArrayName() {
		return arrayName;
	}

	public void setArrayName(String arrayName) {
		this.arrayName = arrayName;
	}

	public Integer getFcHostCount() {
		return fcHostCount;
	}

	public void setFcHostCount(Integer fcHostCount) {
		this.fcHostCount = fcHostCount;
	}

	public Integer getIscsiHostCount() {
		return iscsiHostCount;
	}

	public void setIscsiHostCount(Integer iscsiHostCount) {
		this.iscsiHostCount = iscsiHostCount;
	}

	public Integer getCifsNfsHostCount() {
		return cifsNfsHostCount;
	}

	public void setCifsNfsHostCount(Integer cifsNfsHostCount) {
		this.cifsNfsHostCount = cifsNfsHostCount;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}


	@Override
	public String toString(){
		return "ArrayHosts [id=" + id + ", arrayName=" + arrayName + ", fcHostCount=" + fcHostCount + ", iscsiHostCount=" + iscsiHostCount + ", cifsNfsHostCount=" + cifsNfsHostCount + "]";
	}
}
