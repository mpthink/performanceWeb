package com.dell.petshow.vtas.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import java.util.Date;
import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author mpthink
 * @since 2018-01-02
 */
public class ArrayConfiguration extends Model<ArrayConfiguration> {

    private static final long serialVersionUID = 1L;

	@TableId("ID")
	private Integer id;
	@TableField("POLL_DATETIME")
	private Date pollDatetime;
	private String project;
	@TableField("ARRAY_NAME")
	private String arrayName;
	private String model;
	private Integer lunCount;
	private Integer lunThinCount;
	private Integer fsThinCount;
	private Integer lunSnapCount;
	private Integer fsSnapCount;
	private Integer lunIlcCount;
	private Integer fsIlcCount;
	private Integer fsCount;
	private Integer fcHostCount;
	private Integer iscsiHostCount;
	private Integer cifsNfsHostCount;
	private Integer totalDriveCount;
	private Float sizeTb;
	private String driveTypes;
	private String ilcIlpdEnable;
	private String fastcacheEnable;
	private String mappedRaidEnable;
	private String encryption;
	private String fsnEnable;
	private Float pool1SizeTb;
	private String pool1DriveTypes;
	private Float pool2SizeTb;
	private String pool2DriveTypes;
	private Float pool3SizeTb;
	private String pool3DriveTypes;
	private Float pool4SizeTb;
	private String pool4DriveTypes;
	private Float pool5SizeTb;
	private String pool5DriveTypes;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getPollDatetime() {
		return pollDatetime;
	}

	public void setPollDatetime(Date pollDatetime) {
		this.pollDatetime = pollDatetime;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getArrayName() {
		return arrayName;
	}

	public void setArrayName(String arrayName) {
		this.arrayName = arrayName;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getLunCount() {
		return lunCount;
	}

	public void setLunCount(Integer lunCount) {
		this.lunCount = lunCount;
	}

	public Integer getLunThinCount() {
		return lunThinCount;
	}

	public void setLunThinCount(Integer lunThinCount) {
		this.lunThinCount = lunThinCount;
	}

	public Integer getFsThinCount() {
		return fsThinCount;
	}

	public void setFsThinCount(Integer fsThinCount) {
		this.fsThinCount = fsThinCount;
	}

	public Integer getLunSnapCount() {
		return lunSnapCount;
	}

	public void setLunSnapCount(Integer lunSnapCount) {
		this.lunSnapCount = lunSnapCount;
	}

	public Integer getFsSnapCount() {
		return fsSnapCount;
	}

	public void setFsSnapCount(Integer fsSnapCount) {
		this.fsSnapCount = fsSnapCount;
	}

	public Integer getLunIlcCount() {
		return lunIlcCount;
	}

	public void setLunIlcCount(Integer lunIlcCount) {
		this.lunIlcCount = lunIlcCount;
	}

	public Integer getFsIlcCount() {
		return fsIlcCount;
	}

	public void setFsIlcCount(Integer fsIlcCount) {
		this.fsIlcCount = fsIlcCount;
	}

	public Integer getFsCount() {
		return fsCount;
	}

	public void setFsCount(Integer fsCount) {
		this.fsCount = fsCount;
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

	public Integer getTotalDriveCount() {
		return totalDriveCount;
	}

	public void setTotalDriveCount(Integer totalDriveCount) {
		this.totalDriveCount = totalDriveCount;
	}

	public Float getSizeTb() {
		return sizeTb;
	}

	public void setSizeTb(Float sizeTb) {
		this.sizeTb = sizeTb;
	}

	public String getDriveTypes() {
		return driveTypes;
	}

	public void setDriveTypes(String driveTypes) {
		this.driveTypes = driveTypes;
	}

	public String getIlcIlpdEnable() {
		return ilcIlpdEnable;
	}

	public void setIlcIlpdEnable(String ilcIlpdEnable) {
		this.ilcIlpdEnable = ilcIlpdEnable;
	}

	public String getFastcacheEnable() {
		return fastcacheEnable;
	}

	public void setFastcacheEnable(String fastcacheEnable) {
		this.fastcacheEnable = fastcacheEnable;
	}

	public String getMappedRaidEnable() {
		return mappedRaidEnable;
	}

	public void setMappedRaidEnable(String mappedRaidEnable) {
		this.mappedRaidEnable = mappedRaidEnable;
	}

	public String getEncryption() {
		return encryption;
	}

	public void setEncryption(String encryption) {
		this.encryption = encryption;
	}

	public String getFsnEnable() {
		return fsnEnable;
	}

	public void setFsnEnable(String fsnEnable) {
		this.fsnEnable = fsnEnable;
	}

	public Float getPool1SizeTb() {
		return pool1SizeTb;
	}

	public void setPool1SizeTb(Float pool1SizeTb) {
		this.pool1SizeTb = pool1SizeTb;
	}

	public String getPool1DriveTypes() {
		return pool1DriveTypes;
	}

	public void setPool1DriveTypes(String pool1DriveTypes) {
		this.pool1DriveTypes = pool1DriveTypes;
	}

	public Float getPool2SizeTb() {
		return pool2SizeTb;
	}

	public void setPool2SizeTb(Float pool2SizeTb) {
		this.pool2SizeTb = pool2SizeTb;
	}

	public String getPool2DriveTypes() {
		return pool2DriveTypes;
	}

	public void setPool2DriveTypes(String pool2DriveTypes) {
		this.pool2DriveTypes = pool2DriveTypes;
	}

	public Float getPool3SizeTb() {
		return pool3SizeTb;
	}

	public void setPool3SizeTb(Float pool3SizeTb) {
		this.pool3SizeTb = pool3SizeTb;
	}

	public String getPool3DriveTypes() {
		return pool3DriveTypes;
	}

	public void setPool3DriveTypes(String pool3DriveTypes) {
		this.pool3DriveTypes = pool3DriveTypes;
	}

	public Float getPool4SizeTb() {
		return pool4SizeTb;
	}

	public void setPool4SizeTb(Float pool4SizeTb) {
		this.pool4SizeTb = pool4SizeTb;
	}

	public String getPool4DriveTypes() {
		return pool4DriveTypes;
	}

	public void setPool4DriveTypes(String pool4DriveTypes) {
		this.pool4DriveTypes = pool4DriveTypes;
	}

	public Float getPool5SizeTb() {
		return pool5SizeTb;
	}

	public void setPool5SizeTb(Float pool5SizeTb) {
		this.pool5SizeTb = pool5SizeTb;
	}

	public String getPool5DriveTypes() {
		return pool5DriveTypes;
	}

	public void setPool5DriveTypes(String pool5DriveTypes) {
		this.pool5DriveTypes = pool5DriveTypes;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}


	@Override
	public String toString(){
		return "ArrayConfiguration [id=" + id + ", pollDatetime=" + pollDatetime + ", project=" + project + ", arrayName=" + arrayName + ", model=" + model + ", lunCount=" + lunCount + ", lunThinCount=" + lunThinCount + ", fsThinCount=" + fsThinCount + ", lunSnapCount=" + lunSnapCount + ", fsSnapCount=" + fsSnapCount + ", lunIlcCount=" + lunIlcCount + ", fsIlcCount=" + fsIlcCount + ", fsCount=" + fsCount + ", fcHostCount=" + fcHostCount + ", iscsiHostCount=" + iscsiHostCount + ", cifsNfsHostCount=" + cifsNfsHostCount + ", totalDriveCount=" + totalDriveCount + ", sizeTb=" + sizeTb + ", driveTypes=" + driveTypes + ", ilcIlpdEnable=" + ilcIlpdEnable + ", fastcacheEnable=" + fastcacheEnable + ", mappedRaidEnable=" + mappedRaidEnable + ", encryption=" + encryption + ", fsnEnable=" + fsnEnable + ", pool1SizeTb=" + pool1SizeTb + ", pool1DriveTypes=" + pool1DriveTypes + ", pool2SizeTb=" + pool2SizeTb + ", pool2DriveTypes=" + pool2DriveTypes + ", pool3SizeTb=" + pool3SizeTb + ", pool3DriveTypes=" + pool3DriveTypes + ", pool4SizeTb=" + pool4SizeTb + ", pool4DriveTypes=" + pool4DriveTypes + ", pool5SizeTb=" + pool5SizeTb + ", pool5DriveTypes=" + pool5DriveTypes + "]";
	}
}
