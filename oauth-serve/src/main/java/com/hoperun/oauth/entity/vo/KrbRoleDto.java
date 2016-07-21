package com.hoperun.oauth.entity.vo;

import com.hoperun.oauth.entity.Transferable;
import com.hoperun.oauth.entity.po.KrbRole;

/**
 * @ClassName KrbRoleDto.java
 * @Description 
 * @author yin_changbao
 * @Date May 23, 2016 1:20:03 PM
 *
 */
public class KrbRoleDto implements Transferable<KrbRole>{
	
	private Long id;
	
	private String roleName;
	
	private Integer privilege;

	/* (non-Javadoc)
	 * @see com.hoperun.krb.agent.data.domain.Transferable#transfer()
	 */
	@Override
	public KrbRole transfer() {
		// TODO Auto-generated method stub
		return null;
	}	

}
