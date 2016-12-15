package com.meng.wechat.entity;

import java.util.List;

/**
 * ΢���û�����˿��ʵ��
 * @author shterm
 */
public class WeChatUser {
	
	/** �û��Ƿ��ĸù��ںű�ʶ��ֵΪ0ʱ��������û�û�й�ע�ù��ںţ���ȡ����������Ϣ�� */
	private int subscribe;
	
	/** �û��ı�ʶ���Ե�ǰ���ں�Ψһ */
	private String openid;
	
	/** �û����ǳ� */
	private String nickname;
	
	/** �û����Ա�ֵΪ1ʱ�����ԣ�ֵΪ2ʱ��Ů�ԣ�ֵΪ0ʱ��δ֪ */
	private int sex;
	
	/** �û����ڳ��� */
	private String city;
	
	/** �û����ڹ��� */
	private String country;
	
	/** �û�����ʡ�� */
	private String province;
	
	/** �û������ԣ���������Ϊzh_CN */
	private String language;
	
	/** �û�ͷ�����һ����ֵ����������ͷ���С����0��46��64��96��132��ֵ��ѡ��0����640*640������ͷ�񣩣��û�û��ͷ��ʱ����Ϊ�ա����û�����ͷ��ԭ��ͷ��URL��ʧЧ�� */
	private String headimgurl;
	
	/** �û���עʱ�䣬Ϊʱ���������û�����ι�ע����ȡ����עʱ�� */
	private String subscribe_time;
	
	/** ֻ�����û������ںŰ󶨵�΢�ſ���ƽ̨�ʺź󣬲Ż���ָ��ֶΡ� */
	private String unionid;
	
	/** ���ں���Ӫ�߶Է�˿�ı�ע�����ں���Ӫ�߿���΢�Ź���ƽ̨�û��������Է�˿��ӱ�ע */
	private String remark;
	
	/** �û����ڵķ���ID�����ݾɵ��û�����ӿڣ� */
	private String groupid;
	
	/** �û������ϵı�ǩID�б� */
	private List<Long> tagid_list;

	public int getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(int subscribe) {
		this.subscribe = subscribe;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getSubscribe_time() {
		return subscribe_time;
	}

	public void setSubscribe_time(String subscribe_time) {
		this.subscribe_time = subscribe_time;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public List<Long> getTagid_list() {
		return tagid_list;
	}

	public void setTagid_list(List<Long> tagid_list) {
		this.tagid_list = tagid_list;
	}

}
