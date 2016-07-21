package com.hoperun.oauth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoperun.oauth.dao.IAuthorizationRequiermentDao;
import com.hoperun.oauth.entity.po.AuthorizationRequierment;
import com.hoperun.oauth.service.IOAuthorizationRequiermentService;
import com.hoperun.oauth.utils.StringUtil;

/**
 * @ClassName AuthorizationRequiermentServiceImpl.java
 * @Description 
 * @author yin_changbao
 * @Date Jun 12, 2016 1:53:21 PM
 *
 */
@Service
public class OAuthorizationRequiermentServiceImpl implements IOAuthorizationRequiermentService {

	@Autowired
	IAuthorizationRequiermentDao authorizationRequiermentRepository;

	@Override
	public AuthorizationRequierment findReqmByClient(String clientId) {
		if(StringUtil.isEmpty(clientId))
			return null;
		return authorizationRequiermentRepository.findOneWithClient(clientId);
	}
	

}
