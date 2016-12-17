package com.meng.wechat.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.meng.wechat.entity.TextMessage;
import com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler;

import net.sf.json.JSONObject;

/**
 * ΢�Ź�����
 */

public class WeChatUtils {
	
	/** ����ʱ��־ */
    private static final Log log = LogFactory.getLog(WeChatUtils.class);
    
    /** ��ȡaccess_token�ӿڵ��õ�ַ��������APPID��APPSECRET��  */
    public static final String GET_ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    
    public static final String APPID = "wx53ac70c6aabcbb7c";
    
    public static final String APPSECRET = "d4624c36b6795d1d99dcf0547af5443d";
    
    /** ��ȡ�û��б�������ACCESS_TOKEN�� NEXT_OPENID*/
    public static final String GET_USERLIST_URL = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
    
    /** ��ȡ�û��б�������ACCESS_TOKEN�� NEXT_OPENID*/
    public static final String GET_USERLIST_URL2 = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN";
    
    /** ��ȡ�����û���ϸ��Ϣ������ACCESS_TOKEN��OPENID */
    public static final String GET_USERINFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN ";
    
    /** ������ȡ�û�������Ϣ�����֧��һ����ȡ100��������ACCESS_TOKEN */
    public static final String POST_USERINFO_BATCH_URL = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN";
    
    /** �����ظ����ݵ�xml��ʽ�е�����ǰ׺ */
    public static final String DATA_FORMAT_PREFIX = "<![CDATA[";
    
    /** �����ظ����ݵ�xml��ʽ�е����ݺ�׺ */
    public static final String DATA_FORMAT_SUFFIX = "]]>";
    
    /** ��Ϣ���ͣ��ı� */
    public static final String MESSAGE_TYPE_TEXT = "text";
    
    /** ��Ϣ���ͣ�ͼƬ */
    public static final String MESSAGE_TYPE_IMAGE = "image";
    
    /** ��Ϣ���ͣ����� */
    public static final String MESSAGE_TYPE_VOICE = "voice";
    
    /** ��Ϣ���ͣ���Ƶ */
    public static final String MESSAGE_TYPE_VIDEO = "video";
    
    /** ��Ϣ���ͣ�С��Ƶ */
    public static final String MESSAGE_TYPE_SHORTVIDEO = "shortvideo";
    
    /** ��Ϣ���ͣ�����λ��  */
    public static final String MESSAGE_TYPE_LOCATION = "location";
    
    /** ��Ϣ���ͣ����� */
    public static final String MESSAGE_TYPE_LINK = "link";
    
    /** �������ƣ�ToUserName */
    public static final String ATTR_TO_USER_NAME = "ToUserName";
    
    /** �������ƣ�FromUserName */
    public static final String ATTR_FROM_USER_NAME = "FromUserName";
    
    /** �������ƣ�CreateTime */
    public static final String ATTR_CREATE_TIME = "CreateTime";
    
    /** �������ƣ�MsgType */
    public static final String ATTR_MSG_TYPE = "MsgType";
    
    /** �������ƣ�Content */
    public static final String ATTR_CONTENT = "Content";
    
    /** �������ƣ�MsgId */
    public static final String ATTR_MSG_ID = "MsgId";
    
    /** �������ƣ�PicUrl */
    public static final String ATTR_PIC_URL = "PicUrl";
    
    /** �������ƣ�MediaId */
    public static final String ATTR_MEDIA_ID = "MediaId";
    
    /** �������ƣ�Format */
    public static final String ATTR_FORMAT = "Format";
    
    /** �������ƣ�Recognition */
    public static final String ATTR_RECOGNITION = "Recognition";
    
    /** �������ƣ�ThumbMediaId */
    public static final String ATTR_THUMB_MEDIA_ID = "ThumbMediaId";
    
    /** �������ƣ�Location_X */
    public static final String ATTR_LOCATION_X = "Location_X";
    
    /** �������ƣ�Location_Y */
    public static final String ATTR_LOCATION_Y = "Location_Y";
    
    /** �������ƣ�Scale */
    public static final String ATTR_SCALE = "Scale";
    
    /** �������ƣ�Label */
    public static final String ATTR_LABEL = "Label";
    
    /** �������ƣ�Title */
    public static final String ATTR_TITLE = "Title";
    
    /** �������ƣ�Description */
    public static final String ATTR_DESCRIPTION = "Description";
    
    /** �������ƣ�Url */
    public static final String ATTR_URL = "Url";
    
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
        	
        	if ("POST".equalsIgnoreCase(requestMethod)) {
        		requestMethod = "POST";
        	} else {
        		requestMethod = "GET";
        	}
        	
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
            log.error("�����쳣��", ce);
        } catch (Exception e) {
            log.error("https�����쳣��", e);
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
	@SuppressWarnings("unchecked")
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
	
	/**
	 * �����ظ��ı���Ϣʵ��ת��Ϊxml
	 * @param textMessage
	 * @return
	 */
	public static String messageToXml(TextMessage textMessage) {
		try {
			JAXBContext context = JAXBContext.newInstance(TextMessage.class);
			Marshaller marshaller = context.createMarshaller();
			// xml��ʽ
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			// ȥ������xml��Ĭ�ϱ���ͷ
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			// ������ת���ַ��Ĵ���
			marshaller.setProperty(CharacterEscapeHandler.class.getName(), new CharacterEscapeHandler() {
				public void escape(char[] ch, int start,int length, boolean isAttVal, Writer writer) throws IOException {
					writer.write(ch, start, length);
				}
			});
			StringWriter sw = new StringWriter();
			marshaller.marshal(textMessage, sw);
			return sw.toString();
		} catch (JAXBException e) {
			log.error("", e);
		}
		return null;
	}
	
}
