package com.meng.wechat.entity;

/**
 * ΢�Žӿڵ���ƾ֤
 * @author meng
 */
public class AccessToken {
    
    /** access_token */
    private String accessToken;
    
    /** ��Чʱ�䡣��λ���롣 */
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
