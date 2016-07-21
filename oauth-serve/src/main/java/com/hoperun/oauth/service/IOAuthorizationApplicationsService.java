package com.hoperun.oauth.service;

import java.util.List;

import com.hoperun.oauth.entity.constants.AuthorizationStatus;
import com.hoperun.oauth.entity.po.AuthorizationApplications;

/**
 * @ClassName IAuthorizationApplicationsService.java
 * @Description 
 * @author yin_changbao
 * @Date Jun 12, 2016 1:53:36 PM
 *
 */
public interface IOAuthorizationApplicationsService {

	/**
	 * @param owner
	 * @param requester
	 * @param areId 
	 * @return
	 */
	AuthorizationApplications findLatestWithRoles(String owner, String requester, Long areId);

	/**
	 * @param reourceOwner
	 * @param applicant
	 * @param clientId 
	 */
	void saveAndNotify(String reourceOwner, String applicant, String clientId);

	/**
	 * @param username
	 * @param princ
	 * @param clientId
	 */
	void approveAndNotify(String username, String princ, String clientId);

	/**
	 * @param username
	 * @return
	 */
	List<AuthorizationApplications> findWaitForApproveByResourceOwner(String username);

	/**
	 * @param id
	 * @param username
	 * @param fromElem
	 */
	void updateStatusById(Long id, String username, AuthorizationStatus fromElem);

	/**
	 * @param id
	 * @param applicant
	 * @param req
	 */
	void updateOwnerApprove(String id, String applicant, String req);

	/**
	 * @param user
	 * @param owner
	 * @param cid
	 * @return
	 */
	List<String> findAuhoris(String user, String owner, String cid);

}
