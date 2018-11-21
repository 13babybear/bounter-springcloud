package cn.bounter.common.model;



import cn.bounter.common.util.JwtUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT Token过滤器基类
 * 子类只需要继承它，并加上类似@WebFilter("/api/*")注解即可
 * @author simon
 *
 */
public class BaseTokenFilter implements Filter {

	private static final String REQUEST_METHOD_OPTIONS = "OPTIONS";
	private static final String AUTHORIZATION_PREFIX = "Bearer ";

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		//解决跨域问题
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
		response.setHeader("Access-Control-Allow-Methods","GET, POST, PUT, DELETE");
		response.setHeader("Access-Control-Expose-Headers","Authorization");

		//解决Content-Type:application/json时跨域设置失败问题
		if(REQUEST_METHOD_OPTIONS.equals(request.getMethod())) {
			//放行OPTIONS请求
			filterChain.doFilter(request, response);
			return;
		}

		//解析JWT
		//从Header中获取accessToken
		String authHeader = request.getHeader("Authorization");
		if(authHeader == null || !authHeader.startsWith(AUTHORIZATION_PREFIX)) {
			//返回“401”未授权
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		String accessToken = authHeader.substring(AUTHORIZATION_PREFIX.length());
		String userId = JwtUtil.parseToken(accessToken);
		if(userId == null){
			//返回“401”未授权
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		request.setAttribute("userId", userId);
		filterChain.doFilter(request, response);

	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

}
