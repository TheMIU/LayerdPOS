package lk.ijse.jsp.filter;

import lk.ijse.jsp.servlet.util.ResponseUtil;

import javax.json.JsonObject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebFilter(urlPatterns = "/*", filterName = "B")
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Auth Filter Init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        System.out.println("Auth Filter Do Filter Invoked");

        /*String auth = req.getHeader("Auth");
        System.out.println(auth);*/

        String auth = "user=admin,pass=1234";

        res.addHeader("Content-Type", "application/json");

        if (auth != null && auth.equals("user=admin,pass=1234")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            res.addHeader("Access-Control-Allow-Origin", "*");
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            JsonObject jsonObject = ResponseUtil.genJson("Auth-Error", "You are not Authenticated to use this Service.!");
            res.getWriter().print(jsonObject);
        }
    }

    @Override
    public void destroy() {

    }
}
