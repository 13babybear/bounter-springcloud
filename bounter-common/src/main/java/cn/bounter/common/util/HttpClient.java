package cn.bounter.common.util;

import cn.bounter.common.model.JacksonFactory;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;


/**
 * rest http请求的客户端工具 每个新的请求独立启动一个线程去发送，请求间不会相互堵塞
 * 
 * @author sheng.zhao
 *
 */
public class HttpClient {

	// 初始化HttpClient对象
	private static CloseableHttpClient httpClient = HttpClients.createDefault();

	public static CloseableHttpClient createSSLClientDefault() {  
        try {  
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {  
                // 信任所有 
            	@Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {  
                    return true;  
                }
            }).build();  
            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;  
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);  
            HttpHost proxy = new HttpHost("localhost",8888);
			RequestConfig config = RequestConfig.custom().setProxy(proxy).setConnectTimeout(10000).setSocketTimeout(15000).build();
            return HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultRequestConfig(config).build();  
        } catch (KeyManagementException e) {  
            e.printStackTrace();  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        } catch (KeyStoreException e) {  
            e.printStackTrace();  
        }  
        return HttpClients.createDefault();  
  
    }
	
	public static String doGet (final String url) throws Exception {
		FutureTask<String> task = new FutureTask<String>(
						new Callable<String>() {

							@Override
							public String call () throws Exception {
								String result = "";
								// 创建HttpGet对象
								HttpGet httpGet = new HttpGet(url);
								// 发送get请求获取HttpResponse
								CloseableHttpResponse response = httpClient
												.execute(httpGet);
								try {
									// 如果服务器成功地返回响应
									if (response.getStatusLine()
													.getStatusCode() == 200) {
										// 获取服务器响应内容
										result = EntityUtils.toString(
														response.getEntity(),
														StandardCharsets.UTF_8);
									}
								} finally {
									response.close();
								}
								return result;
							}
						});
		new Thread(task).start();
		return task.get();
	}
	
	/**
	 * 启用新的线程发送Get请求， 利用 FutureTask的回调方法让主线程从新线程中获取返回内容
	 * 
	 * @param url
	 *        请求的url
	 * @return 服务器端响应内容字符串
	 * @throws Exception
	 */
	public static String doGet (final String url, Map<String, String> headers) throws Exception {
		FutureTask<String> task = new FutureTask<String>(
						new Callable<String>() {

							@Override
							public String call () throws Exception {
								String result = "";
								// 创建HttpGet对象
								HttpGet httpGet = new HttpGet(url);
								if(headers != null) {
									headers.forEach((k,v) -> {
										httpGet.addHeader(k, v);
									});
						    	}
								// 发送get请求获取HttpResponse
								CloseableHttpResponse response = httpClient
												.execute(httpGet);
								try {
									// 如果服务器成功地返回响应
									if (response.getStatusLine()
													.getStatusCode() == 200) {
										// 获取服务器响应内容
										result = EntityUtils.toString(
														response.getEntity(),
														StandardCharsets.UTF_8);
									}
								} finally {
									response.close();
								}
								return result;
							}
						});
		new Thread(task).start();
		return task.get();
	}

	/**
	 * 拼接URL方式发送post请求
	 * 
	 * @param url
	 *        请求url
	 * @return 服务器端响应内容字符串
	 * @throws Exception
	 */
	public static String doPost (final String url) throws Exception {
		FutureTask<String> task = new FutureTask<String>(
						new Callable<String>() {

							@Override
							public String call () throws Exception {
								String result = "";
								// 创建HttpPost对象
								HttpPost httpPost = new HttpPost(url);
								RequestConfig requestConfig = RequestConfig.custom()    
								        .setConnectTimeout(5000).setConnectionRequestTimeout(5000)    
								        .setSocketTimeout(5000).build();
								httpPost.setConfig(requestConfig);
								// 发送post请求获取HttpResponse
								CloseableHttpResponse response = httpClient
												.execute(httpPost);
								try {
									// 如果服务器成功地返回响应
									if (response.getStatusLine()
													.getStatusCode() == 200) {
										// 获取服务器响应内容
										result = EntityUtils.toString(
														response.getEntity(),
														StandardCharsets.UTF_8);
									}
								} finally {
									response.close();
								}
								return result;
							}
						});
		new Thread(task).start();
		return task.get();
	}
	
	public static String doPost (final String url, final Map<String, String> querys) throws Exception {
		FutureTask<String> task = new FutureTask<String>(
						new Callable<String>() {

							@Override
							public String call () throws Exception {
								String result = "";
								// 创建HttpPost对象
								HttpPost httpPost = new HttpPost(url);
								// 对传递的请求参数进行封装
								List<NameValuePair> requestParams = new ArrayList<NameValuePair>();
								querys.forEach((k,v) -> {
									requestParams.add(new BasicNameValuePair(k, v));
								});
								// 设置请求参数
								httpPost.setEntity(new UrlEncodedFormEntity(
												requestParams,
												StandardCharsets.UTF_8));
								// 发送post请求获取HttpResponse
								CloseableHttpResponse response = httpClient
												.execute(httpPost);
								try {
									// 如果服务器成功地返回响应
									if (response.getStatusLine()
													.getStatusCode() == 200) {
										// 获取服务器响应内容
										result = EntityUtils.toString(
														response.getEntity(),
														StandardCharsets.UTF_8);
									}
								} finally {
									response.close();
								}
								return result;
							}
						});
		new Thread(task).start();
		return task.get();
	}
	
	
	/**
	 * 发送 application/json POST 请求
	 * @param url				 	请求地址
	 * @param jsonBody				请求体字符串（urf-8）
	 * @param headers				请求头
	 * @return
	 * @throws Exception
	 */
	public static String doPost (final String url, final String jsonBody, final Map<String, String> headers) throws Exception {
		FutureTask<String> task = new FutureTask<String>(
						new Callable<String>() {

							@Override
							public String call () throws Exception {
								String result = "";
								// 创建HttpPost对象
								HttpPost httpPost = new HttpPost(url);
								// 设置请求头
								if(headers != null) {
									headers.forEach((k,v) -> {
										httpPost.addHeader(k, v);
									});
						    	}
								// 设置Json请求参数
								StringEntity stringEntity = new StringEntity(jsonBody, "utf-8");
								stringEntity.setContentType("application/json");			//发送json数据需要设置contentType
								httpPost.setEntity(stringEntity);
								// 发送post请求获取HttpResponse
								CloseableHttpResponse response = httpClient
												.execute(httpPost);
								try {
									// 如果服务器成功地返回响应
										// 获取服务器响应内容
										result = EntityUtils.toString(
														response.getEntity(),
														StandardCharsets.UTF_8);
								} finally {
									response.close();
								}
								return result;
							}
						});
		new Thread(task).start();
		return task.get();
	}
	
	/**
	 * 发送 application/json POST 请求
	 * @param url				请求地址，如：http://localhost
	 * @param body				请求体
	 * @param headers			请求头
	 * @return
	 * @throws Exception
	 */
	public static String doPost (final String url, final Object body, final Map<String, String> headers) throws Exception {
		FutureTask<String> task = new FutureTask<String>(
						new Callable<String>() {

							@Override
							public String call () throws Exception {
								String result = "";
								// 创建HttpPost对象
								HttpPost httpPost = new HttpPost(url);
								// 设置请求头
								if(headers != null) {
									headers.forEach((k,v) -> {
										httpPost.addHeader(k, v);
									});
						    	}
								// 设置Json请求体
								StringEntity stringEntity = new StringEntity(JacksonFactory.getMapper().writeValueAsString(body), "utf-8");
								stringEntity.setContentType("application/json");			//发送json数据需要设置contentType
								httpPost.setEntity(stringEntity);
								// 发送post请求获取HttpResponse
								CloseableHttpResponse response = httpClient
												.execute(httpPost);
								try {
									// 如果服务器成功地返回响应
										// 获取服务器响应内容
										result = EntityUtils.toString(
														response.getEntity(),
														StandardCharsets.UTF_8);
								} finally {
									response.close();
								}
								return result;
							}
						});
		new Thread(task).start();
		return task.get();
	}
	
	/**
	 * 发送 application/json POST 请求
	 * @param url			请求地址，如：http://localhost
	 * @param body			请求体
	 * @return
	 * @throws Exception
	 */
	public static String doPost (final String url, final Object body) throws Exception {
		FutureTask<String> task = new FutureTask<String>(
						new Callable<String>() {

							@Override
							public String call () throws Exception {
								String result = "";
								// 创建HttpPost对象
								HttpPost httpPost = new HttpPost(url);
								// 设置Json请求体
								StringEntity stringEntity = new StringEntity(JacksonFactory.getMapper().writeValueAsString(body), "utf-8");
								stringEntity.setContentType("application/json");			//发送json数据需要设置contentType
								httpPost.setEntity(stringEntity);
								// 发送post请求获取HttpResponse
								CloseableHttpResponse response = httpClient
												.execute(httpPost);
								try {
									// 如果服务器成功地返回响应
										// 获取服务器响应内容
										result = EntityUtils.toString(
														response.getEntity(),
														StandardCharsets.UTF_8);
								} finally {
									response.close();
								}
								return result;
							}
						});
		new Thread(task).start();
		return task.get();
	}
	
	public static void main(String[] args) throws Exception {
		String url = "https://xpaper.com/admin/x/plan/sync";
		String jsonBody = "{\n\t\"id\":\"4078246\"\n}";
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ4cGFwZXIiLCJzdWIiOiJhdXRoIiwiaWF0IjoxNTI5NDg4Nzk5LCJleHAiOjE1Mjk0OTIzOTl9.bOF007web254U-5hR1irdjevFeOLtM05k0xUxbj350aOJvWLis8dUg8atSzgzM5KwuUcnsG2WC5kVMe4JI6ZP47i2RUDaFN9xISQn1JrvQiM8iQuG1OlnKMgUNPy3Ak2VU24MDh1P0BiOjneWjfuZK8loIeceeO2ytO9pX4k-fE1Rq0lOuGvWS23nQTYeEx5IO4HxsmdJHh5mD_rHUjgKdjjRndHPCPsR8vSlRk5RRZyzZWHU_d0FnvgWEfS_a-IfGpWZ5yNGe36SX9ukoftOLg4WR02fUtYVs5liUUVLCF73x3Mj6tq_OLzzglilS-w9u7U0r9YUBzflIRw9F8gGw");
		System.out.println(doPost(url, jsonBody, headers));
	}
}
