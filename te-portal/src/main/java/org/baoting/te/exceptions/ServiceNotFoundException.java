package org.baoting.te.exceptions;

import org.baoting.te.constaints.ExceptionCodeEnum;

/**
 * 
 * 
* @ClassName: ConfigException.java 
* @Description: 
* @author YinChang-bao
* @date Nov 16, 2015
*
 */
public class ServiceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4711341642173967482L;
	private String errorCode = null;

	public ServiceNotFoundException(String errorMsg) {
		super(errorMsg);
		this.errorCode = "";
	}

	public ServiceNotFoundException(String errorCode, String errorMsg) {
		super(errorMsg);
		this.errorCode = errorCode;
	}

	public ServiceNotFoundException(ExceptionCodeEnum exp) {
		super(exp.getExpMsg());
		this.errorCode = exp.getExpCode();
	}

	public String getErrorCode() {
		return errorCode;
	}

	@Override
	public String getLocalizedMessage() {
		return getMessage();
	}
}
