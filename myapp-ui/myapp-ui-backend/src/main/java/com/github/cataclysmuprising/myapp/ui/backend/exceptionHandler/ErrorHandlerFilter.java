package com.github.cataclysmuprising.myapp.ui.backend.exceptionHandler;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;

// To handle all exceptions out of Spring Controllers
@Component
public class ErrorHandlerFilter implements Filter {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = ((HttpServletRequest) request);

        HttpServletResponse httpServletResponse = ((HttpServletResponse) response);
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/error/500");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {

    }

}
