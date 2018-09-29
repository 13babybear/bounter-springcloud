package cn.bounter.common.util;

import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
	
	/**
	 * 创建Cookie
	 * @param response
	 * @param name		Cookie名字
	 * @param value		Cookie值
	 * @param secure	是否只对HTTPS可见
	 * @param maxAge	Cookie失效时间
	 * @param domain	Cookie域
	 */
    public static void create(HttpServletResponse response, String name, String value, Boolean secure, Integer maxAge, String domain) {
        Cookie cookie = new Cookie(name, value);
        cookie.setSecure(secure);
        //对JS不可见
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
//        cookie.setDomain(domain);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 清除指定名字的Cookie
     * @param response
     * @param name		Cookie名字
     */
    public static void clear(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    /**
     * 根据名字获取Cookie值
     * @param request
     * @param name		Cookie名字
     * @return
     */
    public static String getValue(HttpServletRequest request, String name) {
        Cookie cookie = WebUtils.getCookie(request, name);
        return cookie != null ? cookie.getValue() : null;
    }
}
