package com.dell.petshow.vtas.monitor.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dell.petshow.vtas.entity.VtasMonitorResult;
import com.dell.petshow.vtas.monitor.service.Strategy;

@Service("MemoryGrowthStrategy")
public class MemoryGrowthStrategy implements Strategy {

	//if there are 10 times continuing growing, it should have problem
	//10 times = 5 hours, this value can be set in DB and get it dynamically in future
	private int threshold_count = 10;

	//sometimes, the data will decrease a little, then increase again.
	//this parameter is used for this scenario, and the value is the threshold for max decease times
	private int tolerence_rollback_times = 2;

	//this value is used for max gap when decrease happens.
	//private int tolerence_value_gap = 40;

	@Override
	public boolean monitor(String arrayName, String monitorObject, List<Integer> nums) {
		int count = 0;
		int tolerence_times = 0;
		for (int i = 0; i < nums.size() - 1; i++) {
			if (nums.get(i + 1) > nums.get(i)) {
				count++;
			} else if (nums.get(i) == 0 || nums.get(i + 1) == nums.get(i)) {
				continue;
			} else if (nums.get(i + 1) < nums.get(i) && tolerence_times < tolerence_rollback_times) {
				tolerence_times++;
				continue;
			} else {
				count = 0;
			}
			if (count >= threshold_count) {
				VtasMonitorResult vtasMonitorResult = new VtasMonitorResult();
				vtasMonitorResult.setArrayName(arrayName);
				vtasMonitorResult.setMonitorObject(monitorObject);
				vtasMonitorResult.setGmtCreate(new Date());
				vtasMonitorResult.setMonitorStrategy("memory continued growth strategy");
				vtasMonitorResult.insert();
				return true;
			}
		}
		return false;
	}
}
