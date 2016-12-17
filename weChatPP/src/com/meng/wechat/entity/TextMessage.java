package com.meng.wechat.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.meng.wechat.util.WeChatUtils;

/**
 * 被动回复文本消息实体
 * @author meng
 */
@XmlRootElement(name="xml")
public class TextMessage {
	
	/** 接收方帐号（收到的OpenID） */
	@XmlElement(name="ToUserName")
	private String ToUserName;

	/** 开发者微信号 */
	@XmlElement(name="FromUserName")
	private String FromUserName;
	
	/** 消息创建时间 （整型） */
	@XmlElement(name="CreateTime")
	private long CreateTime;
	
	/** 类型：text */
	@XmlElement(name="MsgType")
	private String MsgType = WeChatUtils.MESSAGE_TYPE_TEXT;
	
	/** 回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示） */
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
