package com.dell.petshow.vtas.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;


/**
 * <p>
 *
 * </p>
 *
 * @author mpthink
 * @since 2017-11-14
 */
public class VersionDateMap extends Model<VersionDateMap> {

	private static final long serialVersionUID = 1L;

	@TableId("VERSION")
	private String version;
	@TableField("DATE")
	private String date;
	@TableField("PROGRAM")
	private String program;


	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	@Override
	protected Serializable pkVal() {
		return this.version;
	}


	@Override
	public String toString() {
		return "VersionDateMap [version=" + version + ", date=" + date + ", program=" + program + "]";
	}
}
