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
public class InterfaceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -19428345953698021L;
	private String errorCode = null;

	public InterfaceException(String errorMsg) {
		super(errorMsg);
		this.errorCode = "";
	}

	public InterfaceException(String errorCode, String errorMsg) {
		super(errorMsg);
		this.errorCode = errorCode;
	}

	public InterfaceException(ExceptionCodeEnum exp) {
		super(exp.getExpMsg());
		this.errorCode = exp.getExpCode();
	}

	public InterfaceException(ExceptionCodeEnum exp,Throwable throwable) {
		super(exp.getExpMsg(),throwable);
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
