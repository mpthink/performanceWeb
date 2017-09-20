package com.dell.petshow.common;

import java.math.BigDecimal;

public class Test {

	public static void main(String[] args) {

		double temp1 = Math.random() * 100;
		BigDecimal bd = new BigDecimal(temp1);
		double trans = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		System.out.println(trans);
	}

}
