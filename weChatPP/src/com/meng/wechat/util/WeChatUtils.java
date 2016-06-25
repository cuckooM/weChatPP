package com.meng.wechat.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import com.meng.wechat.entity.AccessToken;

import net.sf.json.JSONObject;

/**
 * ΢�Ź�����
 */

public class WeChatUtils {
    
    /** ��ȡaccess_token�ӿڵ��õ�ַ��������APPID��APPSECRET��  */
    public static final String GET_ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    
    public static final String APPID = "wx467edf2c564dd211";
    
    public static final String APPSECRET = "9f8de2b06acd72c3c5f782d35e243364";
    
	/**
	 * У��ǩ��
	 * 
	 * @param signature ΢�ż���ǩ��
	 * @param timestamp ʱ���
	 * @param nonce �����
	 * @param token ΢�Ź�����д��token
	 * @return
	 */
	public static boolean checkSignature(String signature, String timestamp, String nonce, String token) {
		// ��token��timestamp��nonce���ֵ�����
		String[] paramArr = new String[] { token, timestamp, nonce };
		Arrays.sort(paramArr);

		// �������Ľ��ƴ�ӳ�һ���ַ���
		String content = paramArr[0].concat(paramArr[1]).concat(paramArr[2]);

		String ciphertext = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			// �ԽӺ���ַ�������sha1����
			byte[] digest = md.digest(content.toString().getBytes());
			ciphertext = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		// ��sha1���ܺ���ַ�����signature���жԱ�
		return null != ciphertext && ciphertext.equals(signature.toUpperCase());
	}

	/**
	 * ���ֽ�����ת��Ϊʮ�������ַ���
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * ���ֽ�ת��Ϊʮ�������ַ���
	 * 
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);
		return s;
	}
	
	/**
	 * ��΢�ŷ����������ȡaccess_token
	 * @return access_token
	 */
	public static AccessToken requestAccessToken (String appId, String appSecret){
	    String requestUrl = GET_ACCESSTOKEN_URL.replace("APPID", appId).replace("APPSECRET", appSecret);
	    JSONObject value = CommonUtils.httpsRequest(requestUrl, "GET", null);
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
	
	
}
