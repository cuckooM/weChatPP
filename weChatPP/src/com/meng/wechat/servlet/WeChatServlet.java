package com.meng.wechat.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.meng.wechat.util.WeChatUtils;

/**
 * ΢�Ź���ƽ̨��������������ĺ�����
 * @author meng
 */
public class WeChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * ����΢�ŷ�����������У������
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // ΢�ż���ǩ��
        String signature = request.getParameter("signature");
        // ʱ���
        String timestamp = request.getParameter("timestamp");
        // �����
        String nonce = request.getParameter("nonce");
        // ����ַ���
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();
        // ����У�飬��У��ɹ��򷵻�echostr����ʾ����ɹ����������ʧ��
        if (WeChatUtils.checkSignature(signature, timestamp, nonce, "")) {
            out.print(echostr);
        }
        out.close();
        out = null;
	}

	/**
	 * ����΢�ŷ�����������
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // ��������Ӧ�ı��������ΪUTF-8����ֹ�������룩
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // ΢�ż���ǩ��
        String signature = request.getParameter("signature");
        // ʱ���
        String timestamp = request.getParameter("timestamp");
        // �����
        String nonce = request.getParameter("nonce");

        PrintWriter out = response.getWriter();
        // ����У��
        if (WeChatUtils.checkSignature(signature, timestamp, nonce, "")) {
            // ���ú��ķ�������մ�������
//            String respXml = CoreService.processRequest(request);
//            out.print(respXml);
        }
        out.close();
        out = null;
	}

}
