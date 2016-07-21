package com.hoperun.oauth.dao.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hoperun.oauth.dao.DaoSupport;
import com.hoperun.oauth.dao.IAuthorizationApplicationsDao;
import com.hoperun.oauth.entity.constants.AuthorizationStatus;
import com.hoperun.oauth.entity.po.AuthorizationApplications;


/**
 * @ClassName AuthorizationApplicationsDaoImpl.java
 * @Description 
 * @author yin_changbao
 * @Date Jun 14, 2016 2:56:37 PM
 *
 */
@Repository
public class AuthorizationApplicationsDaoImpl extends DaoSupport implements IAuthorizationApplicationsDao {

	private static final Logger logger = LoggerFactory.getLogger(AuthorizationApplicationsDaoImpl.class);
	@Override
	public AuthorizationApplications findWithOwnerAndRequester(String owner, String requester,Long arId) {
		String hql ="select ao from AuthorizationApplications ao where ao.applyDate >= (select max(aa.applyDate) "
				+ "from AuthorizationApplications aa join aa.resourceOwner owner join aa.applicant apper "
				+ "where owner.princ=? and apper.princ=? and aa.ar.id=?)";
		List<AuthorizationApplications> li = null;
		try {
			li = this.queryByHql(hql, new Object[]{owner,requester,arId});
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		if(li!=null&!li.isEmpty())
			return li.get(0);
		return null;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public void saveAndFlush(AuthorizationApplications aa) {
		try {
			super.save(aa);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

	@Override
	public List<AuthorizationApplications> findWithResourceOwner(String username, AuthorizationStatus status) {
		String hql = " from AuthorizationApplications aas where aas.resourceOwner.princ=? and aas.authorizationStatus=?";
		List<AuthorizationApplications> li = null;
		try {
			li = this.queryByHql(hql, new Object[]{username,status});
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return li;
	}

	@Override
	public AuthorizationApplications findWithIdAndUser(String username, Long id) {
		String hql = " from AuthorizationApplications aas where aas.id=? and  aas.resourceOwner.princ=? ";
		List<AuthorizationApplications> li = null;
		try {
			li = this.queryByHql(hql, new Object[]{id,username});
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return li==null||li.isEmpty()?null:li.get(0);
	}

	@Override
	public void update(AuthorizationApplications aas) throws Exception {
		super.update(aas);
		
	}

	@Override
	public List<AuthorizationApplications> findAllWithOwnerAndRequester(String owner, String user, String cid) {
		String hql ="select aa "
				+ "from AuthorizationApplications aa join aa.resourceOwner owner join aa.applicant apper "
				+ "where aa.authorizationStatus=0 and owner.princ=? and apper.princ=? and aa.ar.client.clientId=?";
		List<AuthorizationApplications> li = null;
		try {
			li = this.queryByHql(hql, new Object[]{owner,user,cid});
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return li;
	}

}
