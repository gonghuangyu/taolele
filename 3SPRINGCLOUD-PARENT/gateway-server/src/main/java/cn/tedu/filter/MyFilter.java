package cn.tedu.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MyFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }
     /*
        拿到请求的uri地址 /zuul-a/** /zuul-b/**
        判断是否以 /zuul-a/开始 String uri startWith
     */
    @Override
    public boolean shouldFilter() {
        //zuul 过滤器总是通过请求上下文对象,专门获取request response
        RequestContext context =
                RequestContext.getCurrentContext();
        //获取request
        HttpServletRequest request = context.getRequest();
        //获取请求地址 getUrl(域名端口) getUri(不包含域名端口)
        StringBuffer url = request.getRequestURL();
        String uri = request.getRequestURI();
        System.out.println(url.toString());
        System.out.println(uri);
        //判断当前请求是否 /zuul-a
        return uri.startsWith("/zuul-a");
    }
    /*
        已经经过shouldFilter判断该请求要经过过滤
        拿到请求对象,响应对象,看看请求参数是否有ticket
        有,就什么都不做
        没有,通过请求上下文,拒绝向后调用请求,直接返回响应了
     */
    @Override
    public Object run() throws ZuulException {
        //拿到请求,响应对象(如果是中文,需要设置中文配置)
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        HttpServletResponse response = context.getResponse();
        response.setContentType("text/html;charset=utf-8");
        //从request中获取参数
        String ticket = request.getParameter("ticket");
        //有可能是空 null ""
        if(StringUtils.isEmpty(ticket)){
            //context上下文拦截向后发送,不让继续执行后续逻辑
            context.setSendZuulResponse(false);
            context.setResponseBody
                    ("{\"code\":401,\"msg\":\"在zuul过滤器鉴权失败\"}");
            context.setResponseStatusCode(401);
        }
        return null;
    }
}
