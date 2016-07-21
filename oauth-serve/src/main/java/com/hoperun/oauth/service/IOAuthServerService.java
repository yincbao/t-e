package com.hoperun.oauth.service;

/**
 * 
 * @ClassName IOAuthServerService.java
 * @Description 
 * @author yin_changbao
 * @Date Jun 12, 2016 4:35:58 PM
 *
 */
public interface IOAuthServerService {

    public void addAuthCode(String authCode, String username);
    public void addAccessToken(String accessToken, String username);

    boolean checkAuthCode(String authCode);
    boolean checkAccessToken(String accessToken);

    String getUsernameByAuthCode(String authCode);
    String getUsernameByAccessToken(String accessToken);


    long getExpireIn();


    public boolean checkClientId(String clientId);

    public boolean checkClientSecret(String clientSecret);


}
