package com.nokia.netactplus.system.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import java.util.Date;
import java.io.Serializable;


/**
 * <p>
 * Lab 淇℃伅琛
 * </p>
 *
 * @author mpthink
 * @since 2017-04-20
 */
public class NetactLab extends Model<NetactLab> {

    private static final long serialVersionUID = 1L;

    /**
     * 涓婚敭
     */
	private Long id;
    /**
     * lab 鍚嶇О锛宔.g:clab1507
     */
	private String labName;
    /**
     * lab 鐗堟湰
     */
	private String labVersion;
    /**
     * lab link
     */
	private String labUrl;
    /**
     * lab 绫诲瀷, 0:cloudLab, 1:cloud_small, 2: cloud_node manager, 3:cloud_medium, 4:cloud_large, 5:sprintLab, 6:unknown
     */
	private Integer labType;
    /**
     * db node IP address
     */
	private String dbIpv4;
    /**
     * viis node IP address
     */
	private String viisIpv4;
    /**
     * node manager VDA-1 IP address
     */
	private String vda1Ipv4;
    /**
     * lbwas node IP address
     */
	private String lbwasIpv4;
    /**
     * lab鐘舵?,0:鏈?煡,1:鍙?敤,2:婵?椿, 婵?椿鍚庝細鏇存柊lab 鐗堟湰淇℃伅
     */
	private Integer labStatus;
    /**
     * lab 鐘舵?璇︽儏
     */
	private String statusInfo;
    /**
     * lab 鎻忚堪/remark绛変俊鎭
     */
	private String labDesc;
    /**
     * lab root 瀵嗙爜
     */
	private String rootPassword;
    /**
     * lab db 瀵嗙爜
     */
	private String dbPassword;
    /**
     * lab omc 瀵嗙爜
     */
	private String omcPassword;
    /**
     * viis root 瀵嗙爜
     */
	private String viisPassword;
    /**
     * lab鎵?睘project
     */
	private String labProject;
    /**
     * lab鎵?湪MPP
     */
	private String labMpp;
    /**
     * lab 鎵?睘team
     */
	private String labOwner;
    /**
     * lab 閰嶇疆鎯呭喌, e.g: Cloud_Zu v3, Cloud_Zs, Cloud_Xs
     */
	private String labConfig;
    /**
     * 鍒涘缓鏃堕棿
     */
	private Date gmtCreate;
    /**
     * 淇?敼鏃堕棿
     */
	private Date gmtModified;
    /**
     * 杩囨湡鏃堕棿
     */
	private Date gmtRelease;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLabName() {
		return labName;
	}

	public void setLabName(String labName) {
		this.labName = labName;
	}

	public String getLabVersion() {
		return labVersion;
	}

	public void setLabVersion(String labVersion) {
		this.labVersion = labVersion;
	}

	public String getLabUrl() {
		return labUrl;
	}

	public void setLabUrl(String labUrl) {
		this.labUrl = labUrl;
	}

	public Integer getLabType() {
		return labType;
	}

	public void setLabType(Integer labType) {
		this.labType = labType;
	}

	public String getDbIpv4() {
		return dbIpv4;
	}

	public void setDbIpv4(String dbIpv4) {
		this.dbIpv4 = dbIpv4;
	}

	public String getViisIpv4() {
		return viisIpv4;
	}

	public void setViisIpv4(String viisIpv4) {
		this.viisIpv4 = viisIpv4;
	}

	public String getVda1Ipv4() {
		return vda1Ipv4;
	}

	public void setVda1Ipv4(String vda1Ipv4) {
		this.vda1Ipv4 = vda1Ipv4;
	}

	public String getLbwasIpv4() {
		return lbwasIpv4;
	}

	public void setLbwasIpv4(String lbwasIpv4) {
		this.lbwasIpv4 = lbwasIpv4;
	}

	public Integer getLabStatus() {
		return labStatus;
	}

	public void setLabStatus(Integer labStatus) {
		this.labStatus = labStatus;
	}

	public String getStatusInfo() {
		return statusInfo;
	}

	public void setStatusInfo(String statusInfo) {
		this.statusInfo = statusInfo;
	}

	public String getLabDesc() {
		return labDesc;
	}

	public void setLabDesc(String labDesc) {
		this.labDesc = labDesc;
	}

	public String getRootPassword() {
		return rootPassword;
	}

	public void setRootPassword(String rootPassword) {
		this.rootPassword = rootPassword;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public String getOmcPassword() {
		return omcPassword;
	}

	public void setOmcPassword(String omcPassword) {
		this.omcPassword = omcPassword;
	}

	public String getViisPassword() {
		return viisPassword;
	}

	public void setViisPassword(String viisPassword) {
		this.viisPassword = viisPassword;
	}

	public String getLabProject() {
		return labProject;
	}

	public void setLabProject(String labProject) {
		this.labProject = labProject;
	}

	public String getLabMpp() {
		return labMpp;
	}

	public void setLabMpp(String labMpp) {
		this.labMpp = labMpp;
	}

	public String getLabOwner() {
		return labOwner;
	}

	public void setLabOwner(String labOwner) {
		this.labOwner = labOwner;
	}

	public String getLabConfig() {
		return labConfig;
	}

	public void setLabConfig(String labConfig) {
		this.labConfig = labConfig;
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

	public Date getGmtRelease() {
		return gmtRelease;
	}

	public void setGmtRelease(Date gmtRelease) {
		this.gmtRelease = gmtRelease;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}


	@Override
	public String toString(){
		return "NetactLab [id=" + id + ", labName=" + labName + ", labVersion=" + labVersion + ", labUrl=" + labUrl + ", labType=" + labType + ", dbIpv4=" + dbIpv4 + ", viisIpv4=" + viisIpv4 + ", vda1Ipv4=" + vda1Ipv4 + ", lbwasIpv4=" + lbwasIpv4 + ", labStatus=" + labStatus + ", statusInfo=" + statusInfo + ", labDesc=" + labDesc + ", rootPassword=" + rootPassword + ", dbPassword=" + dbPassword + ", omcPassword=" + omcPassword + ", viisPassword=" + viisPassword + ", labProject=" + labProject + ", labMpp=" + labMpp + ", labOwner=" + labOwner + ", labConfig=" + labConfig + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified + ", gmtRelease=" + gmtRelease + "]";
	}
}
