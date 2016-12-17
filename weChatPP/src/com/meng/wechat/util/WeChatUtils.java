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
 * 微信工具类
 */

public class WeChatUtils {
	
	/** 运行时日志 */
    private static final Log log = LogFactory.getLog(WeChatUtils.class);
    
    /** 获取access_token接口调用地址。参数：APPID、APPSECRET。  */
    public static final String GET_ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    
    public static final String APPID = "wx53ac70c6aabcbb7c";
    
    public static final String APPSECRET = "d4624c36b6795d1d99dcf0547af5443d";
    
    /** 获取用户列表。参数：ACCESS_TOKEN、 NEXT_OPENID*/
    public static final String GET_USERLIST_URL = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
    
    /** 获取用户列表。参数：ACCESS_TOKEN、 NEXT_OPENID*/
    public static final String GET_USERLIST_URL2 = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN";
    
    /** 获取单个用户详细信息。参数ACCESS_TOKEN、OPENID */
    public static final String GET_USERINFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN ";
    
    /** 批量获取用户基本信息。最多支持一次拉取100条。参数ACCESS_TOKEN */
    public static final String POST_USERINFO_BATCH_URL = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN";
    
    /** 被动回复数据的xml格式中的数据前缀 */
    public static final String DATA_FORMAT_PREFIX = "<![CDATA[";
    
    /** 被动回复数据的xml格式中的数据后缀 */
    public static final String DATA_FORMAT_SUFFIX = "]]>";
    
    /** 消息类型：文本 */
    public static final String MESSAGE_TYPE_TEXT = "text";
    
    /** 消息类型：图片 */
    public static final String MESSAGE_TYPE_IMAGE = "image";
    
    /** 消息类型：语音 */
    public static final String MESSAGE_TYPE_VOICE = "voice";
    
    /** 消息类型：视频 */
    public static final String MESSAGE_TYPE_VIDEO = "video";
    
    /** 消息类型：小视频 */
    public static final String MESSAGE_TYPE_SHORTVIDEO = "shortvideo";
    
    /** 消息类型：地理位置  */
    public static final String MESSAGE_TYPE_LOCATION = "location";
    
    /** 消息类型：链接 */
    public static final String MESSAGE_TYPE_LINK = "link";
    
    /** 属性名称：ToUserName */
    public static final String ATTR_TO_USER_NAME = "ToUserName";
    
    /** 属性名称：FromUserName */
    public static final String ATTR_FROM_USER_NAME = "FromUserName";
    
    /** 属性名称：CreateTime */
    public static final String ATTR_CREATE_TIME = "CreateTime";
    
    /** 属性名称：MsgType */
    public static final String ATTR_MSG_TYPE = "MsgType";
    
    /** 属性名称：Content */
    public static final String ATTR_CONTENT = "Content";
    
    /** 属性名称：MsgId */
    public static final String ATTR_MSG_ID = "MsgId";
    
    /** 属性名称：PicUrl */
    public static final String ATTR_PIC_URL = "PicUrl";
    
    /** 属性名称：MediaId */
    public static final String ATTR_MEDIA_ID = "MediaId";
    
    /** 属性名称：Format */
    public static final String ATTR_FORMAT = "Format";
    
    /** 属性名称：Recognition */
    public static final String ATTR_RECOGNITION = "Recognition";
    
    /** 属性名称：ThumbMediaId */
    public static final String ATTR_THUMB_MEDIA_ID = "ThumbMediaId";
    
    /** 属性名称：Location_X */
    public static final String ATTR_LOCATION_X = "Location_X";
    
    /** 属性名称：Location_Y */
    public static final String ATTR_LOCATION_Y = "Location_Y";
    
    /** 属性名称：Scale */
    public static final String ATTR_SCALE = "Scale";
    
    /** 属性名称：Label */
    public static final String ATTR_LABEL = "Label";
    
    /** 属性名称：Title */
    public static final String ATTR_TITLE = "Title";
    
    /** 属性名称：Description */
    public static final String ATTR_DESCRIPTION = "Description";
    
    /** 属性名称：Url */
    public static final String ATTR_URL = "Url";
    
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
     * 发送https请求通用方法
     * 
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject 解析后的响应值
     */
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        try {
        	
        	if ("POST".equalsIgnoreCase(requestMethod)) {
        		requestMethod = "POST";
        	} else {
        		requestMethod = "GET";
        	}
        	
            // 创建SSLContext对象
            TrustManager[] tm = { new X509TrustAnyManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从SSLContext对象中得到SSLSocketFactory
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);

            // 向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 设置编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("连接异常：", ce);
        } catch (Exception e) {
            log.error("https请求异常：", e);
        }
        return jsonObject;
    }
    
    /**
	 * 解析微信发来的请求（XML）
	 * 
	 * @param request
	 * @return Map<String, String>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();

		// 从request中取得输入流
		InputStream inputStream = request.getInputStream();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();

		// 遍历所有子节点
		for (Element e : elementList)
			map.put(e.getName(), e.getText());

		// 释放资源
		inputStream.close();
		inputStream = null;

		return map;
	}
	
	/**
	 * 被动回复文本消息实体转换为xml
	 * @param textMessage
	 * @return
	 */
	public static String messageToXml(TextMessage textMessage) {
		try {
			JAXBContext context = JAXBContext.newInstance(TextMessage.class);
			Marshaller marshaller = context.createMarshaller();
			// xml格式
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			// 去掉生成xml的默认报文头
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			// 不进行转义字符的处理
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
