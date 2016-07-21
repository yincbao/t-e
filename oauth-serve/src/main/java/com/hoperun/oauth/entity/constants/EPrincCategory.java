package com.hoperun.oauth.entity.constants;

/**
 * @ClassName EPrincCategory.java
 * @Description 
 * @author yin_changbao
 * @Date May 20, 2016 6:20:26 PM
 *
 */
public enum EPrincCategory {
	
	SERVICE("service"),USER("user");
	
	public String code;

	private EPrincCategory(String code) {
		this.code = code;
	}
	
}
