package org.baoting.te.constaints;

/**
 * 
 * ClassName: ExceptionCodeEnum
 * @description
 * @author yin_changbao
 * @Date   Oct 26, 2015
 *
 */
public enum ExceptionCodeEnum {
	NETWORK_EXCEPTION("500","internal server error"),
	
	INTERFACE_EXCEPTION_NULL_PROGRAM_NOR_VIN("9001","please provide a programId or a vin code"),
	
	INTERFACE_EXCEPTION_UNDEFINED_RUN_TYPE("9002","run type should be clearfied"),
	
	INTERFACE_EXCEPTION_EMPTY_REQUEST("9003","empty request"),
	
	INTERFACE_EXCEPTION_INVALID_DATE_STRING("9004","invalid date string"),
 
	numberFormatException("5001","config error, should be a number"),
	
	InconsistentDeviceId("1001","device Id changed in a session"),
	
	emptyDeviceId("10021","device Id can't be null"),
	//drools excetion 8***
	droolsRuleCompileError("8001","drools Rule compiling Error"),
	
	droolsRuleNotExists("8002","no valid drools rules found."),
	
	fsDroolsRuleBaseNotExists("8003","file system Drools RuleBase Not Exists."),
	
	droolsRuleRepositoryNotExists("8004","drools Rule Repository Not Exists.check your system.properties attr:kie.workbench.git.repository"),
	
	
	//mongo exception 7***
	mongoTryingQueryAll("7001","fobidden qeury all collection from mongodb, because of  high risk of OOM"),
	mongoFindOneError("7002","mongo Find One Error"),
	
	//rdbms exception 6***
	badInputParameter("6001","bad Input Parameter"),
	
	connectionIsNUll("6002","connection Is NUll"),
	
	noSuchPK("6003","no Such PK")
	
	;
	private String expCode;
	private String expMsg;
	public String getExpCode() {
		return expCode;
	}
	public void setExpCode(String expCode) {
		this.expCode = expCode;
	}
	public String getExpMsg() {
		return expMsg;
	}
	public void setExpMsg(String expMsg) {
		this.expMsg = expMsg;
	}
	
	private ExceptionCodeEnum(String expCode, String expMsg) {
		this.expCode = expCode;
		this.expMsg = expMsg;
	}
	
	public static ExceptionCodeEnum fromName(String name) {
		return valueOf(name);
	}

	public static ExceptionCodeEnum fromElem(String code) {
		ExceptionCodeEnum[] types = ExceptionCodeEnum.values();
		for (int x = 0; x < types.length; x++) {
			if (types[x].expCode.equals(code)||types[x].expMsg.equals(code)) {
				return types[x];
			}
		}
		throw new java.lang.IllegalArgumentException(
				"No enum found for the passed code: " + code);
	}
}
