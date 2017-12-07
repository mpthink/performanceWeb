package com.dell.petshow.vtas.monitor.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.dell.petshow.vtas.monitor.service.GetAnalyzeData;
import com.dell.petshow.vtas.monitor.service.Strategy;

public class MemoryAnalyze implements Runnable {

	@Autowired
	private GetAnalyzeData getAnalyzeData;
	private String arrayName;
	private Map<String, Object> resultMap = new HashMap<>();
	private Strategy strategy;

	public MemoryAnalyze() {}

	public MemoryAnalyze(String arrayName, Strategy strategy) {
		this.arrayName = arrayName;
		this.strategy = strategy;
	}

	@Override
	public void run() {
		List<Map<String, Object>> memoryMapDatas = getAnalyzeData.getData(arrayName, "memory");
		List<Integer> SPA_memoryUsedData = new ArrayList<>();
		List<Integer> SPA_peserviceRSSData = new ArrayList<>();
		List<Integer> SPA_csxRSSData = new ArrayList<>();
		List<Integer> SPA_ecomRSSData = new ArrayList<>();
		List<Integer> SPA_mozzoRSSData = new ArrayList<>();
		List<Integer> SPA_tomcatRSSData = new ArrayList<>();
		List<Integer> SPA_TLDlistenerRSSData = new ArrayList<>();
		List<Integer> SPB_memoryUsedData = new ArrayList<>();
		List<Integer> SPB_peserviceRSSData = new ArrayList<>();
		List<Integer> SPB_csxRSSData = new ArrayList<>();
		List<Integer> SPB_ecomRSSData = new ArrayList<>();
		List<Integer> SPB_mozzoRSSData = new ArrayList<>();
		List<Integer> SPB_tomcatRSSData = new ArrayList<>();
		List<Integer> SPB_TLDlistenerRSSData = new ArrayList<>();

		for (Map<String, Object> memoryMapData : memoryMapDatas) {
			if (memoryMapData.get("sp").equals("SPA")) {
				SPA_memoryUsedData.add((Integer) memoryMapData.get("MEM_USED"));
				SPA_peserviceRSSData.add((Integer) memoryMapData.get("peservice_exe_rss"));
				SPA_csxRSSData.add((Integer) memoryMapData.get("csx_ic_safe_rss"));
				SPA_ecomRSSData.add((Integer) memoryMapData.get("ECOM_rss"));
				SPA_mozzoRSSData.add((Integer) memoryMapData.get("mozzo_sh_rss"));
				SPA_tomcatRSSData.add((Integer) memoryMapData.get("tomcat_sh_rss"));
				SPA_TLDlistenerRSSData.add((Integer) memoryMapData.get("TLDlistener_exe_rss"));
			} else {
				SPB_memoryUsedData.add((Integer) memoryMapData.get("MEM_USED"));
				SPB_peserviceRSSData.add((Integer) memoryMapData.get("peservice_exe_rss"));
				SPB_csxRSSData.add((Integer) memoryMapData.get("csx_ic_safe_rss"));
				SPB_ecomRSSData.add((Integer) memoryMapData.get("ECOM_rss"));
				SPB_mozzoRSSData.add((Integer) memoryMapData.get("mozzo_sh_rss"));
				SPB_tomcatRSSData.add((Integer) memoryMapData.get("tomcat_sh_rss"));
				SPB_TLDlistenerRSSData.add((Integer) memoryMapData.get("TLDlistener_exe_rss"));
			}
		}

		//currently, this part is execute sequentially, optimize it using multiple thread later
		strategy.monitor(arrayName, "SPA_MEM_USED", SPA_memoryUsedData);
		strategy.monitor(arrayName, "SPA_peservice_exe_rss", SPA_peserviceRSSData);
		strategy.monitor(arrayName, "SPA_csx_ic_safe_rss", SPA_csxRSSData);
		strategy.monitor(arrayName, "SPA_ECOM_rss", SPA_ecomRSSData);
		strategy.monitor(arrayName, "SPA_mozzo_sh_rss", SPA_mozzoRSSData);
		strategy.monitor(arrayName, "SPA_tomcat_sh_rss", SPA_tomcatRSSData);
		strategy.monitor(arrayName, "SPA_TLDlistener_exe_rss", SPA_TLDlistenerRSSData);

		strategy.monitor(arrayName, "SPB_MEM_USED", SPB_memoryUsedData);
		strategy.monitor(arrayName, "SPB_peservice_exe_rss", SPB_peserviceRSSData);
		strategy.monitor(arrayName, "SPB_csx_ic_safe_rss", SPB_csxRSSData);
		strategy.monitor(arrayName, "SPB_ECOM_rss", SPB_ecomRSSData);
		strategy.monitor(arrayName, "SPB_mozzo_sh_rss", SPB_mozzoRSSData);
		strategy.monitor(arrayName, "SPB_tomcat_sh_rss", SPB_tomcatRSSData);
		strategy.monitor(arrayName, "SPB_TLDlistener_exe_rss", SPB_TLDlistenerRSSData);
	}

	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

	public String getArrayName() {
		return arrayName;
	}

	public void setArrayName(String arrayName) {
		this.arrayName = arrayName;
	}

	public Strategy getStrategy() {
		return strategy;
	}

	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}

}
