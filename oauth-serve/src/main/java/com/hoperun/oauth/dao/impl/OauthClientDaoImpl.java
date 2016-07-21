package com.hoperun.oauth.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hoperun.oauth.dao.DaoSupport;
import com.hoperun.oauth.dao.IOauthClientDao;
import com.hoperun.oauth.entity.po.KrbUser;
import com.hoperun.oauth.entity.po.OauthClient;
import com.hoperun.oauth.utils.Page;
import com.hoperun.oauth.utils.PageUtil;

/**
 * @ClassName OauthClientDaoImpl.java
 * @Description
 * @author yin_changbao
 * @Date Jun 14, 2016 3:16:23 PM
 *
 */
@Repository
public class OauthClientDaoImpl extends DaoSupport implements IOauthClientDao {

	private static final Logger logger = LoggerFactory.getLogger(OauthClientDaoImpl.class);

	@Override
	public OauthClient findByClientId(String clientId) {
		String hql = "select distinct cli from OauthClient cli where cli.clientId=?";
		List<OauthClient> li = null;
		try {
			li = this.queryByHql(hql, new Object[] { clientId });
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if (li == null || li.isEmpty())
			return null;

		return li.get(0);
	}

	@Override
	public KrbUser findOwnerById(String clientId) {
		String hql = "select own from OauthClient cli join cli.owner own where cli.clientId=?";
		List<KrbUser> li = null;
		try {
			li = this.queryByHql(hql, new Object[] { clientId });
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if (li == null || li.isEmpty())
			return null;

		return li.get(0);
	}

	@Override
	public OauthClient findOneByClientSecret(String clientSecret) {
		String hql = "from OauthClient where clientSecret=?";
		List<OauthClient> li = null;
		try {
			li = this.queryByHql(hql, new Object[] { clientSecret });
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if (li == null || li.isEmpty())
			return null;

		return li.get(0);
	}

	@Override
	public Page<OauthClient> findAll(Page<OauthClient> page) {
		int[] i = PageUtil.parsePageNums(page);
		String hql = "from OauthClient";
		List<OauthClient> li = null;
		long count = 0L;
		if(page==null)
			page = new Page<OauthClient>();
		try {
			li = this.queryPageByHql(hql, null, i[0], i[1]);
			count = this.countByHql("select count(*) from OauthClient", null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		page.setResult(li);
		page.setTotalCount(count);
		return page;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public OauthClient save(OauthClient client) {
		try {
			super.save(client);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return client;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public OauthClient saveAndFlush(OauthClient client) {
		try {
			super.update(client);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return client;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public void delete(Long id) {
		try {
			String hql = "delete from OauthClient c where c.id=?";
			this.executeByHql(hql, new Object[]{id});
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public List<OauthClient> findAll() {
		String hql = "from OauthClient";
		long count = 0L;
		try {
			return this.queryByHql(hql, null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public OauthClient findById(Long id) {
		return super.queryById(OauthClient.class, id);
	}

}
