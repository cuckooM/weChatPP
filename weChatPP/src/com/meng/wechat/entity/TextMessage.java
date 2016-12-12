package com.meng.wechat.entity;

import com.meng.wechat.util.WeChatUtils;

/**
 * 被动回复文本消息实体
 * @author meng
 */
public class TextMessage {
	
	/** 接收方帐号（收到的OpenID） */
	private String ToUserName;

	/** 开发者微信号 */
	private String FromUserName;
	
	/** 消息创建时间 （整型） */
	private long CreateTime;
	
	/** 类型：text */
	private String MsgType = WeChatUtils.MESSAGE_TYPE_TEXT;
	
	/** 回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示） */
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
