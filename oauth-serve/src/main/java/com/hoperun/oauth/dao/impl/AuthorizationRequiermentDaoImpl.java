package com.hoperun.oauth.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hoperun.oauth.dao.DaoSupport;
import com.hoperun.oauth.dao.IAuthorizationRequiermentDao;
import com.hoperun.oauth.entity.po.AuthorizationRequierment;

/**
 * @ClassName AuthorizationRequiermentDaoImpl.java
 * @Description 
 * @author yin_changbao
 * @Date Jun 14, 2016 3:05:20 PM
 *
 */
@Repository
public class AuthorizationRequiermentDaoImpl extends DaoSupport implements IAuthorizationRequiermentDao {

	private static final Logger logger = LoggerFactory.getLogger(AuthorizationRequiermentDaoImpl.class);

	@Override
	public AuthorizationRequierment findOneWithClient(String clientId) {
		String hql = "select ar from AuthorizationRequierment ar join ar.client cli where cli.clientId=?";
		
		List<AuthorizationRequierment> li = null;
		try {
			li = this.queryByHql(hql, new Object[]{clientId});
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		if(li==null||li.isEmpty())
			return null;
		return li.get(0);
	}

}
