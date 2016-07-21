package com.hoperun.oauth.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * 
 * ClassName: DaoSupport
 * @description
 * @author yin_changbao
 * @Date   Jul 22, 2015
 * 
 * @param <T>
 */
@Repository
public class DaoSupport {

	private static final Log logger = LogFactory.getLog(DaoSupport.class);
	@PersistenceContext(unitName = "persistenceUnit")
	protected EntityManager em;
	
	public <T>T save(T entity) throws Exception{
		em.persist(entity);
		return entity;
	}
	
	@Transactional(value="transactionManager",propagation=Propagation.REQUIRED,readOnly=false)
	public <T>T update(T entity ) throws Exception{
		return em.merge(entity);
	}

	@Transactional(value="transactionManager",propagation=Propagation.REQUIRED,readOnly=false)
	public <T> T delete(T entity ) throws Exception{
		em.remove(entity);
		return entity;
	}

	@Transactional(value="transactionManager",propagation=Propagation.REQUIRED,readOnly=false)
	public  <T> void batchDelete( Class<T> entityClass, Object[] entityids) throws Exception{
		for(Object id : entityids) {
			em.remove(em.getReference(entityClass, id));
		}
	}
	
	@Transactional(value="transactionManager",propagation=Propagation.REQUIRED,readOnly=false)
	public <T> void  batchedSave( List<T> entities) {
		try {
			Session session = (Session) em.getDelegate();
			session.setFlushMode(FlushMode.MANUAL);
			int batchSize = 50;
			int i = 0;
			for (T entity : entities) {
				session.save(entity);
				i++;
				if (i % batchSize == 0) {
					session.flush();
					session.clear();
				}
			}
			session.flush();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	
	public <T> T queryById( Class<T> entityClass, Object entityid) {
		return em.find(entityClass, entityid);
	}
	
	public <T> List<T> queryByHql( final String hql, final Object[] parameters)  throws Exception {
		try{
			Query query = em.createQuery(hql);
			setQueryParams(query,parameters);
			return query.getResultList();
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw e;
		}
		
	}
	public <T> List<T> queryBySql( final String sql, final Object[] parameters)  throws Exception {
		try{
			Query query = em.createNativeQuery(sql);
			setQueryParams(query,parameters);
			return query.getResultList();
		}catch(Exception e){
			logger.error(e);
			throw e;
		}
		
	}
	
	public <T> List<T> queryPageByHql( final String hql, final Object[] parameters,
			final int page, final int pageSize) throws Exception {
		try{
			Query query = em.createQuery(hql);
			setQueryParams(query,parameters);
			if (page == -1 || pageSize == -1) {
				return query.getResultList();
			} else {
				query.setFirstResult(page);
				query.setMaxResults(pageSize);
				return query.getResultList();
			}
		}catch(Exception e){
			logger.error(e);
			throw e;
		}
		
	}
	
	public <T> List<T> queryPageBySql( final String sql, final Object[] parameters,
			final int page, final int pageSize) throws Exception{
		try{
			Query query = em.createNativeQuery(sql);
			setQueryParams(query,parameters);
			if (page == -1 || pageSize == -1) {
				return query.getResultList();
			} else {
				query.setFirstResult(page);
				query.setMaxResults(pageSize);
				return query.getResultList();
			}
		}catch(Exception e){
			logger.error(e);
			throw e;
		}
		
	}
	

	public int executeByHql( final String hql, final Object[] parameters) throws Exception{
		try{
			Query query = em.createQuery(hql);
			setQueryParams(query,parameters);
			return query.executeUpdate();
		}catch(Exception e){
			logger.error(e);
			throw e;
		}
		
	}
	
	public int executeBySql( final String sql, final Object[] parameters) throws Exception{
		try{
			Query query = em.createNativeQuery(sql);
			setQueryParams(query,parameters);	
			return query.executeUpdate();
		}catch(Exception e){
			logger.error(e);
			throw e;
		}
	}
	
	
	public long countByHql( final String hql, final Object[] parameters) throws Exception{
		try{
			Query query = em.createQuery(hql);
			setQueryParams(query,parameters);
			Object obj = query.getSingleResult();
			Long totalSize = Long.parseLong(obj.toString());
			return totalSize!=null?totalSize.longValue():0;
		}catch(Exception e){
			logger.error(e);
			throw e;
		}
	}
	
	public long countBySql( final String sql, final Object[] parameters) throws Exception{
		try{
			Query query = em.createNativeQuery(sql);
			setQueryParams(query,parameters);
			Object obj = query.getSingleResult();
			Long totalSize = Long.parseLong(obj.toString());
			return totalSize!=null?totalSize.longValue():0;
		}catch(Exception e){
			logger.error(e);
			throw e;
		}
	}
	
	private void setQueryParams(Query query,Object[] queryParams) {
		if(null != queryParams && 0 < queryParams.length) {
			for (int i = 0; i < queryParams.length; i++) {
				query.setParameter(i+1, queryParams[i]);
			}
		}
	}
	
	protected <T> String getEntityName(T entity) {
		return entity.getClass().getSimpleName();
	}
	
	protected static final ThreadLocal<Connection> session = new ThreadLocal<Connection>();
	
	protected  Connection createConnection(){
		java.sql.Connection connection = session.get();
		if(connection==null){
			Session hiberNateSession = (org.hibernate.Session) this.em.getDelegate();
			SessionFactoryImplementor sf = (SessionFactoryImplementor) hiberNateSession.getSessionFactory();
			try {
				connection  = sf.getConnectionProvider().getConnection();
				if(connection !=null)
					session.set(connection);
				else
					throw new RuntimeException("connectionIsNUll");
			} catch (SQLException e) {
				logger.error(e.getMessage(),e);
			}
		}
		return connection;
	}
}
