package lk.ijse.jsp.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = "/*", filterName = "B")
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Auth Filter Init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);

        /*HttpServletResponse res = (HttpServletResponse) servletResponse;
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        System.out.println("Auth Filter Do Filter Invoked");

        //Interrupt the request and check the Auth header with every request
        String auth = req.getHeader("Auth");

        //so, if the Auth header is present and username & password
        //are matching with every request then we can proceed the request
        //to relevant servlet
        if (auth != null && auth.equals("user=admin,pass=admin")) {
            //forward the request to the requested servlet
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            //otherwise send a json object that the clint is not allowed
            //to access the service
            res.addHeader("Content-Type", "application/json");
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            JsonObject jsonObject = ResponseUtil.genJson("Auth-Error", "You are not Authenticated to use this Service.!");
            res.getWriter().print(jsonObject);
        }*/

    }

    @Override
    public void destroy() {

    }
}
