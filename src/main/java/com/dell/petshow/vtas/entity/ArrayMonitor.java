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
 * @since 2017-11-02
 */
public class ArrayMonitor extends Model<ArrayMonitor> {

    private static final long serialVersionUID = 1L;

	@TableId("ID")
	private Integer id;
	@TableField("ARRAY_NAME")
	private String arrayName;
	@TableField("SP")
	private String sp;
	@TableField("CPU_BUSY")
	private Integer cpuBusy;
	@TableField("CPU_FILT")
	private Integer cpuFilt;
	@TableField("MEM_USED")
	private Integer memUsed;
	@TableField("MEM_FREE")
	private Integer memFree;
	@TableField("MEM_SWPD")
	private Integer memSwpd;
	@TableField("IO_READ")
	private Integer ioRead;
	@TableField("IO_WRITE")
	private Integer ioWrite;
	@TableField("IOPS_WRITE")
	private Integer iopsWrite;
	@TableField("IOPS_READ")
	private Integer iopsRead;
	private Integer ktraceUtil;
	@TableField("ECOM")
	private Integer ecom;
	private Integer csxIcSafe;
	@TableField("POLL_DATETIME")
	private Date pollDatetime;
	@TableField("VERSION")
	private String version;


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

	public String getSp() {
		return sp;
	}

	public void setSp(String sp) {
		this.sp = sp;
	}

	public Integer getCpuBusy() {
		return cpuBusy;
	}

	public void setCpuBusy(Integer cpuBusy) {
		this.cpuBusy = cpuBusy;
	}

	public Integer getCpuFilt() {
		return cpuFilt;
	}

	public void setCpuFilt(Integer cpuFilt) {
		this.cpuFilt = cpuFilt;
	}

	public Integer getMemUsed() {
		return memUsed;
	}

	public void setMemUsed(Integer memUsed) {
		this.memUsed = memUsed;
	}

	public Integer getMemFree() {
		return memFree;
	}

	public void setMemFree(Integer memFree) {
		this.memFree = memFree;
	}

	public Integer getMemSwpd() {
		return memSwpd;
	}

	public void setMemSwpd(Integer memSwpd) {
		this.memSwpd = memSwpd;
	}

	public Integer getIoRead() {
		return ioRead;
	}

	public void setIoRead(Integer ioRead) {
		this.ioRead = ioRead;
	}

	public Integer getIoWrite() {
		return ioWrite;
	}

	public void setIoWrite(Integer ioWrite) {
		this.ioWrite = ioWrite;
	}

	public Integer getIopsWrite() {
		return iopsWrite;
	}

	public void setIopsWrite(Integer iopsWrite) {
		this.iopsWrite = iopsWrite;
	}

	public Integer getIopsRead() {
		return iopsRead;
	}

	public void setIopsRead(Integer iopsRead) {
		this.iopsRead = iopsRead;
	}

	public Integer getKtraceUtil() {
		return ktraceUtil;
	}

	public void setKtraceUtil(Integer ktraceUtil) {
		this.ktraceUtil = ktraceUtil;
	}

	public Integer getEcom() {
		return ecom;
	}

	public void setEcom(Integer ecom) {
		this.ecom = ecom;
	}

	public Integer getCsxIcSafe() {
		return csxIcSafe;
	}

	public void setCsxIcSafe(Integer csxIcSafe) {
		this.csxIcSafe = csxIcSafe;
	}

	public Date getPollDatetime() {
		return pollDatetime;
	}

	public void setPollDatetime(Date pollDatetime) {
		this.pollDatetime = pollDatetime;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}


	@Override
	public String toString(){
		return "Harrier [id=" + id + ", arrayName=" + arrayName + ", sp=" + sp + ", cpuBusy=" + cpuBusy + ", cpuFilt=" + cpuFilt + ", memUsed=" + memUsed + ", memFree=" + memFree + ", memSwpd=" + memSwpd + ", ioRead=" + ioRead + ", ioWrite=" + ioWrite + ", iopsWrite=" + iopsWrite + ", iopsRead=" + iopsRead + ", ktraceUtil=" + ktraceUtil + ", ecom=" + ecom + ", csxIcSafe=" + csxIcSafe + ", pollDatetime=" + pollDatetime + ", version=" + version + "]";
	}
}
