package com.nokia.netactplus.common.exception;

public class DAOExceptionOfOracle extends RuntimeException {

	/**
	 * DAO exception for Oracle access
	 */
	private static final long serialVersionUID = 1L;

	public DAOExceptionOfOracle() {}

	public DAOExceptionOfOracle(String msg) {
		super(msg);
	}

	public DAOExceptionOfOracle(Throwable e) {
		super(e);
	}

	public DAOExceptionOfOracle(String msg, Throwable e) {
		super(msg, e);
	}
}
