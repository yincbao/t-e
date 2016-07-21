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
public class DaoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8849011626903118289L;
	private String errorCode = null;

	public DaoException(String errorMsg) {
		super(errorMsg);
		this.errorCode = "";
	}

	public DaoException(String errorCode, String errorMsg) {
		super(errorMsg);
		this.errorCode = errorCode;
	}

	public DaoException(ExceptionCodeEnum exp) {
		super(exp.getExpMsg());
		this.errorCode = exp.getExpCode();
	}
	
	public DaoException(ExceptionCodeEnum exp,Throwable throwable) {
		super(exp.getExpMsg(),throwable);
		this.errorCode = exp.getExpCode();
	}
	public DaoException(String exp,Throwable throwable) {
		super(exp,throwable);
		this.errorCode = exp;
	}



	public String getErrorCode() {
		return errorCode;
	}

	@Override
	public String getLocalizedMessage() {
		return getMessage();
	}
}
