package com.hoperun.oauth.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hoperun.oauth.dao.IKrbPrincipleDao;
import com.hoperun.oauth.dao.IKrbRealmDao;
import com.hoperun.oauth.entity.po.KrbRealm;
import com.hoperun.oauth.entity.po.KrbUser;
import com.hoperun.oauth.entity.vo.KrbUserDto;
import com.hoperun.oauth.exception.UserNameDuplicateExceptioin;
import com.hoperun.oauth.service.IKrbPrincipleService;
import com.hoperun.oauth.service.PasswordHelper;
import com.hoperun.oauth.utils.Page;
import com.hoperun.oauth.utils.StringUtil;
import com.hoperun.oauth.utils.UUIDUtils;

/**
 * @ClassName KrbPrincipleServiceImpl.java
 * @Description
 * @author yin_changbao
 * @Date May 23, 2016 11:48:57 AM
 *
 */
@Service
public class KrbPrincipleServiceImpl implements IKrbPrincipleService {

	private static final Logger logger = LoggerFactory.getLogger(KrbPrincipleServiceImpl.class);
	
	@Autowired
	IKrbPrincipleDao krbPrincipleDao;
	@Autowired
	IKrbRealmDao krbRealmDao;
	@Autowired
	PasswordHelper passwordHelper;
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public KrbUserDto save(KrbUserDto krbPrincipleDto) {
		try {
			krbPrincipleDao.save(krbPrincipleDto.transfer());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return krbPrincipleDto;
	}

	@Override
	public KrbUser findValidRequireDefRealm(String userName) {
		if (StringUtil.isEmpty(userName))
			return null;
		else
			return krbPrincipleDao.findOneWithPrinc(userName);
	}
	
	@Override
	public Page<KrbUser> findAll(Page<KrbUser> page) {
		if(page==null||page.getPageNo()<1||page.getPageSize()<1)
			page = new Page<KrbUser>();
		krbPrincipleDao.findAll(page);
		return page;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public void createUser(KrbUser user) {
		try {
			createUser(user,null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<KrbUser> findAll() {
		
		return krbPrincipleDao.findAll();
	}

	@Override
	public KrbUser findOne(Long id) {
		if(id==null)
			return null;
		return	krbPrincipleDao.findById(id);
			
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public void updateUser(KrbUser user,Long preId)  {
		if(user==null||preId==null||user.getDefaultRealm()==null)
			return;
		KrbUser puser =	krbPrincipleDao.findById(preId);
		String defaultRealmName = user.getDefaultRealm().getRealm();
		try {
			if(!StringUtil.isEmpty(defaultRealmName)&&puser!=null){
				KrbRealm realm = krbRealmDao.findWithRealm(defaultRealmName);
				if(realm!=null)
					puser.setDefaultRealm(realm);
				
			}
			krbPrincipleDao.update(puser);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public void deleteUser(Long id) {
		if(id==null||id.longValue()<1)
			return;
		krbPrincipleDao.delete(id);
		
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public KrbUser changePassword(Long id, String newPassword) {
		KrbUser user = krbPrincipleDao.findById(id);
		if(user==null)
			return null;
		user.setPassword(newPassword);
		passwordHelper.encryptPassword(user);
		return krbPrincipleDao.update(user);
		
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={SQLException.class})
	public KrbUser createUser(KrbUser user, String defaultRealmName) throws UserNameDuplicateExceptioin {
		if(user!=null){
			KrbUser u = krbPrincipleDao.findOneWithPrinc(user.getPrinc());
			if(u!=null){
				throw new UserNameDuplicateExceptioin("User Name Already Exists");
			}
			user.setSalt(UUIDUtils.create().toString());
			if(!StringUtil.isEmpty(defaultRealmName)){
				KrbRealm realm = krbRealmDao.findWithRealm(defaultRealmName);
				if(realm!=null)
					user.setDefaultRealm(realm);
			}
			passwordHelper.encryptPassword(user);
			try {
				krbPrincipleDao.save(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return user;
		}
		return null;
		
	}


}
