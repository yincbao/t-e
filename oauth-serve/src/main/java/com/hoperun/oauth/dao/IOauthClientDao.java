package com.hoperun.oauth.dao;

import java.util.List;

import com.hoperun.oauth.entity.po.KrbUser;
import com.hoperun.oauth.entity.po.OauthClient;
import com.hoperun.oauth.utils.Page;

/**
 * 
 * @ClassName IOauthClientDao.java
 * @Description 
 * @author yin_changbao
 * @Date May 25, 2016 4:25:49 PM
 *
 */
public interface IOauthClientDao {

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
	OauthClient findOneByClientSecret(String clientSecret);

	/**
	 * @param pageable
	 * @return
	 */
	Page<OauthClient> findAll(Page<OauthClient> page);

	/**
	 * @param client
	 * @return
	 */
	OauthClient save(OauthClient client);

	/**
	 * @param client
	 * @return
	 */
	OauthClient saveAndFlush(OauthClient client);

	/**
	 * @param id
	 */
	void delete(Long id);

	/**
	 * @return
	 */
	List<OauthClient> findAll();

	OauthClient findById(Long id);

}
