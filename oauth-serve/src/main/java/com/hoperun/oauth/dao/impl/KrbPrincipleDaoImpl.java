package com.hoperun.oauth.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hoperun.oauth.dao.DaoSupport;
import com.hoperun.oauth.dao.IKrbPrincipleDao;
import com.hoperun.oauth.entity.po.KrbUser;
import com.hoperun.oauth.utils.Page;
import com.hoperun.oauth.utils.PageUtil;

/**
 * @ClassName KrbPrincipleDaoImpl.java
 * @Description 
 * @author yin_changbao
 * @Date Jun 14, 2016 3:08:45 PM
 *
 */
@Repository
public class KrbPrincipleDaoImpl extends DaoSupport implements IKrbPrincipleDao {

	private static final Logger logger = LoggerFactory.getLogger(KrbPrincipleDaoImpl.class);
	
	@Override
	public KrbUser findOneWithPrinc(String princ) {
		String hql = "select distinct princs from KrbUser princs where princs.princ = ?";
		try {
			List<KrbUser> li = this.queryByHql(hql, new Object[]{princ});
			if(li!=null&&!li.isEmpty()){
				return  li.get(0);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return null;
	}

	@Override
	public void save(KrbUser transfer) throws Exception {
		super.save(transfer);
	}

	@Override
	public void findAll(Page<KrbUser> page) {
		String hql = "from KrbUser";
		try {
			int[] i = PageUtil.parsePageNums(page);
			List<KrbUser> li = this.queryPageByHql(hql, null, i[0], i[1]);
			if(li!=null&&!li.isEmpty()){
				page.setResult(li);
			}
			page.setTotalCount(this.countByHql("select count(*) from KrbUser", null));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
	}

	@Override
	public List<KrbUser> findAll() {
		String hql = "from KrbUser";
		try {
			return this.queryByHql(hql, null);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.hoperun.oauth.dao.IKrbPrincipleDao#findById(java.lang.Long)
	 */
	@Override
	public KrbUser findById(Long id) {
		String hql = "select distinct princs from KrbUser princs where princs.id = ?";
		try {
			List<KrbUser> li = this.queryByHql(hql, new Object[]{id});
			if(li!=null&&!li.isEmpty()){
				return  li.get(0);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return null;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public void delete(Long id) {
		String hql = "delete from KrbUser where id=?";
		try {
			this.executeByHql(hql, new Object[]{id});
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
	}

	@Override
	public KrbUser update(KrbUser user) {
		try {
			return super.update(user);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}

	@Override
	public KrbUser findOneByPrinc(String principal) {
		// TODO Auto-generated method stub
		return null;
	}

}
