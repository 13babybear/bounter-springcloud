package cn.bounter.common.util;

import cn.bounter.common.model.JacksonFactory;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
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
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
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
 */
public class HttpClient {

    // 初始化HttpClient对象
    private static CloseableHttpClient httpClient = HttpClients.createDefault();


    //抓包配置，不需要抓包时请忽略
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
            HttpHost proxy = new HttpHost("localhost", 8888);
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


    /**
     * GET
     * @param url       请求地址
     * @return
     * @throws Exception
     */
    public static String doGet(String url) throws Exception {
        return doGet(url, null);
    }


    /**
     * GET
     * @param url               请求地址
     * @param headers           请求头
     * @return
     * @throws Exception
     */
    public static String doGet(String url, Map<String, String> headers) throws Exception {
        FutureTask<String> task = new FutureTask<String>(
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        HttpGet httpGet = new HttpGet(url);

                        //设置请求头
                        if (headers != null) {
                            headers.forEach((k, v) -> {
                                httpGet.addHeader(k, v);
                            });
                        }

                        // 发送get请求获取HttpResponse
                        CloseableHttpResponse response = httpClient.execute(httpGet);

                        //解析响应
                        return parseResponse(response);
                    }
                });

        new Thread(task).start();
        return task.get();
    }


    /**
     * POST
     * @param url       请求地址
     * @return
     * @throws Exception
     */
    public static String doPost( String url) throws Exception {
        FutureTask<String> task = new FutureTask<String>(
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        HttpPost httpPost = new HttpPost(url);

                        RequestConfig requestConfig = RequestConfig.custom()
                                .setConnectTimeout(5000).setConnectionRequestTimeout(5000)
                                .setSocketTimeout(5000).build();
                        httpPost.setConfig(requestConfig);
                        // 发送post请求获取HttpResponse
                        CloseableHttpResponse response = httpClient.execute(httpPost);

                        //解析响应
                        return parseResponse(response);
                    }
                });

        new Thread(task).start();
        return task.get();
    }


    /**
     * POST(application/x-www-form-urlencoded)
     * @param url               请求地址
     * @param querys            请求头
     * @return
     * @throws Exception
     */
    public static String doPost(String url, Map<String, String> querys) throws Exception {
        FutureTask<String> task = new FutureTask<String>(
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        HttpPost httpPost = new HttpPost(url);

                        // 设置请求参数
                        List<NameValuePair> requestParams = new ArrayList<NameValuePair>();
                        querys.forEach((k, v) -> {
                            requestParams.add(new BasicNameValuePair(k, v));
                        });
                        httpPost.setEntity(new UrlEncodedFormEntity(requestParams, StandardCharsets.UTF_8));

                        // 发送post请求获取HttpResponse
                        CloseableHttpResponse response = httpClient.execute(httpPost);

                        //解析响应
                        return parseResponse(response);
                    }
                });

        new Thread(task).start();
        return task.get();
    }


    /**
     * 上传文件
     * @param url    请求地址
     * @param querys 请求参数(文件为File类型,普通参数为String类型)
     * @return
     * @throws Exception
     */
    public static String doUpload(String url, Map<String, Object> querys) throws Exception {
        FutureTask<String> task = new FutureTask<String>(
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        HttpPost httpPost = new HttpPost(url);

                        // 设置请求参数
                        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.RFC6532);        //解决中文文件名乱码
                        querys.forEach((k, v) -> {
                            if (v instanceof File) {
                                multipartEntityBuilder.addPart(k, new FileBody((File) v));
                            } else {
                                multipartEntityBuilder.addPart(k, new StringBody((String) v, ContentType.create("text/plain", Consts.UTF_8)));    //解决中文参数乱码
                            }
                        });
                        httpPost.setEntity(multipartEntityBuilder.build());

                        // 发送post请求获取HttpResponse
                        CloseableHttpResponse response = httpClient.execute(httpPost);

                        //解析响应
                        return parseResponse(response);
                    }
                });

        new Thread(task).start();
        return task.get();
    }


    /**
     * POST(application/json)
     * @param url           请求地址
     * @param body          请求体
     * @param headers       请求头
     * @return
     * @throws Exception
     */
    public static String doPost(String url, Object body, Map<String, String> headers) throws Exception {
        FutureTask<String> task = new FutureTask<String>(
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        HttpPost httpPost = new HttpPost(url);

                        // 设置请求头
                        if (headers != null) {
                            headers.forEach((k, v) -> {
                                httpPost.addHeader(k, v);
                            });
                        }

                        // 设置Json请求体
                        StringEntity stringEntity = new StringEntity(JacksonFactory.getMapper().writeValueAsString(body), "utf-8");
                        stringEntity.setContentType("application/json");            //发送json数据需要设置contentType
                        httpPost.setEntity(stringEntity);

                        // 发送post请求获取HttpResponse
                        CloseableHttpResponse response = httpClient.execute(httpPost);

                        //解析响应
                        return parseResponse(response);
                    }
                });

        new Thread(task).start();
        return task.get();
    }


    /**
     * POST(application/json)
     * @param url  请求地址
     * @param body 请求体
     * @return
     * @throws Exception
     */
    public static String doPost(String url, Object body) throws Exception {
        return doPost(url, body, null);
    }


    /**
     * 把响应解析成字符串，并关闭资源和连接
     * @param response
     * @return
     * @throws IOException
     */
    private static String parseResponse(CloseableHttpResponse response) throws IOException {
        String result = null;

        try {
            HttpEntity entity = response.getEntity();
            // 如果服务器成功地返回响应
            if (response.getStatusLine().getStatusCode() == 200) {
                // 获取服务器响应内容
                result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            }
            //释放资源
            EntityUtils.consume(entity);
        } finally {
            //释放连接
            response.close();
        }

        return result;
    }


    public static void main(String[] args) throws Exception {
        System.out.println(doGet("https://speedpdf.com/convertor/api/convert_record"));
    }
}
