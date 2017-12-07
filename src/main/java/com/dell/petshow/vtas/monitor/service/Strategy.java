package com.dell.petshow.vtas.monitor.service;

import java.util.List;

public interface Strategy {

	boolean monitor(String arrayName, String monitorTarget, List<Integer> nums);
}
