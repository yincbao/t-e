package com.hoperun.oauth.dao;

import com.hoperun.oauth.entity.po.AuthorizationRequierment;

/**
 * @ClassName IAuthorizationRequiermentDao.java
 * @Description 
 * @author yin_changbao
 * @Date Jun 12, 2016 3:11:47 PM
 *
 */
public interface IAuthorizationRequiermentDao {

	/**
	 * @param clientId
	 * @return
	 */
	
	AuthorizationRequierment findOneWithClient(String clientId);

}
