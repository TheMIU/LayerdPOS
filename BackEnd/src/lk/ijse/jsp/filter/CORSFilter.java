package lk.ijse.jsp.filter;

import lk.ijse.jsp.servlet.util.ResponseUtil;

import javax.json.JsonObject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*", filterName = "A")
public class CORSFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("CORS Filter Init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        System.out.println("CORS Filter Do Filter Invoked");

        String method = req.getMethod();

        if (method.equals("OPTIONS")) {
            // Handle CORS preflight request
            res.setStatus(200);

            res.addHeader("Access-Control-Allow-Origin", "*");
            res.addHeader("Access-Control-Allow-Methods", "PUT, DELETE");
            res.addHeader("Access-Control-Allow-Headers", "content-type, auth");
        } else {
            String auth = req.getHeader("Auth");
            System.out.println(auth);
            res.addHeader("Content-Type", "application/json");

            if (auth != null && auth.equals("user=admin,pass=1234")) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                JsonObject jsonObject = ResponseUtil.genJson("Auth-Error", "You are not Authenticated to use this Service.!");
                res.getWriter().print(jsonObject);
            }

            res.addHeader("Access-Control-Allow-Origin", "*");
        }
    }

    @Override
    public void destroy() {

    }
}
