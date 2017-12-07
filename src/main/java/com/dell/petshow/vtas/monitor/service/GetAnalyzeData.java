package com.dell.petshow.vtas.monitor.service;

import java.util.List;
import java.util.Map;

public interface GetAnalyzeData {
	public List<Map<String, Object>> getData(String arrayName, String dataType);
}
