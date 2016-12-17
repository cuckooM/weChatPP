package com.meng.wechat.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.meng.wechat.util.WeChatUtils;

/**
 * �����ظ��ı���Ϣʵ��
 * @author meng
 */
@XmlRootElement(name="xml")
public class TextMessage {
	
	/** ���շ��ʺţ��յ���OpenID�� */
	@XmlElement(name="ToUserName")
	private String ToUserName;

	/** ������΢�ź� */
	@XmlElement(name="FromUserName")
	private String FromUserName;
	
	/** ��Ϣ����ʱ�� �����ͣ� */
	@XmlElement(name="CreateTime")
	private long CreateTime;
	
	/** ���ͣ�text */
	@XmlElement(name="MsgType")
	private String MsgType = WeChatUtils.MESSAGE_TYPE_TEXT;
	
	/** �ظ�����Ϣ���ݣ����У���content���ܹ����У�΢�ſͻ��˾�֧�ֻ�����ʾ�� */
	@XmlElement(name="Content")
	private String Content;

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = WeChatUtils.DATA_FORMAT_PREFIX + toUserName + WeChatUtils.DATA_FORMAT_SUFFIX;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = WeChatUtils.DATA_FORMAT_PREFIX + fromUserName + WeChatUtils.DATA_FORMAT_SUFFIX;
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
		MsgType = WeChatUtils.DATA_FORMAT_PREFIX + msgType + WeChatUtils.DATA_FORMAT_SUFFIX;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = WeChatUtils.DATA_FORMAT_PREFIX + content + WeChatUtils.DATA_FORMAT_SUFFIX;
	}
}
