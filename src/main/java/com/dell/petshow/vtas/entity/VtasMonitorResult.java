package com.dell.petshow.vtas.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import java.util.Date;
import java.io.Serializable;


/**
 * <p>
 * 监控结果记录表
 * </p>
 *
 * @author mpthink
 * @since 2017-12-07
 */
public class VtasMonitorResult extends Model<VtasMonitorResult> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	private Long id;
    /**
     * array Name
     */
	private String arrayName;
    /**
     * 监控对象
     */
	private String monitorObject;
    /**
     * 监控采用策略
     */
	private String monitorStrategy;
    /**
     * 其他信息
     */
	private String otherInfo;
    /**
     * 状态 0:未确认 1:已确认不显示
     */
	private Integer resultStatus;
    /**
     * 创建时间
     */
	private Date gmtCreate;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getArrayName() {
		return arrayName;
	}

	public void setArrayName(String arrayName) {
		this.arrayName = arrayName;
	}

	public String getMonitorObject() {
		return monitorObject;
	}

	public void setMonitorObject(String monitorObject) {
		this.monitorObject = monitorObject;
	}

	public String getMonitorStrategy() {
		return monitorStrategy;
	}

	public void setMonitorStrategy(String monitorStrategy) {
		this.monitorStrategy = monitorStrategy;
	}

	public String getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}

	public Integer getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(Integer resultStatus) {
		this.resultStatus = resultStatus;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}


	@Override
	public String toString(){
		return "VtasMonitorResult [id=" + id + ", arrayName=" + arrayName + ", monitorObject=" + monitorObject + ", monitorStrategy=" + monitorStrategy + ", otherInfo=" + otherInfo + ", resultStatus=" + resultStatus + ", gmtCreate=" + gmtCreate + "]";
	}
}
