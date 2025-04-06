package com.musinsa.ohj.configuration;

import com.musinsa.ohj.presentation.filter.MdcLoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebFilterConfiguration {
    @Bean
    public FilterRegistrationBean<MdcLoggingFilter> mdcLoggingFilterBean() {
        FilterRegistrationBean<MdcLoggingFilter> registrationBean =
                new FilterRegistrationBean<>(new MdcLoggingFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
