package cn.bounter.gateway.filter;

import brave.Tracer;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * 跟踪ID过滤器，为所有从网关出去的响应头加上跟踪ID
 */
@Component
public class TraceIdPostFilter extends ZuulFilter {

    @Autowired
    private Tracer tracer;

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletResponse response = requestContext.getResponse();
        //从tracer中获取trace_id
        response.addHeader("Trace-Id", tracer.currentSpan().context().traceIdString());
        return null;
    }
}
