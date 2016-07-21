package com.hoperun.oauth.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hoperun.oauth.dao.IAuthorizationApplicationsDao;
import com.hoperun.oauth.dao.IAuthorizationRequiermentDao;
import com.hoperun.oauth.dao.IKrbPrincipleDao;
import com.hoperun.oauth.entity.constants.AuthorizationStatus;
import com.hoperun.oauth.entity.po.AuthorizationApplications;
import com.hoperun.oauth.entity.po.AuthorizationRequierment;
import com.hoperun.oauth.entity.po.KrbUser;
import com.hoperun.oauth.service.IOAuthorizationApplicationsService;
import com.hoperun.oauth.utils.HttpClientHelper;
import com.hoperun.oauth.utils.StringUtil;

/**
 * @ClassName AuthorizationApplicationsServiceImpl.java
 * @Description 
 * @author yin_changbao
 * @Date Jun 12, 2016 1:53:48 PM
 *
 */
@Service
public class OAuthorizationApplicationsServiceImpl implements IOAuthorizationApplicationsService {
	
	private static final Logger logger = LoggerFactory.getLogger(OAuthorizationApplicationsServiceImpl.class);

	@Autowired
	IAuthorizationApplicationsDao authorizationApplicationsDao;
	
	@Autowired
	IKrbPrincipleDao krbPrincipleDao;
	
	@Autowired
	IAuthorizationRequiermentDao authorizationRequiermentDao;
	
	@Override
	public AuthorizationApplications findLatestWithRoles(String owner, String requester,Long arId) {
		if(StringUtil.isEmpty(owner)||StringUtil.isEmpty(requester))
			return null;
		return authorizationApplicationsDao.findWithOwnerAndRequester(owner,requester,arId);
	}

	@Override
	public void saveAndNotify(String reourceOwner, String applicant,String clientId) {
		
		authorizationApplicationsDao.saveAndFlush(buildAuthorizationApplication(reourceOwner,applicant,clientId,AuthorizationStatus.PENDING));
		notifyToWorkFlowSys(reourceOwner,applicant,clientId);

	}
	@Override
	public List<String> findAuhoris(String owner,String user,String cid){
		List<AuthorizationApplications> sd = authorizationApplicationsDao.findAllWithOwnerAndRequester(owner, user,cid);
		List<String> s = new ArrayList<String>();
		if(sd!=null){
			for(AuthorizationApplications aa:sd){
				s.add(aa.getAr().getName());
			}
		}
		return s;
	}

	/**
	 * @param reourceOwner
	 * @param applicant
	 * @param ar
	 */
	private void notifyToWorkFlowSys(String reourceOwner, String applicant, String ar) {
		HttpClientHelper.doGet("http://localhost:8080/act/oa/leave/fireAuthProc/"+reourceOwner+"/"+applicant+"/"+ar);
		
	}

	@Override
	public void approveAndNotify(String reourceOwner, String applicant, String clientId) {
		authorizationApplicationsDao.saveAndFlush(buildAuthorizationApplication(reourceOwner,applicant,clientId,AuthorizationStatus.OK));
		
	}

	/**
	 * @param reourceOwner
	 * @param applicant
	 * @param clientId
	 * @param ok
	 * @return
	 */
	private AuthorizationApplications buildAuthorizationApplication(String reourceOwner, String applicant, String clientId, AuthorizationStatus state) {
		KrbUser ro = krbPrincipleDao.findOneWithPrinc(reourceOwner);
		KrbUser at = krbPrincipleDao.findOneWithPrinc(applicant);
		AuthorizationRequierment ar = authorizationRequiermentDao.findOneWithClient(clientId);
		
		if(ro==null||ro==null)
			throw new RuntimeException("Authorization failed, neither reourceOwner nor applicant could be null");
		
		AuthorizationApplications aa = new AuthorizationApplications();
		aa.setApplicant(at);
		aa.setResourceOwner(ro);
		aa.setAr(ar);
		aa.setAuthorizationStatus(state);
		aa.setApplyDate(System.currentTimeMillis());
		aa.setValidity(7*24*3600*1000L);
		aa.setVersion(1.0D);
		aa.setLastUpdate(new Date());
		return aa;
	}

	@Override
	public List<AuthorizationApplications> findWaitForApproveByResourceOwner(String username) {
		return authorizationApplicationsDao.findWithResourceOwner(username,AuthorizationStatus.PENDING);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public void updateStatusById(Long id, String username, AuthorizationStatus fromElem) {
		AuthorizationApplications aas = authorizationApplicationsDao.findWithIdAndUser(username,id);
		if(!aas.isOverdate()){
			aas.setAuthorizationStatus(fromElem);
			try {
				authorizationApplicationsDao.update(aas);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
		}
		else
			logger.debug("application [{}] is overtime...",id);
	}

	/* (non-Javadoc)
	 * @see com.hoperun.oauth.service.IOAuthorizationApplicationsService#updateOwnerApprove(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void updateOwnerApprove(String id, String applicant, String req) {
		authorizationApplicationsDao.saveAndFlush(buildAuthorizationApplication(id,applicant,req,AuthorizationStatus.OK));
		
	}
	
	

}
