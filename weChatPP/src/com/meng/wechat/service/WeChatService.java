package com.meng.wechat.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.meng.wechat.entity.AccessToken;
import com.meng.wechat.entity.WeChatUser;
import com.meng.wechat.util.WeChatUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

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

	/**
	 * ��ȡȫ����˿��ϸ��Ϣ
	 */
	@SuppressWarnings("unchecked")
	public void getFans() {
		List<String> list = new ArrayList<String>();
		
		// ���ȣ���ȡ��˿openid�б�
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
					list.addAll((List<String>) JSONArray.toCollection(data.getJSONArray("openid")));
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
		
		// ��Σ���ȡ��˿��ϸ��Ϣ
		int size = list.size();
		if (size > 0) {
			// ������ȡ��˿��ϸ��Ϣ
			JSONObject data = new JSONObject();
			JSONArray userList = new JSONArray();
			for (int i = 0; i < size; i++) {
				JSONObject user = new JSONObject();
				user.put("openid", list.get(i));
				user.put("lang", "zh-CN");
				userList.add(user);
				if ((i + 1) % 100 == 0) {
					// 100 �ı���������һ�������ȡ�û���ϸ��Ϣ
					data.put("user_list", userList);
					requestUserInfoBatch(data.toString());
					user = new JSONObject();
					userList = new JSONArray();
				}
			}
			if (userList.size() > 0) {
				data.put("user_list", userList);
				requestUserInfoBatch(data.toString());
			}
			
		}
		
	}
	
	/**
	 * ��΢�ŷ���������������ȡ�û���ϸ��Ϣ
	 * @param body ������
	 * @return �û��б�
	 */
	@SuppressWarnings("unchecked")
	private List<WeChatUser> requestUserInfoBatch(String body) {
		accessToken = getAccessToken();
		if (null == accessToken) {
			return null;
		}
		String requestUrl = WeChatUtils.POST_USERINFO_BATCH_URL.replace("ACCESS_TOKEN", accessToken.getAccessToken());
		JSONObject value = WeChatUtils.httpsRequest(requestUrl, "POST", body);
		if (null == value) {
			return null;
		}
		JSONArray userInfoList;
		try {
			userInfoList = value.getJSONArray("user_info_list");
		} catch (JSONException e) {
			int errcode = value.getInt("errcode");
	        String errmsg = value.getString("errmsg");
	        log.error("The error code is " + errcode + ". The error message is " + errmsg);
	        return null;
		}
		
		List<WeChatUser> weChatUserList = JSONArray.toList(userInfoList, new WeChatUser(), new JsonConfig());
		for (WeChatUser weChatUser : weChatUserList) {
			int subscribe = weChatUser.getSubscribe();
			String openid = weChatUser.getOpenid();
			if (subscribe == 0) {
				log.info("�û�" + openid + "��ȡ����ע��");
			} else {
				log.info("�û�" + openid + "���ڹ�ע��" + weChatUser.toString());
			}
		}
		return weChatUserList;
	}

}
