package com.hoperun.oauth.entity.constants;

/**
 * @ClassName EPrincCategory.java
 * @Description R=4, W=2, RW=6, X=1, RWX=7
 * @author yin_changbao
 * @Date May 20, 2016 6:20:26 PM
 *
 */
public enum EPrivilege {
	
	R(4),RW(6);
	
	public int dValue;

	private EPrivilege(int dValue) {
		this.dValue = dValue;
	}
	
}
