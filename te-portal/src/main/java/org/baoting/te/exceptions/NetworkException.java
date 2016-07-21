package org.baoting.te.exceptions;

import com.hoperun.ism.constaints.ExceptionCodeEnum;

/**
 * 
 * 
* @ClassName: ConfigException.java 
* @Description: 
* @author YinChang-bao
* @date Nov 16, 2015
*
 */
public class NetworkException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4711341642173967482L;
	private String errorCode = null;

	public NetworkException(String errorMsg) {
		super(errorMsg);
		this.errorCode = "";
	}

	public NetworkException(String errorCode, String errorMsg) {
		super(errorMsg);
		this.errorCode = errorCode;
	}

	public NetworkException(ExceptionCodeEnum exp) {
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
