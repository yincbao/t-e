package com.hoperun.oauth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoperun.oauth.dao.IKrbRealmDao;
import com.hoperun.oauth.entity.po.KrbRealm;
import com.hoperun.oauth.service.IKrbRealmService;

/**
 * @ClassName KrbRealmServiceImpl.java
 * @Description 
 * @author yin_changbao
 * @Date Jun 17, 2016 11:44:26 AM
 *
 */
@Service
public class KrbRealmServiceImpl implements IKrbRealmService {

	@Autowired
	IKrbRealmDao krbRealmDao;
	
	@Override
	public KrbRealm findWithRealm(String realm) {
		
		return krbRealmDao.findWithRealm(realm);
	}

}
