package com.meng.wechat.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.meng.wechat.entity.AccessToken;
import com.meng.wechat.entity.TextMessage;
import com.meng.wechat.util.WeChatUtils;

/**
 * ����΢�Ź���ƽ̨��Ϣ�ķ�����
 * @author meng
 */
public class WeChatService {
	
	/**
	 * ��΢�ŷ����������ȡaccess_token
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

	/**
	 * ����΢�ŷ���������
	 * 
	 * @param request
	 * @return xml
	 */
	public static String processRequest(HttpServletRequest request) {
		// xml��ʽ����Ϣ����
		String respXml = null;
		// Ĭ�Ϸ��ص��ı���Ϣ����
		String respContent = "���������ı������ǿ�ʼ����ɣ�";
		try {
			// ����parseXml��������������Ϣ
			Map<String, String> requestMap = WeChatUtils.parseXml(request);
			// ���ͷ��ʺ�
			String fromUserName = requestMap.get("FromUserName");
			// ������΢�ź�
			String toUserName = requestMap.get("ToUserName");
			// ��Ϣ����
			String msgType = requestMap.get("MsgType");
			// ��Ϣ����ʱ��
//			String createTime = requestMap.get("CreateTime");
			
			// �ı���Ϣ
			if (msgType.equals(WeChatUtils.MESSAGE_TYPE_TEXT)) {
				String content = requestMap.get(WeChatUtils.ATTR_CONTENT);
				// ���д���õ��ظ�
//				respContent = ChatService.chat(fromUserName, createTime, content);
				respContent = "��˵������Ϊ��" + content;
			}
			// �ظ��ı���Ϣ
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(System.currentTimeMillis());
			textMessage.setContent(respContent);
			// ���ı���Ϣ����ת����xml
			respXml = WeChatUtils.messageToXml(textMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respXml;
	}

}
