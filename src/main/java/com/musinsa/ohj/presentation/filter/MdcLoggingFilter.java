package com.musinsa.ohj.presentation.filter;

import com.musinsa.ohj.common.utils.StrUtils;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;

import java.io.IOException;

public class MdcLoggingFilter implements Filter {

    private final String TRACE_ID = "traceId";
    private final String SESSION_ID = "sessId";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;

            MDC.put(SESSION_ID, httpRequest.getSession() == null ? "" : httpRequest.getSession().getId());
            MDC.put(TRACE_ID, StrUtils.getRandomAlphanumeric(16));

            chain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}
