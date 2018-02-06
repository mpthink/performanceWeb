package com.dell.petshow.vtas.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dell.petshow.common.controller.SuperController;
import com.dell.petshow.vtas.service.IArrayMonitorService;
import com.dell.petshow.vtas.service.IProgramMapService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mpthink
 * @since 2017-11-02
 */
@Controller
@RequestMapping("/vtas/arraymonitor")
public class ArrayMonitorController extends SuperController {

	@Autowired
	private IArrayMonitorService arrayMonitorService;
	@Autowired
	private IProgramMapService programMapService;

	@RequestMapping("/performance")
	public String showPerformance(Model model) {
		return "vtas/arraymonitor/performance";
	}

	@RequestMapping("/memory")
	public String showMemory(Model model) {
		return "vtas/arraymonitor/memory";
	}

	@RequestMapping("/bandwidth")
	public String showBandwidth(Model model) {
		return "vtas/arraymonitor/bandwidth";
	}

	@RequestMapping("/cpu")
	public String showCPU(Model model) {
		return "vtas/arraymonitor/cpu";
	}

	@RequestMapping("/disk")
	public String showIO(Model model) {
		return "vtas/arraymonitor/disk";
	}

	@RequestMapping("/iops")
	public String showIOPS(Model model) {
		return "vtas/arraymonitor/iops";
	}

	@RequestMapping("/getPrograms")
	@ResponseBody
	public List<Map<String, Object>> getPrograms() {
		return programMapService.selectAllForSelect2(false);
	}

	@RequestMapping("/getDistinctArrayList/{tableName}")
	@ResponseBody
	public List<Map<String, String>> getDistinctArrayList(@PathVariable("tableName") String tableName) {
		return arrayMonitorService.selectDistinctArrayList(tableName);
	}

	@RequestMapping("/getDistinctVersionByArray/{tableName}/{arrayName}")
	@ResponseBody
	public List<Map<String, String>> getDistinctVersionByArray(@PathVariable("tableName") String tableName,
		@PathVariable("arrayName") String arrayName) {

		return arrayMonitorService.selectDistinctVersionByArray(tableName, arrayName);
	}

	@RequestMapping(value = {"/getMemoryList/{tableName}/{arrayName}"})
	@ResponseBody
	public String getMemoryList(@PathVariable("tableName") String tableName, @PathVariable("arrayName") String arrayName,
		@RequestParam(value = "beginTime", required = false) String beginTime,
		@RequestParam(value = "endTime", required = false) String endTime) {
		return toJson(arrayMonitorService.selectMemoryListBasedOnTableNameWithArrayAndVersionAndTimeslot(tableName, arrayName, beginTime, endTime));
	}

	@RequestMapping(value = {"/getBandwidthList/{tableName}/{arrayName}"})
	@ResponseBody
	public String getBandwidthList(@PathVariable("tableName") String tableName, @PathVariable("arrayName") String arrayName,
		@RequestParam(value = "beginTime", required = false) String beginTime,
		@RequestParam(value = "endTime", required = false) String endTime) {
		return toJson(
			arrayMonitorService.selectBandwidthListBasedOnTableNameWithArrayAndVersionAndTimeslot(tableName, arrayName, beginTime, endTime));
	}

	@RequestMapping(value = {"/getCPUList/{tableName}/{arrayName}"})
	@ResponseBody
	public String getCPUList(@PathVariable("tableName") String tableName, @PathVariable("arrayName") String arrayName,
		@RequestParam(value = "beginTime", required = false) String beginTime,
		@RequestParam(value = "endTime", required = false) String endTime) {
		return toJson(arrayMonitorService.selectCPUListBasedOnTableNameWithArrayAndVersionAndTimeslot(tableName, arrayName, beginTime, endTime));
	}

	@RequestMapping(value = {"/getDiskList/{tableName}/{arrayName}"})
	@ResponseBody
	public String getDiskList(@PathVariable("tableName") String tableName, @PathVariable("arrayName") String arrayName,
		@RequestParam(value = "beginTime", required = false) String beginTime,
		@RequestParam(value = "endTime", required = false) String endTime) {
		return toJson(arrayMonitorService.selectDiskListBasedOnTableNameWithArrayAndVersionAndTimeslot(tableName, arrayName, beginTime, endTime));
	}

	@RequestMapping(value = {"/getIOPSList/{tableName}/{arrayName}"})
	@ResponseBody
	public String getIOPSList(@PathVariable("tableName") String tableName, @PathVariable("arrayName") String arrayName,
		@RequestParam(value = "beginTime", required = false) String beginTime,
		@RequestParam(value = "endTime", required = false) String endTime) {
		return toJson(arrayMonitorService.selectIOPSListBasedOnTableNameWithArrayAndVersionAndTimeslot(tableName, arrayName, beginTime, endTime));
	}

	/**
	 * for dashboard charts
	 */
	@RequestMapping(value = {"/getArrayUptime"})
	@ResponseBody
	public String getArrayUptimeForDashBoard() {
		return toJson(arrayMonitorService.getArrayUptimeForDashBoard());
	}

	@RequestMapping(value = {"/getArrayCPUMemoryIO"})
	@ResponseBody
	public String getArrayCPUMemoryIOForDashBoard() {
		return toJson(arrayMonitorService.getArrayCPUMemoryIOForDashBoard());
	}

}
