package com.meng.wechat.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * ΢�Ź�����
 */

public class WeChatUtils {
	
	/** ����ʱ��־ */
    private static final Log log = LogFactory.getLog(WeChatUtils.class);
    
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
     * ����https����ͨ�÷���
     * 
     * @param requestUrl �����ַ
     * @param requestMethod ����ʽ��GET��POST��
     * @param outputStr �ύ������
     * @return JSONObject ���������Ӧֵ
     */
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        try {
            // ����SSLContext����
            TrustManager[] tm = { new X509TrustAnyManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // ��SSLContext�����еõ�SSLSocketFactory
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // ����ʽ��GET/POST��
            conn.setRequestMethod(requestMethod);

            // �������д����
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // ���ñ����ʽ
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // ����������ȡ��������
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // �ͷ���Դ
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("�����쳣��{}", ce);
        } catch (Exception e) {
            log.error("https�����쳣��{}", e);
        }
        return jsonObject;
    }
    
    /**
	 * ����΢�ŷ���������XML��
	 * 
	 * @param request
	 * @return Map<String, String>
	 * @throws Exception
	 */
	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
		// ����������洢��HashMap��
		Map<String, String> map = new HashMap<String, String>();

		// ��request��ȡ��������
		InputStream inputStream = request.getInputStream();
		// ��ȡ������
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// �õ�xml��Ԫ��
		Element root = document.getRootElement();
		// �õ���Ԫ�ص������ӽڵ�
		List<Element> elementList = root.elements();

		// ���������ӽڵ�
		for (Element e : elementList)
			map.put(e.getName(), e.getText());

		// �ͷ���Դ
		inputStream.close();
		inputStream = null;

		return map;
	}
	
}
