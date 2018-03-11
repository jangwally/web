package com.ruanyun.web.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyStore;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.swing.JOptionPane;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.ProtocolException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * 
 * #(c) IFlytek weixin <br/>
 * 
 * 版本说明: $id:$ <br/>
 * 
 * 功能说明: java https/http 请求公共类
 * 
 * <br/>
 * 创建说明: 2014-5-13 上午08:52:24 L H T 创建文件<br/>
 * 
 * 修改历史:<br/>
 * 
 */
public class HttpRequestUtil {
	private static Logger log = LoggerFactory.getLogger(HttpRequestUtil.class);

	/**
	 * 发起https请求并获取json结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject jsonHttpsRequest(String requestUrl,
			String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}
		return jsonObject;
	}

	/**
	 * 功能描述:https 请求返回字符串
	 * 
	 * @author L H T 2014-5-13 下午04:59:26
	 * 
	 * @param url
	 * @return
	 */
	public static String stringHttpsRequest(String requestUrl) {
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod("GET");
			httpUrlConn.connect();

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			// 读取返回结果

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			httpUrlConn.disconnect();
		} catch (ConnectException ce) {
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			log.error("https request get String error:{}", e);
		}
		return buffer.toString();
	}

	/**
	 * 发起 http get请求获取返回字符串
	 * 
	 * @param requestUrl
	 * @return
	 */
	public static String stringHttpRequest(String requestUrl) {
		StringBuffer buffer = null;

		try {
			// 建立连接
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url
					.openConnection();
			httpUrlConn.setDoInput(true);
			httpUrlConn.setRequestMethod("GET");

			// 获取输入流
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			// 读取返回结果
			buffer = new StringBuffer();
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			httpUrlConn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	/**
	 * 发送http请求取得返回的输入流
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @return InputStream
	 */
	public static InputStream inputStreamHttpRequest(String requestUrl) {
		InputStream inputStream = null;
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url
					.openConnection();
			httpUrlConn.setDoInput(true);
			httpUrlConn.setRequestMethod("GET");
			httpUrlConn.connect();
			// 获得返回的输入流
			inputStream = httpUrlConn.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inputStream;
	}

	public static String sendPostXml(String sendUrl, String xml) {
		try {
			byte[] bb = xml.getBytes("UTF-8");
			// 请求地址
			URL url = new URL(sendUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(5 * 1000);// 设置超时的时间
			conn.setDoInput(true);
			conn.setDoOutput(true);// 如果通过post提交数据，必须设置允许对外输出数据
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type",
					"application/xml; charset=UTF-8");
			conn
					.setRequestProperty("Content-Length", String
							.valueOf(bb.length));
			conn.connect();
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			out.write(bb); // 写入请求的字符串
			out.flush();
			out.close();
			// 请求返回的状态
			if (conn.getResponseCode() == 200) {
				// 请求返回的数据
				InputStream in = conn.getInputStream();
				String a = null;
				try {
					byte[] data1 = new byte[in.available()];
					in.read(data1);
					// 转成字符串
					a = new String(data1, "UTF-8");
					// System.out.println(a);
					return a;
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 发起https请求并获取json结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject jsonHttpsRequestNoSSL(String requestUrl,
			String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {

			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url
					.openConnection();

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			System.out.println(buffer.toString());
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}
		return jsonObject;
	}

	public static String receiveSessionID(String[] parameters, Object[] values) {
		String tempSessionId = "";// SessionID
		URL url = null;// 请求处理的Servlet
		ObjectOutputStream objOutputStrm = null;// 对象输出流
		InputStream inStrm = null;// 得到HttpURLConnection的输入流
		HttpURLConnection httpUrlConnection = null;
		try {
			url = new URL("http://api.68wifi.net/delay");
			// 设置HttpURLConnection参数
			httpUrlConnection = setURLConnectionProperties(url);
			// 得到对象输出流
			objOutputStrm = getObjOutStream(httpUrlConnection);
			JSONObject obj = new JSONObject();
			for (int i = 0; i < parameters.length; i++) {
				obj.put(parameters[i], values[i]);
			}
			// 向对象输出流写出数据，这些数据将存到内存缓冲区中
			objOutputStrm.writeObject(obj.toString());
			// 刷新对象输出流，将任何字节都写入潜在的流中（些处为ObjectOutputStream）
			objOutputStrm.flush();
			// 关闭流对象。此时，不能再向对象输出流写入任何数据，先前写入的数据存在于内存缓冲区中,
			// 在调用下边的getInputStream()函数时才把准备好的http请求正式发送到服务器
			// objOutputStrm.close();
			// 调用HttpURLConnection连接对象的getInputStream()函数,
			// 将内存缓冲区中封装好的完整的HTTP请求电文发送到服务端。
			inStrm = httpUrlConnection.getInputStream(); // <===注意，实际发送请求的代码段就在这里
			// 上边的httpConn.getInputStream()方法已调用,本次HTTP请求已结束,下边向对象输出流的输出已无意义，
			// 既使对象输出流没有调用close()方法，下边的操作也不会向对象输出流写入任何数据.
			// 因此，要重新发送数据时需要重新创建连接、重新设参数、重新创建流对象、重新写数据、
			// 重新发送数据(至于是否不用重新这些操作需要再研究)
			// objOutputStrm.writeObject(new String(""));
			// httpUrlConnection.getInputStream();
			// 从服务器读取对象
			Object inObj = readObjectFromServer(inStrm);
			// 处理从服务器读取的JSON对象
			tempSessionId = doJsonObjectFromServerForSesId(tempSessionId, inObj);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			try {
				if (objOutputStrm != null) {
					objOutputStrm.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (inStrm != null) {
					inStrm.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return tempSessionId;
	}

	/**
	 *从服务器读取对象<br>
	 *<功能详细描述><br>
	 * 
	 * @param inStrm
	 *            输入流
	 * @return 从服务器返回的对象
	 * @throws IOException
	 *@see [类、类#方法、类#成员]
	 */
	private static Object readObjectFromServer(InputStream inStrm)
			throws IOException {
		ObjectInputStream objInStream; // 输入流 从服务器读取JSON对象
		objInStream = new ObjectInputStream(inStrm);// 输入流 从服务器读取JSON对象
		Object inObj = null;
		try {
			inObj = objInStream.readObject();// 读取对象
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return inObj;
	}

	private static ObjectOutputStream getObjOutStream(
			HttpURLConnection httpUrlConnection) throws IOException {
		OutputStream outStrm;// 得到HttpURLConnection的输出流
		ObjectOutputStream objOutputStrm;// 对象输出流
		// 此处getOutputStream会隐含的进行connect(即：如同调用上面的connect()方法，
		// 所以在开发中不调用上述的connect()也可以)。
		outStrm = httpUrlConnection.getOutputStream();
		// 现在通过输出流对象构建对象输出流对象，以实现输出可序列化的对象。
		// 使用JSON传值
		objOutputStrm = new ObjectOutputStream(outStrm);
		return objOutputStrm;
	}

	private static HttpURLConnection setURLConnectionProperties(URL url)
			throws IOException, ProtocolException {
		HttpURLConnection httpUrlConnection;
		URLConnection rulConnection = url.openConnection();// 此处的urlConnection对象实际上是根据URL的
		// 请求协议(此处是http)生成的URLConnection类
		// 的子类HttpURLConnection,故此处最好将其转化
		// 为HttpURLConnection类型的对象,以便用到
		// HttpURLConnection更多的API.如下:
		httpUrlConnection = (HttpURLConnection) rulConnection;
		// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
		// http正文内，因此需要设为true, 默认情况下是false;
		httpUrlConnection.setDoOutput(true);
		// 设置是否从httpUrlConnection读入，默认情况下是true;
		httpUrlConnection.setDoInput(true);
		// Post 请求不能使用缓存
		httpUrlConnection.setUseCaches(false);
		// 设定传送的内容类型是可序列化的java对象
		// (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
		// httpUrlConnection.setRequestProperty("Content-type",
		// "application/x-java-serialized-object");
		//   
		httpUrlConnection.setRequestProperty("Content-type", "text/json");
		// 设定请求的方法为"POST"，默认是GET
		httpUrlConnection.setRequestMethod("GET");
		try {
			// 连接，从上述至此的配置必须要在connect之前完成，
			httpUrlConnection.connect();
			httpUrlConnection.setConnectTimeout(1);
			httpUrlConnection.setReadTimeout(1);
		} catch (ConnectException e1) {
			if (e1.getMessage().equals("Connection refused: connect")) {
				JOptionPane.showMessageDialog(null, "连接超时");
				System.exit(0);
			}
		}
		return httpUrlConnection;
	}

	private static String doJsonObjectFromServerForSesId(String tempSessionID,
			Object inObj) throws JSONException {
		// 做非空处理
		if (inObj != null) {
			// 根据得到的序列化对象 构建JSON对象
			JSONObject object = JSONObject.fromObject(inObj);
			tempSessionID = object.toString();
			// 拿到JSON对象中 对应key的值
		}
		return tempSessionID;
	}

	public static void main(String[] args) {
		/*
		 * String requestUrl="http://api.68wifi.net/delay"; String
		 * str="{\"ssid\": \"家\",\"ip\": \"192.168.10.190\",\"ss\": \"10000\"}";
		 * jsonHttpsRequestNoSSL(requestUrl, "POST", str);
		 */
		System.out.println(receiveSessionID(
				new String[] { "ssid", "ip", "ss" }, new Object[] { "家",
						"192.168.10.190", 10000 }));
	}

	public static String sendCertPostXml(String url, String mac_id, String data) {
		try {
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			FileInputStream instream = new FileInputStream(new File("/mnt/cert/yttc.p12"));
			//FileInputStream instream = new FileInputStream(new File("D:/yttc.p12"));
			try {
				keyStore.load(instream, mac_id.toCharArray());
			} finally {
				instream.close();
			}

			// Trust own CA and all self-signed certs
			SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(
					keyStore, mac_id.toCharArray()).build();
			// Allow TLSv1 protocol only
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					sslcontext,
					new String[] { "TLSv1" },
					null,
					SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			CloseableHttpClient httpclient = HttpClients.custom()
					.setSSLSocketFactory(sslsf).build();

			HttpPost httpost = new HttpPost(url); // 设置响应头信息
			httpost.addHeader("Connection", "keep-alive");
			httpost.addHeader("Accept", "*/*");
			httpost.addHeader("Content-Type",
					"application/x-www-form-urlencoded; charset=UTF-8");
			httpost.addHeader("Host", "api.mch.weixin.qq.com");
			httpost.addHeader("X-Requested-With", "XMLHttpRequest");
			httpost.addHeader("Cache-Control", "max-age=0");
			httpost.addHeader("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
			httpost.setEntity(new StringEntity(data, "UTF-8"));
			CloseableHttpResponse response = httpclient.execute(httpost);
			HttpEntity entity = response.getEntity();

			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine());
			if (entity != null) {
				String jsonStr = EntityUtils.toString(entity, "UTF-8");
				return jsonStr;
				// System.out.println("Response content length: " +
				// entity.getContentLength());
				// BufferedReader bufferedReader = new BufferedReader(new
				// InputStreamReader(entity.getContent()));
				// String text;
				// while ((text = bufferedReader.readLine()) != null) {
				// System.out.println(text);
				// }

			}
			EntityUtils.consume(entity);

			response.close();
			httpclient.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String httpPost(String url, NameValuePair[] data)
			throws Exception {
		// TODO Auto-generated method stub
		HttpClient httpClient = new HttpClient();

		PostMethod postMethod = new PostMethod(url);

		// 设置编码,httppost同时会用编码进行url.encode
		httpClient.getParams().setContentCharset("UTF-8");
		postMethod.addRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
		// 将表单的值放入postMethod中
		postMethod.setRequestBody(data);
		// 执行postMethod
		int statusCode = 0;
		try {
			statusCode = httpClient.executeMethod(postMethod);
		} catch (HttpException e) {
			// logger.error(e.getMessage());
		} catch (IOException e) {
			// logger.error(e.getMessage());
		}
		// HttpClient对于要求接受后继服务的请求，象POST和PUT等不能自动处理转发
		// 301或者302
		if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
				|| statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
			// 从头中取出转向的地址
			Header locationHeader = postMethod.getResponseHeader("location");
			String location = null;
			if (locationHeader != null) {
				location = locationHeader.getValue();
				// logger.error("The page was redirected to:" + location);
			} else {
				// logger.error("Location field value is null.");
			}
			return null;
		} else {
			String str = "";
			try {
				str = postMethod.getResponseBodyAsString();
				return str;
			} catch (IOException e) {
				// logger.error(e.getMessage());
			}
		}
		return null;
	}
}
