/**
 * 
 */
package com.hoperun.oauth.entity.po;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author yin_changbao
 *
 */
@Entity
@Table(name = "krb_realm")
public class KrbRealm {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(name = "realm")
	private String realm;
	
	@Column(name = "fid")
	private Long fid;
	
	@ManyToMany(mappedBy = "realms")
	private Set<KrbUser> users;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	public Long getFid() {
		return fid;
	}

	public void setFid(Long fid) {
		this.fid = fid;
	}

	public Set<KrbUser> getUsers() {
		return users;
	}

	public void setUsers(Set<KrbUser> users) {
		this.users = users;
	}
	
	
}
