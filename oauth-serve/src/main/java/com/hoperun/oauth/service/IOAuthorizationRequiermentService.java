package com.hoperun.oauth.service;

import com.hoperun.oauth.entity.po.AuthorizationRequierment;


/**
 * @ClassName IAuthorizationRequiermentService.java
 * @Description 
 * @author yin_changbao
 * @Date Jun 12, 2016 1:53:04 PM
 *
 */
public interface IOAuthorizationRequiermentService {

	/**
	 * @param clientId
	 * @return
	 */
	AuthorizationRequierment findReqmByClient(String clientId);

}
