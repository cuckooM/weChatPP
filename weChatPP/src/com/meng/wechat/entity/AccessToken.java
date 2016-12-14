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
    
    /** 获取凭证时的时间。单位：毫秒 */
    private Long time;
    
    public AccessToken(){}
    
    public AccessToken(String accessToken, int expiresIn, Long time){
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.time = time;
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

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}
    
}
