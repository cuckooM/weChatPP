package com.meng.wechat.entity;

/**
 * ͼ����Ϣ����ʵ��
 * @author meng
 */
public class PictureText {
	
	/** ͼ����Ϣ�ı��� */
	String title;
	
	/** ͼ����Ϣ������ */
	String description;
	
	/** ͼ����Ϣ���������ת������ */
	String url;
	
	/** ͼ����Ϣ��ͼƬ���ӣ�֧��JPG��PNG��ʽ���Ϻõ�Ч��Ϊ��ͼ640*320��Сͼ80*80 */
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
