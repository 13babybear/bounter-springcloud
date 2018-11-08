package cn.bounter.common.util;

import cn.bounter.common.model.IpAddress;
import cn.bounter.common.model.JacksonFactory;
import cn.bounter.common.model.Location;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;


/**
 * IP地址工具类
 * 
 * @author simon
 *
 */
public class IpUtil {
	
	private static final String IPLIB_URL = "http://ip.taobao.com/service/getIpInfo.php";

	/**
	 * 获取客户端真实IP
	 * @param request
	 * @return
	 */
	public static final String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if ("127.0.0.1".equals(ip)) {
			InetAddress inet = null;
			try { // 根据网卡取本机配置的IP
				inet = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			ip = inet.getHostAddress();
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ip != null && ip.length() > 15) {
			if (ip.indexOf(",") > 0) {
				ip = ip.substring(0, ip.indexOf(","));
			}
		}
		return ip;
	}
	

	/**
	 * 根据IP获取地址信息
	 * @param ip
	 * @return
	 * @throws IOException
	 */
	public static IpAddress getAddress(String ip) throws IOException {
		//发送请求，获取地址信息
		String ret = getResult(ip);
		
		if (ret != null && ret.contains("\"ip\":")) {
			//解析地址信息
			ret = ret.substring(ret.indexOf("{\"ip\":"),ret.indexOf("}}") + 1);
			return JacksonFactory.getMapper().readValue(ret, IpAddress.class);
		}
		
		return null;
	}
	
	/**
	 * 调用阿里云API市场获取地址信息
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	public static String getLocationStr(String ip) throws Exception {
		//发送请求，获取地址信息
		String url = "http://iploc.market.alicloudapi.com/v3/ip?ip=" + ip;
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "APPCODE 5a97d782d56f471a9790161f5a7572a1");
		String ret = HttpClient.doGet(url, headers);;
		
		if (!StringUtils.isEmpty(ret)) {
			//解析地址信息
			Location location = JacksonFactory.getMapper().readValue(ret, Location.class);
			return new StringBuilder().append(location.getProvince()).append(location.getCity()).toString();
		}
		
		return null;
	}


	public static String getLocationStr(HttpServletRequest request) {
		Location location = getLocation(request);
		if (location == null) {
			return null;
		}
		return new StringBuilder().append(location.getProvince()).append(location.getCity()).toString();
	}
	
	public static Location getLocation(String ip) {
		//发送请求，获取地址信息
		String url = "http://iploc.market.alicloudapi.com/v3/ip?ip=" + ip;
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "APPCODE 5a97d782d56f471a9790161f5a7572a1");
		String ret;
		try {
			ret = HttpClient.doGet(url, headers);
			if (!StringUtils.isEmpty(ret)) {
				//解析地址信息
				return JacksonFactory.getMapper().readValue(ret, Location.class);
			}
		} catch (Exception e) {};
		
		return null;
	}
	
	public static Location getLocation(HttpServletRequest request) {
		Location location = null;
		try {
			location =  getLocation(getClientIp(request));
		} catch (Exception e) {}
		return location;
	}
	
	/**
	 * 根据ip获取“国家-省-城市”地址字符串
	 * @param ip
	 * @return
	 * @throws IOException
	 */
	public static String getAddressStr(String ip) throws IOException {
		IpAddress ipAddress = getAddress(ip);
		return ipAddress == null ? "未知" : new StringBuilder().append(ipAddress.getCountry()).append(ipAddress.getRegion()).append(ipAddress.getCity()).toString();
	}
	
	/**
	 * 获取“国家-省-城市”地址字符串
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static String getAddressStr(HttpServletRequest request) throws IOException {
		IpAddress ipAddress = getAddress(getClientIp(request));
		return ipAddress == null ? "未知" : new StringBuilder().append(ipAddress.getCountry()).append(ipAddress.getRegion()).append(ipAddress.getCity()).toString();
	}
	
	
	private static String getResult(String ip) {
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(IPLIB_URL);
			connection = (HttpURLConnection) url.openConnection();// 新建连接实例
			/**
			 * 超时错误 由 2s改为5s
			 */
			connection.setConnectTimeout(5000);// 设置连接超时时间，单位毫秒
			connection.setReadTimeout(5000);// 设置读取数据超时时间，单位毫秒
			connection.setDoOutput(true);// 是否打开输出流 true|false
			connection.setDoInput(true);// 是否打开输入流true|false
			connection.setRequestMethod("POST");// 提交方法POST|GET
			connection.setUseCaches(false);// 是否缓存true|false
			connection.connect();// 打开连接端口
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());// 打开输出流往对端服务器写数据
			out.writeBytes("ip=" + ip);// 写数据,也就是提交你的表单 name=xxx&pwd=xxx
			out.flush();// 刷新
			out.close();// 关闭输出流
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));// 往对端写完数据对端服务器返回数据
			// ,以BufferedReader流来读取
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();// 关闭连接
			}
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
	}
}
