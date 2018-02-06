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
 * @since 2018-01-26
 */
public class HostInformation extends Model<HostInformation> {

	private static final long serialVersionUID = 1L;

	@TableId("ID")
	private Integer id;
	@TableField("IPV4")
	private String ipv4;
	@TableField("ARRAY_NAME")
	private String arrayName;
	@TableField("OS")
	private String os;
	@TableField("CPU")
	private Integer cpu;
	@TableField("MEM")
	private Integer mem;
	@TableField("IOX")
	private String iox;
	@TableField("IOX_stat")
	private Integer IOXStat;
	@TableField("FIO")
	private String fio;
	@TableField("FIO_stat")
	private Integer FIOStat;
	@TableField("VJTREE")
	private String vjtree;
	@TableField("VJTREE_stat")
	private Integer VJTREEStat;
	@TableField("DAQ")
	private String daq;
	@TableField("DAQ_stat")
	private Integer DAQStat;
	@TableField("PERL")
	private String perl;
	@TableField("PERL_stat")
	private Integer PERLStat;
	@TableField("XMLRPC")
	private String xmlrpc;
	@TableField("XMLRPC_stat")
	private Integer XMLRPCStat;
	@TableField("POLL_DATETIME")
	private String pollDatetime;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIpv4() {
		return ipv4;
	}

	public void setIpv4(String ipv4) {
		this.ipv4 = ipv4;
	}

	public String getArrayName() {
		return arrayName;
	}

	public void setArrayName(String arrayName) {
		this.arrayName = arrayName;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public Integer getCpu() {
		return cpu;
	}

	public void setCpu(Integer cpu) {
		this.cpu = cpu;
	}

	public Integer getMem() {
		return mem;
	}

	public void setMem(Integer mem) {
		this.mem = mem;
	}

	public String getIox() {
		return iox;
	}

	public void setIox(String iox) {
		this.iox = iox;
	}

	public Integer getIOXStat() {
		return IOXStat;
	}

	public void setIOXStat(Integer IOXStat) {
		this.IOXStat = IOXStat;
	}

	public String getFio() {
		return fio;
	}

	public void setFio(String fio) {
		this.fio = fio;
	}

	public Integer getFIOStat() {
		return FIOStat;
	}

	public void setFIOStat(Integer FIOStat) {
		this.FIOStat = FIOStat;
	}

	public String getVjtree() {
		return vjtree;
	}

	public void setVjtree(String vjtree) {
		this.vjtree = vjtree;
	}

	public Integer getVJTREEStat() {
		return VJTREEStat;
	}

	public void setVJTREEStat(Integer VJTREEStat) {
		this.VJTREEStat = VJTREEStat;
	}

	public String getDaq() {
		return daq;
	}

	public void setDaq(String daq) {
		this.daq = daq;
	}

	public Integer getDAQStat() {
		return DAQStat;
	}

	public void setDAQStat(Integer DAQStat) {
		this.DAQStat = DAQStat;
	}

	public String getPerl() {
		return perl;
	}

	public void setPerl(String perl) {
		this.perl = perl;
	}

	public Integer getPERLStat() {
		return PERLStat;
	}

	public void setPERLStat(Integer PERLStat) {
		this.PERLStat = PERLStat;
	}

	public String getXmlrpc() {
		return xmlrpc;
	}

	public void setXmlrpc(String xmlrpc) {
		this.xmlrpc = xmlrpc;
	}

	public Integer getXMLRPCStat() {
		return XMLRPCStat;
	}

	public void setXMLRPCStat(Integer XMLRPCStat) {
		this.XMLRPCStat = XMLRPCStat;
	}

	public String getPollDatetime() {
		return pollDatetime;
	}

	public void setPollDatetime(String pollDatetime) {
		this.pollDatetime = pollDatetime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}


	@Override
	public String toString() {
		return "HostInformation [id=" + id + ", ipv4=" + ipv4 + ", arrayName=" + arrayName + ", os=" + os + ", cpu=" + cpu + ", mem=" + mem + ", iox="
			+ iox + ", IOXStat=" + IOXStat + ", fio=" + fio + ", FIOStat=" + FIOStat + ", vjtree=" + vjtree + ", VJTREEStat=" + VJTREEStat + ", daq="
			+ daq + ", DAQStat=" + DAQStat + ", perl=" + perl + ", PERLStat=" + PERLStat + ", xmlrpc=" + xmlrpc + ", XMLRPCStat=" + XMLRPCStat
			+ ", pollDatetime=" + pollDatetime + "]";
	}
}
