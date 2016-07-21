package com.hoperun.oauth.service;

import com.hoperun.oauth.entity.po.KrbRealm;

/**
 * @ClassName IKrbRealmService.java
 * @Description 
 * @author yin_changbao
 * @Date Jun 17, 2016 11:41:01 AM
 *
 */
public interface IKrbRealmService {

	/**
	 * @param realm
	 */
	KrbRealm findWithRealm(String realm);

}
