package com.meng.wechat.test;

import com.meng.wechat.entity.AccessToken;
import com.meng.wechat.service.WeChatService;

/**
 * ������
 * @author meng
 */
public class Test {

    public static void main(String[] args) {
    	WeChatService service = new WeChatService();
//        AccessToken token = service.getAccessToken();
//        if (null != token){
//            System.out.println(token.getAccessToken());
//            System.out.println(token.getExpiresIn());
//        }
    	service.getFans();
//    	service.sendCustomerMessage("oBTnus21nkpP1rSC5Sz52BEtlEMY", "����鿴<a href=\"http://blog.csdn.net/lyq8479\">����Ĳ���</a>");
    }
    
}
