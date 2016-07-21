package com.hoperun.oauth.entity.vo;

import java.util.List;

import com.hoperun.oauth.entity.Transferable;
import com.hoperun.oauth.entity.po.KrbUser;

/**
 * @ClassName KrbUserDto.java
 * @Description 
 * @author yin_changbao
 * @Date May 23, 2016 11:53:38 AM
 *
 */
public class KrbUserDto implements Transferable<KrbUser>{
	
	private static final long serialVersionUID = 1L;

	private Long userId;
	
	private String userName;
	
	private String password;
	
	private List<KrbUserRoleRealmDto> resourceList;

	/* (non-Javadoc)
	 * @see com.hoperun.krb.agent.data.domain.IDomain#transfer()
	 */
	@Override
	public KrbUser transfer() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
