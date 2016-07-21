package com.hoperun.oauth.dao;

import java.util.List;

import com.hoperun.oauth.entity.constants.AuthorizationStatus;
import com.hoperun.oauth.entity.po.AuthorizationApplications;

/**
 * @ClassName IAuthorizationApplicationsDao.java
 * @Description 
 * @author yin_changbao
 * @Date Jun 12, 2016 2:55:34 PM
 *
 */
public interface IAuthorizationApplicationsDao {
 
	
	AuthorizationApplications findWithOwnerAndRequester(String owner,String requester,Long arId);

	/**
	 * @param aa
	 */
	void saveAndFlush(AuthorizationApplications aa);

	/**
	 * @param username
	 * @param pending
	 * @return
	 */
	List<AuthorizationApplications> findWithResourceOwner(String username, AuthorizationStatus pending);

	/**
	 * @param username
	 * @param id
	 * @return
	 */
	AuthorizationApplications findWithIdAndUser(String username, Long id);

	/**
	 * @param aas
	 */
	void update(AuthorizationApplications aas) throws Exception ;

	/**
	 * @param owner
	 * @param user
	 * @param cid
	 */
	List<AuthorizationApplications> findAllWithOwnerAndRequester(String owner, String user, String cid);

}
