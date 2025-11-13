package main.java.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        
        String loginURI = httpRequest.getContextPath() + "/auth/login";
        boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI);
        boolean isLoginPage = httpRequest.getRequestURI().endsWith("index.jsp");
        boolean isLoggedIn = session != null && session.getAttribute("user") != null;
        
        if (isLoggedIn && (isLoginRequest || isLoginPage)) {
            // Redirect to dashboard if user is already logged in
            String role = (String) session.getAttribute("role");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/" + role.toLowerCase() + "/dashboard");
        } else if (!isLoggedIn && !isLoginRequest && !isLoginPage) {
            // Redirect to login page if user is not logged in
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/index.jsp");
        } else {
            chain.doFilter(request, response);
        }
    }
}