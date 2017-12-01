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
 * @since 2017-11-30
 */
public class ProgramMap extends Model<ProgramMap> {

    private static final long serialVersionUID = 1L;

	@TableId("MAJOR_VERSION")
	private String majorVersion;
	@TableField("PROGRAM")
	private String program;
	@TableField("STATUS")
	private Integer status;


	public String getMajorVersion() {
		return majorVersion;
	}

	public void setMajorVersion(String majorVersion) {
		this.majorVersion = majorVersion;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	protected Serializable pkVal() {
		return this.majorVersion;
	}


	@Override
	public String toString(){
		return "ProgramMap [majorVersion=" + majorVersion + ", program=" + program + ", status=" + status + "]";
	}
}
