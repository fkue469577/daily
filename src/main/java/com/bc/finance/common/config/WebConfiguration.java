package com.bc.finance.common.config;

import com.bc.finance.common.exception.interceptor.UserAuthRestInterceptor;
import com.bc.finance.common.handler.GlobalExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author ace
 * @date 2017/9/8
 */
@Configuration("admimWebConfig")
@Primary
public class WebConfiguration implements WebMvcConfigurer {

    @Bean
    GlobalExceptionHandler getGlobalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getUserAuthRestInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(getExcludePathPatterns());
    }

    @Bean
    UserAuthRestInterceptor getUserAuthRestInterceptor() {
        return new UserAuthRestInterceptor();
    }

    private List<String> getExcludePathPatterns() {
        return Arrays.asList(
                "/static/**",
                "/test",
                "/captcha",
                "/login",
                "/upload/**",
                "/error",
                "classpath:/static/",
                "classpath:/META-INF/resources/"
                );
    }

}
