package net.devstudy.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName="TestFilter", urlPatterns="/*")
public class TestFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
        filterConfig.getServletContext();
    }
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        req.setAttribute("CURRENT_URL", req.getRequestURI());
        chain.doFilter(request, response);
    }
    public void destroy() {
        System.out.println("Destroy ");
    }
}