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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.hoperun.oauth.entity.Transferable;
import com.hoperun.oauth.entity.vo.KrbUserDto;

/**
 * @author yin_changbao
 *
 */
@Entity
@Table(name = "krb_user")
public class KrbUser implements Transferable<KrbUserDto>{
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(name = "princ")
	private String princ;
	
	@Column(name = "salt")
	private String salt;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "category", length=128)
	private String category;
	
	@Column(name = "is_deleted")
	private boolean isDeleted;
	
	@ManyToOne
	private KrbRealm defaultRealm;
	
	@ManyToMany
    @JoinTable(name = "krb_user_2_realm",
               joinColumns = @JoinColumn(name="user", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="realm", referencedColumnName="id"))
	private Set<KrbRealm> realms;
	
	@OneToMany
	private Set<KrbUserRoleRealm> resources;


	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPrinc() {
		return princ;
	}

	public void setPrinc(String princ) {
		this.princ = princ;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public KrbRealm getDefaultRealm() {
		return defaultRealm;
	}

	public void setDefaultRealm(KrbRealm defaultRealm) {
		this.defaultRealm = defaultRealm;
	}

	public Set<KrbRealm> getRealms() {
		return realms;
	}

	public void setRealms(Set<KrbRealm> realms) {
		this.realms = realms;
	}

	public Set<KrbUserRoleRealm> getResources() {
		return resources;
	}

	public void setResources(Set<KrbUserRoleRealm> resources) {
		this.resources = resources;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	/* (non-Javadoc)
	 * @see com.hoperun.krb.agent.data.domain.Transferable#transfer()
	 */
	@Override
	public KrbUserDto transfer() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return
	 */
	public String getCredentialsSalt() {
		
		return this.princ+this.salt;
	}
	
	
}
