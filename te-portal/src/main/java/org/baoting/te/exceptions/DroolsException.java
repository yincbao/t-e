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
public class DroolsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8849011626903118289L;
	private String errorCode = null;

	public DroolsException(String errorMsg) {
		super(errorMsg);
		this.errorCode = "";
	}

	public DroolsException(String errorCode, String errorMsg) {
		super(errorMsg);
		this.errorCode = errorCode;
	}

	public DroolsException(ExceptionCodeEnum exp) {
		super(exp.getExpMsg());
		this.errorCode = exp.getExpCode();
	}
	
	public DroolsException(ExceptionCodeEnum exp,Throwable throwable) {
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
