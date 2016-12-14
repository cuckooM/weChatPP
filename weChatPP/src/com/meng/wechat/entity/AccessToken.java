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
    
    /** ��ȡƾ֤ʱ��ʱ�䡣��λ������ */
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
