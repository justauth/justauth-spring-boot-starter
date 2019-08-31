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

package com.xkcoding.justauth;

import com.xkcoding.justauth.cache.RedisStateCache;
import com.xkcoding.justauth.properties.CacheProperties;
import com.xkcoding.justauth.properties.JustAuthProperties;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.cache.AuthDefaultStateCache;
import me.zhyd.oauth.cache.AuthStateCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * <p>
 * JustAuth 自动装配类
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-07-22 10:52
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(JustAuthProperties.class)
public class JustAuthAutoConfiguration {
    @Autowired(required = false)
    private RedisTemplate<String, String> justAuthRedisCacheTemplate;

    @Bean
    @ConditionalOnProperty(prefix = "justauth", value = "enabled", havingValue = "true", matchIfMissing = true)
    public AuthRequestFactory authRequestFactory(JustAuthProperties properties, AuthStateCache authStateCache) {
        return new AuthRequestFactory(properties, authStateCache);
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthStateCache authStateCache(JustAuthProperties justAuthProperties) {
        if (CacheProperties.CacheType.REDIS == justAuthProperties.getCache().getType()) {
            log.debug("JustAuth 使用 Redis 缓存存储 state 数据");
            assert justAuthRedisCacheTemplate != null;
            return new RedisStateCache(justAuthRedisCacheTemplate, justAuthProperties.getCache());
        } else if (CacheProperties.CacheType.CUSTOM == justAuthProperties.getCache().getType()) {
            log.debug("JustAuth 使用 自定义缓存存储 state 数据，请自行实现 me.zhyd.oauth.cache.AuthStateCache");
            return null;
        } else {
            log.debug("JustAuth 使用 默认缓存存储 state 数据");
            return AuthDefaultStateCache.INSTANCE;
        }
    }

}
