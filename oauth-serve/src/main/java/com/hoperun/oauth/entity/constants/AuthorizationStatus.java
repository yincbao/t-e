package com.hoperun.oauth.entity.constants;


/**
 * @ClassName AuthorizationStatus.java
 * @Description 
 * @author yin_changbao
 * @Date Jun 12, 2016 10:54:07 AM
 *
 */
public enum AuthorizationStatus {
	
	OK(0),DENY(1),PENDING(2),OVERDATE(3);
	
	public int statusCode;

	private AuthorizationStatus(int statusCode) {
		this.statusCode = statusCode;
	}
	
	public static AuthorizationStatus fromName(String name) {
		return valueOf(name);
	}

	public static AuthorizationStatus fromElem(int code) {
		AuthorizationStatus[] types = AuthorizationStatus.values();
		for (int x = 0; x < types.length; x++) {
			if (types[x].statusCode==(code)) {
				return types[x];
			}
		}
		throw new java.lang.IllegalArgumentException(
				"No enum found for the passed code: " + code);
	}
}
