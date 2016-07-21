package com.hoperun.oauth.service.impl;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoperun.oauth.Constants;
import com.hoperun.oauth.service.IOAuthClientService;
import com.hoperun.oauth.service.IOAuthServerService;

/**
 * @ClassName OAuthServerService.java
 * @Description 
 * @author yin_changbao
 * @Date Jun 12, 2016 4:36:48 PM
 *
 */
@Service
public class OAuthServerServiceimpl implements IOAuthServerService {

	private Cache cache;

    @Autowired
    private IOAuthClientService oauthClientService;

    @Autowired
    public OAuthServerServiceimpl(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("code-cache");
    }

    @Override
    public void addAuthCode(String authCode, String username) {
        cache.put(authCode, username);
    }

    @Override
    public void addAccessToken(String accessToken, String username) {
        cache.put(accessToken, username);
    }

    @Override
    public String getUsernameByAuthCode(String authCode) {
        return (String)cache.get(authCode);
    }

    @Override
    public String getUsernameByAccessToken(String accessToken) {
        return (String)cache.get(accessToken);
    }

    @Override
    public boolean checkAuthCode(String authCode) {
        return cache.get(authCode) != null;
    }

    @Override
    public boolean checkAccessToken(String accessToken) {
        return cache.get(accessToken) != null;
    }

    @Override
    public boolean checkClientId(String clientId) {
        return oauthClientService.findByClientId(clientId) != null;
    }

    @Override
    public boolean checkClientSecret(String clientSecret) {
        return oauthClientService.findByClientSecret(clientSecret) != null;
    }

    @Override
    public long getExpireIn() {
        return Constants.OAUTH_EXPIRE_TIME;
    }

}
