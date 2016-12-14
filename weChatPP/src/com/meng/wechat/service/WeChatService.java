package com.meng.wechat.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.meng.wechat.entity.AccessToken;
import com.meng.wechat.util.WeChatUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * ����΢�Ź���ƽ̨��Ϣ�ķ�����
 * @author meng
 */
public class WeChatService {
	
	/** ����ʱ��־ */
	private static final Log log = LogFactory.getLog(WeChatService.class);
	
	/** ΢�Žӿڵ���ƾ֤��ÿ�ε���ǰ�����Զ��ж���Ч�ԡ� */
	private AccessToken accessToken = null;
	
	/**
	 * ��ȡaccess_token
	 * @return access_token
	 */
	public AccessToken getAccessToken() {
		if (null == accessToken || !isTokenValid(accessToken)) {
			// ���»�ȡƾ֤
			accessToken = requestAccessToken();
		}
		return accessToken;
	}
	
	/**
	 * �ж�access_token�Ƿ���Ч��δ���ڣ���
	 * @param accessToken ΢�Žӿڵ���ƾ֤
	 * @return true����Ч��false����Ч
	 */
	private boolean isTokenValid (AccessToken accessToken) {
		int expiresIn = accessToken.getExpiresIn();
		Long time = accessToken.getTime();
		Long now = System.currentTimeMillis();
		if (now - time > expiresIn * 1000 - 10000) {
			// Ϊ�˱����������ǰ10���Ӽ���Ϊƾ֤�����ˡ�
			return false;
		}
		return true;
	}
	
	/**
	 * ��΢�ŷ����������ȡaccess_token
	 * @return access_token
	 */
	private AccessToken requestAccessToken (){
		Long time = System.currentTimeMillis();
	    String requestUrl = WeChatUtils.GET_ACCESSTOKEN_URL.replace("APPID", WeChatUtils.APPID).replace("APPSECRET", WeChatUtils.APPSECRET);
	    JSONObject value = WeChatUtils.httpsRequest(requestUrl, "GET", null);
	    String access_token = value.getString("access_token");
	    int expires_in = -1;
	    expires_in = value.getInt("expires_in");
	    if (null == access_token || -1 == expires_in){
	        int errcode = value.getInt("errcode");
	        String errmsg = value.getString("errmsg");
	        log.error("The error code is " + errcode + ". The error message is " + errmsg);
	        return null;
	    }
	    AccessToken accessToken = new AccessToken(access_token, expires_in, time);
	    return accessToken;
	}

	@SuppressWarnings("unchecked")
	public void getFans() {
		Set<String> set = new HashSet<String>();
		
		AccessToken accessToken = getAccessToken();
		if (null != accessToken) {
			String next_openid = "";
			int getCount = 0;
			String requestUrl = WeChatUtils.GET_USERLIST_URL2.replace("ACCESS_TOKEN", accessToken.getAccessToken());
			while (null != next_openid) {
				JSONObject value = WeChatUtils.httpsRequest(requestUrl, "GET", null);
				try {
					int total = value.getInt("total");
					int count = value.getInt("count");
					getCount += count;
					next_openid = value.getString("next_openid");
					JSONObject data = value.getJSONObject("data");
					List<String> list = (List<String>) JSONArray.toCollection(data.getJSONArray("openid"));
					set.addAll(list);
					if (getCount >= total) {
						break;
					}
					requestUrl = WeChatUtils.GET_USERLIST_URL.replace("ACCESS_TOKEN", accessToken.getAccessToken()).replace("NEXT_OPENID", next_openid);
				} catch (JSONException e) {
					int errcode = value.getInt("errcode");
			        String errmsg = value.getString("errmsg");
			        log.error("The error code is " + errcode + ". The error message is " + errmsg);
			        return;
				}
			}
			
		}
		
		String ids = "��˿�б�Ϊ��";
		for (String openid : set) {
			ids = ids + openid + ",";
		}
		log.info(ids);
	}

}
