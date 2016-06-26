package com.meng.wechat.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.meng.wechat.util.WeChatUtils;

/**
 * 微信公众平台服务器处理请求的核心类
 * @author meng
 */
public class WeChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
        if (WeChatUtils.checkSignature(signature, timestamp, nonce, "")) {
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
        if (WeChatUtils.checkSignature(signature, timestamp, nonce, "")) {
            // 调用核心服务类接收处理请求
//            String respXml = CoreService.processRequest(request);
//            out.print(respXml);
        }
        out.close();
        out = null;
	}

}
