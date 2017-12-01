package com.dell.petshow.vtas.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import java.util.Date;
import java.io.Serializable;


/**
 * <p>
 * 文件管理表
 * </p>
 *
 * @author mpthink
 * @since 2017-12-01
 */
public class VtasFiles extends Model<VtasFiles> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	private Long id;
    /**
     * 文件名称
     */
	private String fileName;
    /**
     * 文件描述
     */
	private String fileDesc;
    /**
     * 状态 0:正常 1:禁用
     */
	private Integer fileStatus;
    /**
     * 创建时间
     */
	private Date gmtCreate;
    /**
     * 修改时间
     */
	private Date gmtModified;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileDesc() {
		return fileDesc;
	}

	public void setFileDesc(String fileDesc) {
		this.fileDesc = fileDesc;
	}

	public Integer getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(Integer fileStatus) {
		this.fileStatus = fileStatus;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}


	@Override
	public String toString(){
		return "VtasFiles [id=" + id + ", fileName=" + fileName + ", fileDesc=" + fileDesc + ", fileStatus=" + fileStatus + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified + "]";
	}
}
