package com.hoperun.oauth.service;

import java.util.List;

import com.hoperun.oauth.entity.po.KrbUser;
import com.hoperun.oauth.entity.po.OauthClient;
import com.hoperun.oauth.utils.Page;

/**
 * @ClassName IOauthClientService.java
 * @Description 
 * @author yin_changbao
 * @Date May 25, 2016 4:18:28 PM
 *
 */
public interface IOAuthClientService {


	/**
	 * @param pageable
	 * @return
	 */
	Page<OauthClient> findAll(Page<OauthClient> pageable);

	/**
	 * @param client
	 * @return
	 */
	OauthClient save(OauthClient client);

	/**
	 * @param client
	 * @return
	 */
	OauthClient update(OauthClient client);

	/**
	 * @param id
	 */
	void delete(Long id);

	/**
	 * @param clientId
	 * @return
	 */
	boolean checkClientId(String clientId);

	/**
	 * @param clientId
	 * @return
	 */
	OauthClient findByClientId(String clientId);

	/**
	 * @param clientId
	 * @return
	 */
	KrbUser findOwnerById(String clientId);

	/**
	 * @param clientSecret
	 * @return
	 */
	OauthClient findByClientSecret(String clientSecret);

	/**
	 * @param id
	 * @return
	 */
	OauthClient findById(Long id);

}
