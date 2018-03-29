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
 * @since 2018-03-26
 */
public class JobRuntime extends Model<JobRuntime> {

    private static final long serialVersionUID = 1L;

	@TableId("ID")
	private String id;
	@TableField("START_TIME")
	private Date startTime;
	@TableField("END_TIME")
	private Date endTime;
	@TableField("RUN_HOURS")
	private Float runHours;
	@TableField("VERSION")
	private String version;
	@TableField("ARRAY_NAME")
	private String arrayName;
	@TableField("JOB_NAME")
	private String jobName;
	@TableField("JOB_URL")
	private String jobUrl;
	@TableField("BBT_STATUS")
	private String bbtStatus;
	@TableField("STATUS")
	private String status;
	@TableField("MIN_NO_UPDATE")
	private Integer minNoUpdate;
	@TableField("TESTSETS_URL")
	private String testsetsUrl;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Float getRunHours() {
		return runHours;
	}

	public void setRunHours(Float runHours) {
		this.runHours = runHours;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getArrayName() {
		return arrayName;
	}

	public void setArrayName(String arrayName) {
		this.arrayName = arrayName;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobUrl() {
		return jobUrl;
	}

	public void setJobUrl(String jobUrl) {
		this.jobUrl = jobUrl;
	}

	public String getBbtStatus() {
		return bbtStatus;
	}

	public void setBbtStatus(String bbtStatus) {
		this.bbtStatus = bbtStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getMinNoUpdate() {
		return minNoUpdate;
	}

	public void setMinNoUpdate(Integer minNoUpdate) {
		this.minNoUpdate = minNoUpdate;
	}

	public String getTestsetsUrl() {
		return testsetsUrl;
	}

	public void setTestsetsUrl(String testsetsUrl) {
		this.testsetsUrl = testsetsUrl;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}


	@Override
	public String toString(){
		return "JobRuntime [id=" + id + ", startTime=" + startTime + ", endTime=" + endTime + ", runHours=" + runHours + ", version=" + version + ", arrayName=" + arrayName + ", jobName=" + jobName + ", jobUrl=" + jobUrl + ", bbtStatus=" + bbtStatus + ", status=" + status + ", minNoUpdate=" + minNoUpdate + ", testsetsUrl=" + testsetsUrl + "]";
	}
}
