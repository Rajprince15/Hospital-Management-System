package main.java.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    private static final List<String> PUBLIC_PATHS = Arrays.asList(
        "/", "/index.jsp", "/auth/login", "/auth/register", "/auth/logout",
        "/assets/", "/css/", "/js/", "/images/", "/api/public/"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        
        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        String path = requestURI.substring(contextPath.length());
        
        // Check if the path is public
        boolean isPublicPath = PUBLIC_PATHS.stream().anyMatch(publicPath -> 
            path.equals(publicPath) || path.startsWith(publicPath)
        );
        
        boolean isLoggedIn = session != null && session.getAttribute("user") != null;
        
        if (isPublicPath || isLoggedIn) {
            chain.doFilter(request, response);
        } else {
            // Redirect to login page for protected resources
            httpResponse.sendRedirect(contextPath + "/");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code if needed
    }

    @Override
    public void destroy() {
        // Cleanup code if needed
    }
}