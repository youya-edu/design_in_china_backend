package org.dic.demo.security;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Never use this config in production!
 */
@Component
@Profile("dev")
public class AllowAllCORSConfigurer implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("*")
                // about allowCredentials, see
                // <a>https://developer.mozilla.org/zh-CN/docs/Web/API/XMLHttpRequest/withCredentials</a>
                // if false, CORS request can not use cookie
                .allowCredentials(true)
                .maxAge(86400);
    }
}
