package com.meng.wechat.test;

import com.meng.wechat.entity.AccessToken;
import com.meng.wechat.service.WeChatService;
import com.meng.wechat.util.WeChatUtils;

/**
 * ≤‚ ‘¿‡
 * @author meng
 */
public class Test {

    public static void main(String[] args) {
    	WeChatService service = new WeChatService();
        AccessToken token = service.requestAccessToken(WeChatUtils.APPID, WeChatUtils.APPSECRET);
        if (null != token){
            System.out.println(token.getAccessToken());
            System.out.println(token.getExpiresIn());
        }
    }
    
}
