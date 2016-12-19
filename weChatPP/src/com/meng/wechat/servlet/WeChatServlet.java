package com.meng.wechat.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.meng.wechat.service.WeChatService;
import com.meng.wechat.util.WeChatUtils;

/**
 * 微信公众平台服务器处理请求的核心类
 * @author meng
 */
public class WeChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	WeChatService service = new WeChatService();
       
	/**
	 * 处理微信服务器发来的校验请求
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();
        // 进行校验，若校验成功则返回echostr，表示接入成功；否则接入失败
        if (WeChatUtils.checkSignature(signature, timestamp, nonce, "mengbinTest")) {
            out.print(echostr);
        }
        out.close();
        out = null;
	}

	/**
	 * 处理微信服务器的请求
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");

        PrintWriter out = response.getWriter();
        // 请求校验
        if (WeChatUtils.checkSignature(signature, timestamp, nonce, "mengbinTest")) {
            // 调用核心服务类接收处理请求
//            String respXml = WeChatService.processRequest(request);
//            System.out.println(respXml);
//            out.print(respXml);
        	out.print("");
        	try {
				// 调用parseXml方法解析请求消息
				Map<String, String> requestMap = WeChatUtils.parseXml(request);
				// 发送方帐号
				String fromUserName = requestMap.get("FromUserName");
				// 开发者微信号
				String toUserName = requestMap.get("ToUserName");
				System.out.println("发送者openId是：" + fromUserName + "，接收者openId是：" + toUserName);
				// 消息类型
				String msgType = requestMap.get("MsgType");
				// 消息创建时间
				String createTime = requestMap.get("CreateTime");
				service.sendCustomerMessage(fromUserName, "你好呀");
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        out.close();
        out = null;
	}

}
