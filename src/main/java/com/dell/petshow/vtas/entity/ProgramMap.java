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
 * @since 2017-10-17
 */
public class ProgramMap extends Model<ProgramMap> {

    private static final long serialVersionUID = 1L;

	@TableId("MAJOR_VERSION")
	private String majorVersion;
	@TableField("PROGRAM")
	private String program;


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

	@Override
	protected Serializable pkVal() {
		return this.majorVersion;
	}


	@Override
	public String toString(){
		return "ProgramMap [majorVersion=" + majorVersion + ", program=" + program + "]";
	}
}
