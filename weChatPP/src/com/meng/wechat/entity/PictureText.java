package com.meng.wechat.entity;

/**
 * 图文消息内容实体
 * @author meng
 */
public class PictureText {
	
	/** 图文消息的标题 */
	String title;
	
	/** 图文消息的描述 */
	String description;
	
	/** 图文消息被点击后跳转的链接 */
	String url;
	
	/** 图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80 */
	String picurl;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}
	
}
