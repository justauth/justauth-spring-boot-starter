package com.xkcoding.justauth;

import com.xkcoding.justauth.properties.JustAuthProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * JustAuth 自动装配类
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-07-22 10:52
 */
@Configuration
@EnableConfigurationProperties(JustAuthProperties.class)
public class JustAuthAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "justauth", value = "enabled", havingValue = "true", matchIfMissing = true)
    public AuthRequestFactory authRequestFactory(JustAuthProperties properties) {
        return new AuthRequestFactory(properties);
    }

}
