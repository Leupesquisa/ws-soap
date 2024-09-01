package com.demo.web.filter;

import com.demo.web.FakeLoginBean;
import java.io.IOException;
import jakarta.inject.Inject;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/**
 *
 * @author Leu A. Manuel
 * @see https://github.com/Leupesquisa
 */
@WebFilter(filterName = "LoginFilter", urlPatterns = {"/*"})
public class LoginFilter implements Filter {
    
    @Inject
    FakeLoginBean loginBean;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        boolean loggedIn = loginBean.getLoggedInUser()!= null;
        String loginURL = request.getContextPath() + "/fake-login.xhtml";

        if (!loggedIn && !loginOrService(request, loginURL)) {
            response.sendRedirect(loginURL);
        } else {
            chain.doFilter(request, response);
        }
    }
    
    private boolean loginOrService(HttpServletRequest request, String loginURL) {
        if(request.getRequestURI().endsWith("WebService") || request.getRequestURI().equals(loginURL)) {
            return true;
        }
        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

}
