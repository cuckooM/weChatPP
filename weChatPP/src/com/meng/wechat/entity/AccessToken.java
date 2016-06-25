package com.meng.wechat.entity;

/**
 * 微信接口调用凭证
 * @author meng
 */
public class AccessToken {
    
    /** access_token */
    private String accessToken;
    
    /** 有效时间。单位：秒。 */
    private int expiresIn;
    
    public AccessToken(){}
    
    public AccessToken(String accessToken, int expiresIn){
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
    
}
