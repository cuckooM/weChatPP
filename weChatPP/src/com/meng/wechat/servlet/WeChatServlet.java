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
 * ΢�Ź���ƽ̨��������������ĺ�����
 * @author meng
 */
public class WeChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	WeChatService service = new WeChatService();
       
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
        if (WeChatUtils.checkSignature(signature, timestamp, nonce, "mengbinTest")) {
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
        if (WeChatUtils.checkSignature(signature, timestamp, nonce, "mengbinTest")) {
            // ���ú��ķ�������մ�������
//            String respXml = WeChatService.processRequest(request);
//            System.out.println(respXml);
//            out.print(respXml);
        	out.print("");
        	try {
				// ����parseXml��������������Ϣ
				Map<String, String> requestMap = WeChatUtils.parseXml(request);
				// ���ͷ��ʺ�
				String fromUserName = requestMap.get("FromUserName");
				// ������΢�ź�
				String toUserName = requestMap.get("ToUserName");
				System.out.println("������openId�ǣ�" + fromUserName + "��������openId�ǣ�" + toUserName);
				// ��Ϣ����
				String msgType = requestMap.get("MsgType");
				// ��Ϣ����ʱ��
				String createTime = requestMap.get("CreateTime");
				service.sendCustomerMessage(fromUserName, "���ѽ");
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        out.close();
        out = null;
	}

}
