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
 * @since 2018-01-26
 */
public class OfficialSwVersions extends Model<OfficialSwVersions> {

    private static final long serialVersionUID = 1L;

	@TableId("ID")
	private Integer id;
	@TableField("NAME")
	private String name;
	@TableField("VERSION")
	private String version;
	@TableField("CONDIT")
	private String condit;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCondit() {
		return condit;
	}

	public void setCondit(String condit) {
		this.condit = condit;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}


	@Override
	public String toString(){
		return "OfficialSwVersions [id=" + id + ", name=" + name + ", version=" + version + ", condit=" + condit + "]";
	}
}
