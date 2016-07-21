package com.hoperun.oauth.entity.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.hoperun.oauth.entity.constants.AuthorizationStatus;

/**
 * @ClassName AuthorizationApplications.java
 * @Description 
 * @author yin_changbao
 * @Date Jun 12, 2016 10:49:12 AM
 *
 */
@Entity
@Table(name = "oauth_authorization_applications")
public class AuthorizationApplications {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private KrbUser resourceOwner;
	
	@ManyToOne
	private KrbUser applicant;
	
	@OneToOne
	AuthorizationRequierment ar;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "status")
	private AuthorizationStatus authorizationStatus;
	@Column
	private Long applyDate;
	@Column
	private Long validity;
	@Column
	@DateTimeFormat(style = "M-")
	private Date lastUpdate;
	@Column
	private double version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public KrbUser getResourceOwner() {
		return resourceOwner;
	}

	public void setResourceOwner(KrbUser resourceOwner) {
		this.resourceOwner = resourceOwner;
	}

	public KrbUser getApplicant() {
		return applicant;
	}

	public void setApplicant(KrbUser applicant) {
		this.applicant = applicant;
	}

	public AuthorizationRequierment getAr() {
		return ar;
	}

	public void setAr(AuthorizationRequierment ar) {
		this.ar = ar;
	}

	public AuthorizationStatus getAuthorizationStatus() {
		return authorizationStatus;
	}

	public void setAuthorizationStatus(AuthorizationStatus authorizationStatus) {
		this.authorizationStatus = authorizationStatus;
	}

	public Long getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Long applyDate) {
		this.applyDate = applyDate;
	}

	public Long getValidity() {
		return validity;
	}

	public void setValidity(Long validity) {
		this.validity = validity;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public double getVersion() {
		return version;
	}

	public void setVersion(double version) {
		this.version = version;
	}

	/**
	 * @return
	 */
	public boolean isOverdate() {
		if(this.validity==null||validity.longValue()<1)
			return false;
		return (this.applyDate+this.validity)<System.currentTimeMillis();
	}
	
	
}
