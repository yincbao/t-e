package com.hoperun.oauth.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hoperun.oauth.dao.DaoSupport;
import com.hoperun.oauth.dao.IKrbRealmDao;
import com.hoperun.oauth.entity.po.KrbRealm;
import com.hoperun.oauth.utils.StringUtil;

/**
 * @ClassName KrbRealmDaoImpl.java
 * @Description 
 * @author yin_changbao
 * @Date Jun 14, 2016 3:13:07 PM
 *
 */
@Repository
public class KrbRealmDaoImpl extends DaoSupport implements IKrbRealmDao {

	
	private static final Logger  logger = LoggerFactory.getLogger(KrbRealmDaoImpl.class);
	
	@Override
	public KrbRealm findWithRealm(String defaultRealmName) {
		if(StringUtil.isEmpty(defaultRealmName))
			return null;
		String hql = "select distinct kr from KrbRealm  kr where UPPER(kr.realm)=?";
		try {
			List<KrbRealm> li = this.queryByHql(hql, new Object[]{defaultRealmName.toUpperCase()});
			if(li!=null&&!li.isEmpty()){
				return  li.get(0);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}

}
