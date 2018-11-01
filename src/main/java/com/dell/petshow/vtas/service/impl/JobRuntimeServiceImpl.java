package com.dell.petshow.vtas.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dell.petshow.common.util.DateTimeUtil;
import com.dell.petshow.vtas.entity.ArrayInfo;
import com.dell.petshow.vtas.entity.JobRuntime;
import com.dell.petshow.vtas.entity.ProgramMap;
import com.dell.petshow.vtas.mapper.ArrayInfoMapper;
import com.dell.petshow.vtas.mapper.ArrayMonitorMapper;
import com.dell.petshow.vtas.mapper.JobRuntimeMapper;
import com.dell.petshow.vtas.service.IJobRuntimeService;
import com.dell.petshow.vtas.service.IProgramMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mpthink
 * @since 2017-10-17
 */
@Service
public class JobRuntimeServiceImpl extends ServiceImpl<JobRuntimeMapper, JobRuntime> implements IJobRuntimeService {

	@Autowired
	private JobRuntimeMapper jobRuntimeMapper;
	@Autowired
	private ArrayInfoMapper arrayInfoMapper;
	@Autowired
	private ArrayMonitorMapper arrayMonitorMapper;
	@Autowired
	private IProgramMapService programMapService;

	@Override
    @Cacheable(value = "commonCache")
	public Map<String, Object> exeutionStatusForWeb(String beginTime, String endTime) {
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> arrayStatus = new ArrayList<>();
		List<Map<String, Object>> arrayUsageRatio = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("0.0");
		Long dayGap = DateTimeUtil.countDaysBetweenTwoTimesWithDefaultFormatter(beginTime,endTime);
		Long totalHours = dayGap * 24;

		//get all arrays
		Wrapper<ArrayInfo> aWrapper = new EntityWrapper<>();
		aWrapper.ne("USAGE_TYPE", "automation");
		aWrapper.ne("USAGE_TYPE","cndu");
		List<ArrayInfo> arrayInfos = arrayInfoMapper.selectList(aWrapper);
		//try to get running time based on array
		for(ArrayInfo arrayInfo:arrayInfos){
			Map<String, Object> arrayTempMap = new HashMap<>();
			Map<String, Object> usageTempMap = new HashMap<>();

			String arrayName = arrayInfo.getArrayName();
			List<JobRuntime> jobRuntimes = listJobsWithArrayNameGreaterEndTimeOrderbyEndTime(arrayName,beginTime);

			if(jobRuntimes.size() == 0){
                arrayTempMap.put("programName","NA");
                arrayTempMap.put("arrayName",arrayName);
                arrayTempMap.put("build","NA");
                arrayTempMap.put("owner",arrayInfo.getOwnerNicename());
                arrayTempMap.put("jobs","No jobs");
                arrayTempMap.put("jobStatus",2);
                arrayTempMap.put("runningHours",0);
                arrayTempMap.put("idleHours",totalHours);
                arrayTempMap.put("cpuRatio","NA");

                usageTempMap.put("arrayName",arrayName);
                usageTempMap.put("runningHours",0);
                usageTempMap.put("idleHours",totalHours);
                usageTempMap.put("totalHours",totalHours);
                arrayStatus.add(arrayTempMap);
                arrayUsageRatio.add(usageTempMap);
			    continue;
            }
			JobRuntime latestJob = jobRuntimes.get(0);
			String latestVersion = latestJob.getVersion();
			if(latestVersion.length() == 0){
			    continue;
            }
			String majorVersion = latestVersion.substring(0,5);
			ProgramMap programMap = programMapService.selectOneBasedonVersion(majorVersion);
			String programName = programMap.getProgram();
			Double runningHours = countRunningHours(jobRuntimes, beginTime, endTime, dayGap);
			Double idleHours = totalHours - runningHours;
			String runningJobs = getRunningJobsOrLatestJob(jobRuntimes);
			Integer jobStatus = getArrayJobStatus(jobRuntimes);
            Integer cpuRatio = getCPURatioOfArray(arrayName, majorVersion);
			arrayTempMap.put("programName",programName);
            arrayTempMap.put("arrayName",arrayName);
            arrayTempMap.put("build",latestVersion);
            arrayTempMap.put("owner",arrayInfo.getOwnerNicename());
            arrayTempMap.put("jobs",runningJobs);
            arrayTempMap.put("jobStatus",jobStatus);
            arrayTempMap.put("runningHours",df.format(runningHours));
            arrayTempMap.put("idleHours",df.format(idleHours));
            arrayTempMap.put("cpuRatio",cpuRatio);

            usageTempMap.put("arrayName",arrayName);
            usageTempMap.put("runningHours",df.format(runningHours));
            usageTempMap.put("idleHours",df.format(idleHours));
            usageTempMap.put("totalHours",totalHours);

            arrayStatus.add(arrayTempMap);
            arrayUsageRatio.add(usageTempMap);
        }
        result.put("arrayListData",arrayStatus);
        result.put("usageListData",arrayUsageRatio);
		return result;
	}




    @Override
	public List<String> selectDistinctArrayList() {
		return jobRuntimeMapper.selectDistinctArrayList();
	}

	@Override
	@Cacheable(value = "commonCache")
	public List<String> selectDistinctArrayListByProgram(String bigVersion) {
		return jobRuntimeMapper.selectDistinctArrayListByProgram(bigVersion);
	}

	@Override
	@Cacheable(value = "commonCache")
	public List<String> selectVersionByProgramAndArray(String bigVersion, String arrayName) {
		return jobRuntimeMapper.selectVersionByProgramAndArray(bigVersion, arrayName);
	}

	@Override
	public List<String> selectRunHourBySmallVersionAndArray(String smallVersion, String arrayName) {
		return jobRuntimeMapper.selectRunHourBySmallVersionAndArray(smallVersion, arrayName);
	}

	@Override
	public Integer getMaxRowsOfRunHourByProgramAndArray(String bigVersion, String arrayName) {
		return jobRuntimeMapper.getMaxRowsOfRunHourByProgramAndArray(bigVersion, arrayName);
	}

	@Cacheable(value = "commonCache", key = "methodName")
	@Override
	public List<Map<String, Object>> selectAllWithProgramName() {
		return jobRuntimeMapper.selectAllWithProgramName();
	}

	@Override
	public List<Map<String, Object>> selectLatestJobsWithProgramName() {
		return jobRuntimeMapper.selectLatestJobsWithProgramName();
	}

	@Override
	public Map<String, Object> selectMaxHourByProgramAndArray(String bigVersion, String arrayName) {
		return jobRuntimeMapper.selectMaxHourByProgramAndArray(bigVersion, arrayName);
	}

	private List<JobRuntime> listJobsWithArrayNameGreaterEndTimeOrderbyEndTime(String arrayName, String endTime){
		Wrapper<JobRuntime> wrapper = new EntityWrapper<>();
		wrapper.eq("ARRAY_NAME",arrayName);
		wrapper.ge("END_TIME",endTime);
		wrapper.orderBy("END_TIME",false);
		return jobRuntimeMapper.selectList(wrapper);
	}

	private String getTableName(String smallVersion) {
		String majorVersion = smallVersion.substring(0, 5);
		String tableName = programMapService.getVERSIONMAPPROGRAM().get(majorVersion);
		return tableName;
	}

    /**
     * this method is used to count running hours, if job startTime before beginTime, will use begintime as base line
     * @param jobRuntimes
     * @param beginTime
     * @param endTime
     * @param dayGap
     * @return
     */
	private Double countRunningHours(List<JobRuntime> jobRuntimes, String beginTime, String endTime, Long dayGap) {
	    int timeLineLength = dayGap.intValue() * 24 * 60;
	    int[] timeLine = new int[timeLineLength];
        LocalDateTime baseDateTime = DateTimeUtil.pareWithDefaultFormatter(beginTime);
        LocalDateTime lastDateTime = DateTimeUtil.pareWithDefaultFormatter(endTime);

        for(JobRuntime jobRuntime:jobRuntimes){
            LocalDateTime tempStart;
            LocalDateTime tempEnd;

            Date jobBeginTime = jobRuntime.getStartTime();
            Date jobEndTime = jobRuntime.getEndTime();
            LocalDateTime localBeginTime = DateTimeUtil.dateToLocalDateTime(jobBeginTime);
            LocalDateTime localEndTime = DateTimeUtil.dateToLocalDateTime(jobEndTime);
            if(localBeginTime.isBefore(baseDateTime)){
                tempStart = baseDateTime;
            }else{
                tempStart = localBeginTime;
            }
            if(localEndTime.isAfter(lastDateTime)){
                tempEnd = lastDateTime;
            }else{
                tempEnd = localEndTime;
            }
            int loopStart = Long.valueOf(baseDateTime.until(tempStart,ChronoUnit.MINUTES)).intValue();
            int loopEnd = Long.valueOf(baseDateTime.until(tempEnd,ChronoUnit.MINUTES)).intValue();
            if(loopEnd >= timeLineLength){
                loopEnd = timeLineLength;
            }
            for(int i = loopStart;i<loopEnd;i++){
                timeLine[i] = 1;
            }
        }
        double result = 0;
        for(int i=0;i<timeLineLength;i++){
            result += timeLine[i];
        }
		return Double.valueOf(result/60);
	}

    /**
     * used to get the running hours or one latest job
     * it also contains job url info and job name, if job more than one, will add ";" to seprate them
     * @param jobRuntimes
     * @return
     */
    private String getRunningJobsOrLatestJob(List<JobRuntime> jobRuntimes) {

        StringBuffer result = new StringBuffer();
        for(JobRuntime jobRuntime:jobRuntimes){
            if(jobRuntime.getStatus().equals("running")){
                String jobName = jobRuntime.getJobName();
                String[] names = jobName.split("/");
                String shortName = names[names.length-1];
                result.append(shortName).append(",").append(jobRuntime.getJobUrl()).append(";");
            }
        }
        if(result.length() == 0){
            JobRuntime latestJob = jobRuntimes.get(0);
            String jobName = latestJob.getJobName();
            String[] names = jobName.split("/");
            String shortName = names[names.length-1];
            result.append(shortName).append(",").append(latestJob.getJobUrl());
        }
        return result.toString();
    }

    /**
     * get job status/endTime and convert to integer value
     * @param jobRuntimes
     * @return
     * 0 running
     * 1 less and equal 24 hours
     * 2 bigger than hours
     */
    private Integer getArrayJobStatus(List<JobRuntime> jobRuntimes) {
        JobRuntime latestJob = jobRuntimes.get(0);
        LocalDateTime now = LocalDateTime.now();
        if(latestJob.getStatus().equals("running")){
            return 0;
        }else{
            Date endTime = latestJob.getEndTime();
            LocalDateTime localEndTime = DateTimeUtil.dateToLocalDateTime(endTime);
            Long timeGap = localEndTime.until(now, ChronoUnit.HOURS);
            if(timeGap>24){
                return 2;
            }else {
                return 1;
            }
        }
    }

    private Integer getCPURatioOfArray(String arrayName, String majorVersion) {
        Integer result = 0;
        String tableName = programMapService.getVERSIONMAPPROGRAM().get(majorVersion);
        Map<String, Object> spaData = arrayMonitorMapper.selectLatestOneForDashBoardByProgramAndArrayAndSPType(tableName, arrayName, "SPA");
        Map<String, Object> spbData = arrayMonitorMapper.selectLatestOneForDashBoardByProgramAndArrayAndSPType(tableName, arrayName, "SPB");
        if(spbData == null){
            result = (Integer) spaData.get("CPU_FILT");
        }else {
            Integer spaCPU = (Integer) spaData.get("CPU_FILT");
            Integer spbCPU = (Integer) spbData.get("CPU_FILT");
            result = (spaCPU + spbCPU)/2;
        }
        return result;
    }

}
