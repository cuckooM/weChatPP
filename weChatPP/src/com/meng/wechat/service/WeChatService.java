package com.meng.wechat.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.meng.wechat.entity.AccessToken;
import com.meng.wechat.entity.TextMessage;
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

	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return xml
	 */
	public static String processRequest(HttpServletRequest request) {
		// xml格式的消息数据
		String respXml = null;
		// 默认返回的文本消息内容
		String respContent = "发送任意文本，我们开始聊天吧！";
		try {
			// 调用parseXml方法解析请求消息
			Map<String, String> requestMap = WeChatUtils.parseXml(request);
			// 发送方帐号
			String fromUserName = requestMap.get("FromUserName");
			// 开发者微信号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			// 消息创建时间
//			String createTime = requestMap.get("CreateTime");
			
			// 文本消息
			if (msgType.equals(WeChatUtils.MESSAGE_TYPE_TEXT)) {
				String content = requestMap.get(WeChatUtils.ATTR_CONTENT);
				// 进行处理得到回复
//				respContent = ChatService.chat(fromUserName, createTime, content);
				respContent = "你说的内容为：" + content;
			}
			// 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(System.currentTimeMillis());
			textMessage.setContent(respContent);
			// 将文本消息对象转换成xml
			respXml = WeChatUtils.messageToXml(textMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respXml;
	}

}
