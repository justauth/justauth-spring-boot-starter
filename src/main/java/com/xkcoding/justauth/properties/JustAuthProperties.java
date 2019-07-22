package com.xkcoding.justauth.properties;

import lombok.Getter;
import lombok.Setter;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.config.AuthSource;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * JustAuth自动装配配置类
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-07-22 10:59
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "justauth")
public class JustAuthProperties {
    /**
     * 是否启用 JustAuth
     */
    private boolean enabled;

    /**
     * JustAuth 配置
     */
    private Map<AuthSource, AuthConfig> type = new HashMap<>();

}
