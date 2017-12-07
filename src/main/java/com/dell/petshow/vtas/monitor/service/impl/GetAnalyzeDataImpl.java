package com.dell.petshow.vtas.monitor.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dell.petshow.vtas.entity.ProgramMap;
import com.dell.petshow.vtas.entity.SpUptime;
import com.dell.petshow.vtas.mapper.ArrayMonitorMapper;
import com.dell.petshow.vtas.mapper.ProgramMapMapper;
import com.dell.petshow.vtas.mapper.SpUptimeMapper;
import com.dell.petshow.vtas.monitor.service.GetAnalyzeData;

@Service
public class GetAnalyzeDataImpl implements GetAnalyzeData {

	@Autowired
	private ArrayMonitorMapper arrayMonitorMapper;
	@Autowired
	private SpUptimeMapper spUptimeMapper;
	@Autowired
	private ProgramMapMapper programMapMapper;

	@Override
	public List<Map<String, Object>> getData(String arrayName, String dataType) {
		SpUptime spUptime = spUptimeMapper.selectLatestOneByArray(arrayName);
		if (spUptime != null) {
			String smallVersion = spUptime.getVersion();
			ProgramMap programMap = programMapMapper.selectOneBasedonVersion(smallVersion.substring(0, 5));
			String programName = programMap.getProgram();
			String beginTime = getDayTime(-1);
			String endTime = getDayTime(0);
			if (dataType.equals("memory")) {
				return arrayMonitorMapper.selectMemoryListBasedOnTableNameWithArrayAndVersionAndTimeslot(programName, arrayName, beginTime, endTime);
			} else if (dataType.equals("cpu")) {
				return arrayMonitorMapper.selectCPUListBasedOnTableNameWithArrayAndVersionAndTimeslot(programName, arrayName, beginTime, endTime);
			} else if (dataType.equals("disk")) {
				return arrayMonitorMapper.selectDiskListBasedOnTableNameWithArrayAndVersionAndTimeslot(programName, arrayName, beginTime, endTime);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public String getDayTime(int dayBefore) {
		Date dNow = new Date();
		Date dBefore;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (dayBefore == 0) {
			return sdf.format(dNow);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dNow);
		calendar.add(Calendar.DAY_OF_MONTH, dayBefore);
		dBefore = calendar.getTime();
		return sdf.format(dBefore);
	}
}
