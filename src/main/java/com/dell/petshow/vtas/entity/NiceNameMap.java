package com.dell.petshow.vtas.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;


/**
 * <p>
 *
 * </p>
 *
 * @author mpthink
 * @since 2017-10-17
 */
public class NiceNameMap extends Model<NiceNameMap> {

	private static final long serialVersionUID = 1L;

	@TableField("NICE_NAME")
	private String niceName;
	@TableField("VERSION")
	private String version;
	@TableField("PROGRAM")
	private String program;


	public String getNiceName() {
		return niceName;
	}

	public void setNiceName(String niceName) {
		this.niceName = niceName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
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
		return "NiceNameMap [niceName=" + niceName + ", version=" + version + ", program=" + program + "]";
	}
}
