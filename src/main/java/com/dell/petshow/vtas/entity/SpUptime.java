package com.dell.petshow.vtas.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import java.util.Date;
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
public class SpUptime extends Model<SpUptime> {

    private static final long serialVersionUID = 1L;

	@TableId("ID")
	private Integer id;
	@TableField("ARRAY_NAME")
	private String arrayName;
	@TableField("VERSION")
	private String version;
	@TableField("POLL_DATETIME")
	private Date pollDatetime;
	@TableField("UPTIME")
	private Integer uptime;
	@TableField("SP")
	private String sp;


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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getPollDatetime() {
		return pollDatetime;
	}

	public void setPollDatetime(Date pollDatetime) {
		this.pollDatetime = pollDatetime;
	}

	public Integer getUptime() {
		return uptime;
	}

	public void setUptime(Integer uptime) {
		this.uptime = uptime;
	}

	public String getSp() {
		return sp;
	}

	public void setSp(String sp) {
		this.sp = sp;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}


	@Override
	public String toString(){
		return "SpUptime [id=" + id + ", arrayName=" + arrayName + ", version=" + version + ", pollDatetime=" + pollDatetime + ", uptime=" + uptime + ", sp=" + sp + "]";
	}
}
