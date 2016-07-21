package com.hoperun.oauth.dao;

import com.hoperun.oauth.entity.po.KrbRealm;



/**
 * @ClassName IKrbRealmDao.java
 * @Description 
 * @author yin_changbao
 * @Date May 23, 2016 11:40:35 AM
 *
 */
public interface IKrbRealmDao{

	/**
	 * @param defaultRealmName
	 * @return
	 */
	KrbRealm findWithRealm(String defaultRealmName);

}
