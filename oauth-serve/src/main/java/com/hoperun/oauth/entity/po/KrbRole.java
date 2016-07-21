/**
 * 
 */
package com.hoperun.oauth.entity.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hoperun.oauth.entity.Transferable;
import com.hoperun.oauth.entity.constants.EPrivilege;
import com.hoperun.oauth.entity.vo.KrbRoleDto;

/**
 * @author yin_changbao
 *
 */
@Entity
@Table(name = "krb_role")
public class KrbRole implements Transferable<KrbRoleDto> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "role")
	private String role;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="privilege")
	private EPrivilege privilege;

	/* (non-Javadoc)
	 * @see com.hoperun.krb.agent.data.domain.Transferable#transfer()
	 */
	@Override
	public KrbRoleDto transfer() {
		// TODO Auto-generated method stub
		return null;
	}

}
