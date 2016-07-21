package com.hoperun.oauth.entity.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.hoperun.oauth.entity.Transferable;
import com.hoperun.oauth.entity.vo.AuthorizationDto;

/**
 * @ClassName Authorization.java
 * @Description 
 * @author yin_changbao
 * @Date Jun 7, 2016 5:09:27 PM
 *
 */
@Entity
@Table(name = "oauth_authorization")
public class Authorization implements Transferable<AuthorizationDto>{

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private String resource;
	@Column
	private String code = "";
	@Column
	private Integer operation;
	@Column
	private boolean isRequired;
	
	@Column
	private Long date;
	
	@Column
	private Long duration;
	
	@ManyToOne
	private AuthorizationRequierment arToRes;
	
	@ManyToOne
	private AuthorizationRequierment arToUser;


	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}


	public AuthorizationRequierment getArToRes() {
		return arToRes;
	}

	public void setArToRes(AuthorizationRequierment arToRes) {
		this.arToRes = arToRes;
	}

	public AuthorizationRequierment getArToUser() {
		return arToUser;
	}

	public void setArToUser(AuthorizationRequierment arToUser) {
		this.arToUser = arToUser;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}

	public Integer getOperation() {
		return operation;
	}

	public void setOperation(Integer operation) {
		this.operation = operation;
	}

	public boolean isRequired() {
		return isRequired;
	}




	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}




	@Override
	public AuthorizationDto transfer() {
		// TODO Auto-generated method stub
		return null;
	}

}
