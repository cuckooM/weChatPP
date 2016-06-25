package com.meng.wechat.test;

import com.meng.wechat.entity.AccessToken;
import com.meng.wechat.util.WeChatUtils;

/**
 * ≤‚ ‘¿‡
 * @author meng
 */
public class Test {

    public static void main(String[] args) {
        AccessToken token = WeChatUtils.requestAccessToken(WeChatUtils.APPID, WeChatUtils.APPSECRET);
        if (null != token){
            System.out.println(token.getAccessToken());
            System.out.println(token.getExpiresIn());
        }
    }
    
}
