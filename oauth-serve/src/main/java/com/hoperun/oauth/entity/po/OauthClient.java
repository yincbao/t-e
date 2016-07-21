package com.hoperun.oauth.entity.po;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @ClassName OauthClient.java
 * @Description
 * @author yin_changbao
 * @Date May 25, 2016 4:11:03 PM
 *
 */
@Entity
@Table(name = "oauth_client")
public class OauthClient implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="client_name")
	private String clientName;
	
	@Column(name="client_id")
	private String clientId;
	
	@Column(name="client_secret")
	private String clientSecret;
		
	@ManyToOne 
	private KrbUser owner;
	
	@OneToOne
	private KrbRealm clientRealm;
	
	@OneToOne(cascade={CascadeType.ALL},mappedBy="client")
	private AuthorizationRequierment ar;

	
	public AuthorizationRequierment getAr() {
		return ar;
	}

	public void setAr(AuthorizationRequierment ar) {
		this.ar = ar;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		OauthClient client = (OauthClient) o;

		if (id != null ? !id.equals(client.id) : client.id != null)
			return false;

		return true;
	}

	public KrbUser getOwner() {
		return owner;
	}

	public void setOwner(KrbUser owner) {
		this.owner = owner;
	}

	public KrbRealm getClientRealm() {
		return clientRealm;
	}

	public void setClientRealm(KrbRealm clientRealm) {
		this.clientRealm = clientRealm;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "Client{" + "id=" + id + ", clientName='" + clientName + '\'' + ", clientId='" + clientId + '\'' + ", clientSecret='" + clientSecret
				+ '\'' + '}';
	}
}
