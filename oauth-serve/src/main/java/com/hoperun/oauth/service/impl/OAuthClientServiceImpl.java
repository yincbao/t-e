package com.hoperun.oauth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoperun.oauth.dao.IOauthClientDao;
import com.hoperun.oauth.entity.po.KrbUser;
import com.hoperun.oauth.entity.po.OauthClient;
import com.hoperun.oauth.service.IOAuthClientService;
import com.hoperun.oauth.utils.Page;
import com.hoperun.oauth.utils.UUIDUtils;

/**
 * @ClassName OauthClientServiceImpl.java
 * @Description
 * @author yin_changbao
 * @Date May 25, 2016 4:18:47 PM
 *
 */
@Service
public class OAuthClientServiceImpl implements IOAuthClientService {

	private IOauthClientDao oauthClientDao;

	@Autowired
	public OAuthClientServiceImpl(IOauthClientDao oauthClientRepository) {
		this.oauthClientDao = oauthClientRepository;
	}

	@Override
	public Page<OauthClient> findAll(Page<OauthClient> pageable) {
		
		return oauthClientDao.findAll(pageable);
	}

	/**
	 * OauthClient 对象关联较多，其中 OauthClient 到 AuthorizationRequierment是唯一从属，cascade
	 * ALL AuthorizationRequierment 到 Authorization OauthClient 到krbUser
	 * 和KrbRealm 为非唯一从属，所以需要保证 krbUser与Realm在保存client前就是持久化状态下的。
	 */
	@Override
	public OauthClient save(OauthClient client) {
		client.setClientId(UUIDUtils.create().toString());
		client.setClientSecret(UUIDUtils.create().toString());
		return oauthClientDao.save(client);
	}

	@Override
	public OauthClient update(OauthClient client) {

		return oauthClientDao.saveAndFlush(client);
	}

	@Override
	public void delete(Long id) {
		oauthClientDao.delete(id);

	}

	@Override
	public boolean checkClientId(String clientId) {
		return oauthClientDao.findByClientId(clientId) != null;
	}

	@Override
	public OauthClient findByClientId(String clientId) {
		return oauthClientDao.findByClientId(clientId);
	}

	@Override
	public KrbUser findOwnerById(String clientId) {
		return oauthClientDao.findOwnerById(clientId);
	}

	@Override
	public OauthClient findByClientSecret(String clientSecret) {
		return oauthClientDao.findOneByClientSecret(clientSecret);
	}


	@Override
	public OauthClient findById(Long id) {
		return oauthClientDao.findById(id);
	}


}
