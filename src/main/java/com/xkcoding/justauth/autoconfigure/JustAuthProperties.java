/*
 * Copyright (c) 2019-2029, xkcoding & Yangkai.Shen & 沈扬凯 (237497819@qq.com & xkcoding.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.xkcoding.justauth.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import me.zhyd.oauth.config.AuthConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.net.Proxy;
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
     * JustAuth 默认配置
     */
    private Map<String, AuthConfig> type = new HashMap<>();


    /**
     * http 相关的配置，可设置请求超时时间和代理配置
     */
    private JustAuthHttpConfig httpConfig;

    /**
     * JustAuth 自定义配置
     */
    @NestedConfigurationProperty
    private ExtendProperties extend;

    /**
     * 缓存配置类
     */
    @NestedConfigurationProperty
    private CacheProperties cache = new CacheProperties();

    @Getter
    @Setter
    public static class JustAuthProxyConfig {
        private String type = Proxy.Type.HTTP.name();
        private String hostname;
        private int port;
    }

    @Getter
    @Setter
    public static class JustAuthHttpConfig {
        private int timeout;
        private Map<String, JustAuthProxyConfig> proxy;
    }

}
