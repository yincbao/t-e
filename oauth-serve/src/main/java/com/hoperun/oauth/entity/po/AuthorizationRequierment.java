package com.hoperun.oauth.entity.po;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @ClassName AuthorizationRequierment.java
 * @Description 
 * @author yin_changbao
 * @Date Jun 7, 2016 5:39:15 PM
 *
 */
@Entity
@Table(name = "oauth_authorization_requierment")
public class AuthorizationRequierment {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String name;
	
	@ManyToOne
	private KrbUser resourceOwner;
	
	@OneToMany(mappedBy="arToRes", cascade={CascadeType.ALL},fetch=FetchType.EAGER)
	private Set<Authorization> authorizationsToResource;
	
	@OneToMany(mappedBy="arToUser", cascade={CascadeType.ALL},fetch=FetchType.EAGER )
	private Set<Authorization> authorizationsToUser;
	
	@Column
	private Double version;
	
	@OneToOne(optional = false, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "client_id", referencedColumnName = "id", unique = true)
	private OauthClient client;

	public KrbUser getResourceOwner() {
		return resourceOwner;
	}

	public void setResourceOwner(KrbUser resourceOwner) {
		this.resourceOwner = resourceOwner;
	}

	public Set<Authorization> getAuthorizationsToResource() {
		return authorizationsToResource;
	}

	public void setAuthorizationsToResource(Set<Authorization> authorizationsToResource) {
		this.authorizationsToResource = authorizationsToResource;
	}

	public Set<Authorization> getAuthorizationsToUser() {
		return authorizationsToUser;
	}

	public void setAuthorizationsToUser(Set<Authorization> authorizationsToUser) {
		this.authorizationsToUser = authorizationsToUser;
	}

	public Double getVersion() {
		return version;
	}

	public void setVersion(Double version) {
		this.version = version;
	}

	public OauthClient getClient() {
		return client;
	}

	public void setClient(OauthClient client) {
		this.client = client;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
