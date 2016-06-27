package com.meng.wechat.service;

import net.sf.json.JSONObject;

import com.meng.wechat.entity.AccessToken;
import com.meng.wechat.util.WeChatUtils;

/**
 * 处理微信公众平台消息的服务类
 * @author meng
 */
public class WeChatService {
	
	/**
	 * 向微信服务器请求获取access_token
	 * @return access_token
	 */
	public AccessToken requestAccessToken (String appId, String appSecret){
	    String requestUrl = WeChatUtils.GET_ACCESSTOKEN_URL.replace("APPID", appId).replace("APPSECRET", appSecret);
	    JSONObject value = WeChatUtils.httpsRequest(requestUrl, "GET", null);
	    String access_token = value.getString("access_token");
	    int expires_in = -1;
	    expires_in = value.getInt("expires_in");
	    if (null == access_token || -1 == expires_in){
	        String errcode = value.getString("errcode");
	        String errmsg = value.getString("errmsg");
	        System.out.println("The error code is " + errcode + ". The error message is " + errmsg);
	        return null;
	    }
	    AccessToken accessToken = new AccessToken(access_token, expires_in);
	    return accessToken;
	}

	

}
