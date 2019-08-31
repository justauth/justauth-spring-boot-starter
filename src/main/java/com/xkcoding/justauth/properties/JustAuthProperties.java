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

package com.xkcoding.justauth.properties;

import lombok.Getter;
import lombok.Setter;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.config.AuthSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

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

    /**
     * 缓存配置类
     */
    @NestedConfigurationProperty
    private CacheProperties cache;

}
