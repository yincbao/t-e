package com.hoperun.oauth.dao;

import java.util.List;
import java.util.Optional;

import com.hoperun.oauth.entity.po.KrbUser;
import com.hoperun.oauth.utils.Page;


/**
 * @ClassName IKrbPrincipleDao.java
 * @Description 
 * @author yin_changbao
 * @Date May 23, 2016 11:40:35 AM
 *
 */
public interface IKrbPrincipleDao{

	/**
	 * @param userName
	 * @return
	 */
	
	KrbUser findOneWithPrinc( String princ);

	/**
	 * @param transfer
	 */
	void save(KrbUser transfer) throws Exception;

	/**
	 * @param page
	 */
	void findAll(Page<KrbUser> page);

	/**
	 * @return
	 */
	List<KrbUser> findAll();

	/**
	 * @param id
	 * @return
	 */
	KrbUser findById(Long id);

	/**
	 * @param id
	 */
	void delete(Long id);

	/**
	 * @param user
	 */
	KrbUser update(KrbUser user);

	/**
	 * @param principal
	 * @return
	 */
	KrbUser findOneByPrinc(String principal);

}
