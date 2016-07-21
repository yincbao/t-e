package com.hoperun.oauth.service;

import java.util.List;

import com.hoperun.oauth.entity.po.KrbUser;
import com.hoperun.oauth.exception.UserNameDuplicateExceptioin;
import com.hoperun.oauth.utils.Page;


/**
 * @ClassName IKrbPrincipleService.java
 * @Description 
 * @author yin_changbao
 * @Date May 23, 2016 11:48:28 AM
 *
 */
public interface IKrbPrincipleService {

	/**
	 * @param userName
	 * @return
	 */
	KrbUser findValidRequireDefRealm(String userName);
	
	Page<KrbUser> findAll(Page<KrbUser> page);
	
	List<KrbUser> findAll();

	/**
	 * @param user
	 */
	void createUser(KrbUser user);

	/**
	 * @param id
	 * @return
	 */
	KrbUser findOne(Long id);

	/**
	 * @param user
	 * @param id 
	 */
	void updateUser(KrbUser user, Long id);

	/**
	 * @param id
	 */
	void deleteUser(Long id);

	/**
	 * @param id
	 * @param newPassword
	 */
	KrbUser changePassword(Long id, String newPassword);

	/**
	 * @param user
	 * @param defaultRealmName
	 * @throws Exception 
	 */
	KrbUser createUser(KrbUser user, String defaultRealmName) throws UserNameDuplicateExceptioin;

}
