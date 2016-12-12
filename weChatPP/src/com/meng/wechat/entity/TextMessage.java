package com.meng.wechat.entity;

import com.meng.wechat.util.WeChatUtils;

/**
 * �����ظ��ı���Ϣʵ��
 * @author meng
 */
public class TextMessage {
	
	/** ���շ��ʺţ��յ���OpenID�� */
	private String ToUserName;

	/** ������΢�ź� */
	private String FromUserName;
	
	/** ��Ϣ����ʱ�� �����ͣ� */
	private long CreateTime;
	
	/** ���ͣ�text */
	private String MsgType = WeChatUtils.MESSAGE_TYPE_TEXT;
	
	/** �ظ�����Ϣ���ݣ����У���content���ܹ����У�΢�ſͻ��˾�֧�ֻ�����ʾ�� */
	private String Content;

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}
