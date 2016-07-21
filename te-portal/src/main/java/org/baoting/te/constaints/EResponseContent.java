package org.baoting.te.constaints;

public enum EResponseContent {

	SUCCEED(200, "success"),
	
	SUCCEED_NO_DATA(201, "success but no data"),

	NETWORK_ERROR(500, "internal server error"),

	UNKNOW_ERROR(5000, "unknow error"),

	NULL_PROGRAM_NOR_VIN(9001, "please provide a programId or a vin code"),

	UNDEFINED_RUN_TYPE(9002, "run type should be clearfied"),
	
	UNDEFINED_YEAR_MONTH(9006, "please specify year month"),

	EMPTY_REQUEST(9003, "empty request"),

	INVALID_DATE_STRING(9004, "invalid date string"),

	INVALID_VIN_STRING(9005, "invalid VIN string"),
	
	EMPTY_DATE_STRING(9007, "date can not be null"),
	
	INVALID_PROGRAM_ID(9008, "invalid program id"),
	
	INVALID_VIN_CRON_OR_EXETYPE(9009, "invalid cron_or_exetype"),
	;

	private int code;

	private String message;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private EResponseContent(int code, String message) {
		this.code = code;
		this.message = message;
	}

}
