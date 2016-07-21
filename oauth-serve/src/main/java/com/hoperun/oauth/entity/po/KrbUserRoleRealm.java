package com.hoperun.oauth.entity.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.hoperun.oauth.entity.Transferable;
import com.hoperun.oauth.entity.vo.KrbUserRoleRealmDto;

/**
 * @author yin_changbao
 *
 */
@Entity
@Table(name = "krb_user_role_realm")
public class KrbUserRoleRealm implements Transferable<KrbUserRoleRealmDto>{
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(name = "user")
	private KrbUser user;
	
	@ManyToOne
	private KrbRole role;
	
	@ManyToOne
	private KrbRealm realm;

	/* (non-Javadoc)
	 * @see com.hoperun.krb.agent.data.domain.IDomain#transfer()
	 */
	@Override
	public KrbUserRoleRealmDto transfer() {
		
		return null;
	}

	
}
