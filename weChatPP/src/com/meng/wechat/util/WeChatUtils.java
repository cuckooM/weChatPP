package com.meng.wechat.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import com.meng.wechat.entity.AccessToken;

import net.sf.json.JSONObject;

/**
 * 微信工具类
 */

public class WeChatUtils {
    
    /** 获取access_token接口调用地址。参数：APPID、APPSECRET。  */
    public static final String GET_ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    
    public static final String APPID = "wx467edf2c564dd211";
    
    public static final String APPSECRET = "9f8de2b06acd72c3c5f782d35e243364";
    
	/**
	 * 校验签名
	 * 
	 * @param signature 微信加密签名
	 * @param timestamp 时间戳
	 * @param nonce 随机数
	 * @param token 微信官网填写的token
	 * @return
	 */
	public static boolean checkSignature(String signature, String timestamp, String nonce, String token) {
		// 对token、timestamp和nonce按字典排序
		String[] paramArr = new String[] { token, timestamp, nonce };
		Arrays.sort(paramArr);

		// 将排序后的结果拼接成一个字符串
		String content = paramArr[0].concat(paramArr[1]).concat(paramArr[2]);

		String ciphertext = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			// 对接后的字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			ciphertext = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		// 将sha1加密后的字符串与signature进行对比
		return null != ciphertext && ciphertext.equals(signature.toUpperCase());
	}

	/**
	 * 将字节数组转换为十六进制字符串
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
	 * 将字节转换为十六进制字符串
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
	 * 向微信服务器请求获取access_token
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
