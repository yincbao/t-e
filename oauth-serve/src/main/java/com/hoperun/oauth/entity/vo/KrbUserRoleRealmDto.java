package com.hoperun.oauth.entity.vo;

import com.hoperun.oauth.entity.Transferable;
import com.hoperun.oauth.entity.po.KrbUserRoleRealm;

/**
 * @ClassName KrbUserRoleRealmDto.java
 * @Description 
 * @author yin_changbao
 * @Date May 23, 2016 12:04:29 PM
 *
 */
public class KrbUserRoleRealmDto implements Transferable<KrbUserRoleRealm>  {
	
	private Long resouce;
	
	private Integer privliage;
	
	private Long roleId;
	
	private String roleName;

	/* (non-Javadoc)
	 * @see com.hoperun.krb.agent.data.domain.IDomain#transfer()
	 */
	@Override
	public KrbUserRoleRealm transfer() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
