package com.guptaji.springboot_learning.customFilter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RequestedUriFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(RequestedUriFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        LOG.info("Custom Filter of application has been invoked");
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        LOG.info("Requested URI {}", httpServletRequest.getRequestURI());

        // this step will pass the request to either next filter or to dispatcher if it'll be the
        // last filter in the chain
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
