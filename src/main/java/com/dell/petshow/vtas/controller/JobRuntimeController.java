package com.dell.petshow.vtas.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dell.petshow.common.controller.SuperController;
import com.dell.petshow.vtas.service.IJobRuntimeService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mpthink
 * @since 2017-10-17
 */
@Controller
@RequestMapping("/vtas/jobRuntime")
public class JobRuntimeController extends SuperController {

	@Autowired
	private IJobRuntimeService jobRuntimeService;

	@RequestMapping("/vsBuild")
	public String index(Model model) {
		return "vtas/jobruntime/vsbuild";
	}

	@RequestMapping("/listJobs")
	public String listJobs(Model model) {
		return "vtas/jobruntime/listJobs";
	}

	@RequestMapping("/getall")
	@ResponseBody
	public String getAll() {
		return toJson(jobRuntimeService.selectAllWithProgramName());
	}

	@RequestMapping("/getArrayByProgram/{bigVersion}/")
	@ResponseBody
	public List<Map<String, String>> getArrayByProgram(@PathVariable("bigVersion") String bigVersion) {
		if (bigVersion == "" || bigVersion == null) {
			return null;
		}
		List<String> arrayList = jobRuntimeService.selectDistinctArrayListByProgram(bigVersion);
		List<Map<String, String>> listMap = new ArrayList<>();
		for (String arrayName : arrayList) {
			Map<String, String> map = new HashMap<>();
			map.put("id", arrayName);
			map.put("text", arrayName);
			listMap.add(map);
		}
		return listMap;
	}

	@RequestMapping("/getVersionByProgramAndArray/{bigVersion}/{arrayName}")
	@ResponseBody
	public List<Map<String, String>> getVersionByArray(@PathVariable("bigVersion") String bigVersion, @PathVariable("arrayName") String arrayName) {
		if (bigVersion == "" || bigVersion == null || arrayName == null) {
			return null;
		}
		List<String> versionList = jobRuntimeService.selectVersionByProgramAndArray(bigVersion, arrayName);
		List<Map<String, String>> listMap = new ArrayList<>();
		for (String version : versionList) {
			Map<String, String> map = new HashMap<>();
			map.put("id", version);
			map.put("text", version);
			listMap.add(map);
		}
		return listMap;
	}

	@RequestMapping("/getRunHourByProgramAndArray/{bigVersion}/{arrayName}/{smallVersion}/")
	@ResponseBody
	public Map<String, Object> getRunHourByArray(@PathVariable("bigVersion") String bigVersion, @PathVariable("arrayName") String arrayName,
		@PathVariable("smallVersion") String smallVersion) {
		if (bigVersion == "" || bigVersion == null || arrayName == null) {
			return null;
		}
		Map<String, Object> dataMap = new HashMap<>();
		if (smallVersion == null || smallVersion.equals("all")) {
			//select all versions for this array
			List<String> versionList = jobRuntimeService.selectVersionByProgramAndArray(bigVersion, arrayName);
			if (versionList.size() == 0) {
				return null;
			}
			Integer maxLegend = jobRuntimeService.getMaxRowsOfRunHourByProgramAndArray(bigVersion, arrayName);
			String[] series_data = new String[maxLegend];
			StringBuffer temp_xAxis_data = new StringBuffer("");
			for (int i = 0; i < versionList.size(); i++) {
				if (i == versionList.size() - 1) {
					temp_xAxis_data.append(versionList.get(i));
				} else {
					temp_xAxis_data.append(versionList.get(i)).append(",");
				}
				handleRunHourList(versionList.get(i), arrayName, series_data);
			}
			dataMap.put("xAxis_data", temp_xAxis_data);
			dataMap.put("series_data", series_data);
		} else {
			//select the dedicated version for this array
			dataMap = handleRunHourListForSingleVersion(smallVersion, arrayName);
		}
		return dataMap;
	}

	private void handleRunHourList(String smallVersion, String arrayName, String[] series_data) {
		List<String> hourList = jobRuntimeService.selectRunHourBySmallVersionAndArray(smallVersion, arrayName);
		for (int i = 0; i < hourList.size(); i++) {
			if (series_data[i] == null) {
				series_data[i] = hourList.get(i);
			} else {
				series_data[i] = series_data[i] + "," + hourList.get(i);
			}
		}
	}

	private Map<String, Object> handleRunHourListForSingleVersion(String smallVersion, String arrayName) {
		List<String> hourList = jobRuntimeService.selectRunHourBySmallVersionAndArray(smallVersion, arrayName);
		Integer maxLegend = hourList.size();
		String[] hourArray = new String[maxLegend];
		for (int i = 0; i < hourList.size(); i++) {
			hourArray[i] = hourList.get(i);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("xAxis_data", smallVersion);
		map.put("series_data", hourArray);
		return map;
	}
}
