package com.hoperun.oauth.exception;

/**
 * @ClassName UserNameDuplicateExceptioin.java
 * @Description 
 * @author yin_changbao
 * @Date Jun 27, 2016 5:59:07 PM
 *
 */
public class UserNameDuplicateExceptioin extends RuntimeException {

	
	private String errorCode = null;
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public UserNameDuplicateExceptioin(String errorMsg) {
		super(errorMsg);
		this.errorCode = "";
	}

	public UserNameDuplicateExceptioin(String errorCode, String errorMsg) {
		super(errorMsg);
		this.errorCode = errorCode;
	}
}
