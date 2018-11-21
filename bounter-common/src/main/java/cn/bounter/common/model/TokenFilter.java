package cn.bounter.common.model;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Token过滤器
 * @author simon
 *
 */
public class TokenFilter implements Filter {

	//url白名单,在init方法中初始化
	private String[] ignores;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		ignores = filterConfig.getInitParameter("ignores").split(",");
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		//从请求头中获取Token
//		String token = request.getHeader("token");
		
		//进行URL白名单匹配
		for(String ignoreUrl : ignores) {
			//匹配成功
			if (request.getRequestURI().indexOf(ignoreUrl) > -1) {
				//放行
				filterChain.doFilter(request, response);
				return;
			}
		}

		//检查Session中是否存在Token
		if(request.getSession().getAttribute("token") != null) {
			//放行
			filterChain.doFilter(request, response);
			return;
		}
		
		//重定向到系统首页
		request.getRequestDispatcher("/unauthenticated").forward(request, response);
//		request.getRequestDispatcher("/admin/login.html").forward(request, response);
//		response.sendRedirect(request.getContextPath()+"/login.html");
		
	}

	@Override
	public void destroy() {
	}
	
}
