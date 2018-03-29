package com.dell.petshow.vtas.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author mpthink
 * @since 2018-03-26
 */
public class ContinousBackupServer extends Model<ContinousBackupServer> {

    private static final long serialVersionUID = 1L;

	@TableField("ID")
	private Integer id;
	private String serverUrl;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	@Override
	protected Serializable pkVal() {
		return this.serverUrl;
	}


	@Override
	public String toString(){
		return "ContinousBackupServer [id=" + id + ", serverUrl=" + serverUrl + "]";
	}
}
