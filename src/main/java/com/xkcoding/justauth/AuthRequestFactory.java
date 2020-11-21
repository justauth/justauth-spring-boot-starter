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

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.xkcoding.http.config.HttpConfig;
import com.xkcoding.justauth.autoconfigure.ExtendProperties;
import com.xkcoding.justauth.autoconfigure.JustAuthProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.cache.AuthStateCache;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.config.AuthDefaultSource;
import me.zhyd.oauth.config.AuthSource;
import me.zhyd.oauth.enums.AuthResponseStatus;
import me.zhyd.oauth.exception.AuthException;
import me.zhyd.oauth.request.AuthDefaultRequest;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.util.CollectionUtils;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * AuthRequest工厂类
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-07-22 14:21
 */
@Slf4j
@RequiredArgsConstructor
public class AuthRequestFactory {
    private final JustAuthProperties properties;
    private final AuthStateCache authStateCache;

    /**
     * 返回当前Oauth列表
     *
     * @return Oauth列表
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<String> oauthList() {
        // 默认列表
        List<String> defaultList = new ArrayList<>(properties.getType().keySet());
        // 扩展列表
        List<String> extendList = new ArrayList<>();
        ExtendProperties extend = properties.getExtend();
        if (null != extend) {
            Class enumClass = extend.getEnumClass();
            List<String> names = EnumUtil.getNames(enumClass);
            // 扩展列表
            extendList = extend.getConfig()
                .keySet()
                .stream()
                .filter(x -> names.contains(x.toUpperCase()))
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        }

        // 合并
        return (List<String>) CollUtil.addAll(defaultList, extendList);
    }

    /**
     * 返回AuthRequest对象
     *
     * @param source {@link AuthSource}
     * @return {@link AuthRequest}
     */
    public AuthRequest get(String source) {
        if (StrUtil.isBlank(source)) {
            throw new AuthException(AuthResponseStatus.NO_AUTH_SOURCE);
        }

        // 获取 JustAuth 中已存在的
        AuthRequest authRequest = getDefaultRequest(source);

        // 如果获取不到则尝试取自定义的
        if (authRequest == null) {
            authRequest = getExtendRequest(properties.getExtend().getEnumClass(), source);
        }

        if (authRequest == null) {
            throw new AuthException(AuthResponseStatus.UNSUPPORTED);
        }

        return authRequest;
    }

    /**
     * 获取自定义的 request
     *
     * @param clazz  枚举类 {@link AuthSource}
     * @param source {@link AuthSource}
     * @return {@link AuthRequest}
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private AuthRequest getExtendRequest(Class clazz, String source) {
        String upperSource = source.toUpperCase();
        try {
            EnumUtil.fromString(clazz, upperSource);
        } catch (IllegalArgumentException e) {
            // 无自定义匹配
            return null;
        }

        Map<String, ExtendProperties.ExtendRequestConfig> extendConfig = properties.getExtend().getConfig();

        // key 转大写
        Map<String, ExtendProperties.ExtendRequestConfig> upperConfig = new HashMap<>(6);
        extendConfig.forEach((k, v) -> upperConfig.put(k.toUpperCase(), v));

        ExtendProperties.ExtendRequestConfig extendRequestConfig = upperConfig.get(upperSource);
        if (extendRequestConfig != null) {

            // 配置 http config
            configureHttpConfig(upperSource, extendRequestConfig, properties.getHttpConfig());

            Class<? extends AuthRequest> requestClass = extendRequestConfig.getRequestClass();

            if (requestClass != null) {
                // 反射获取 Request 对象，所以必须实现 2 个参数的构造方法
                return ReflectUtil.newInstance(requestClass, (AuthConfig) extendRequestConfig, authStateCache);
            }
        }

        return null;
    }


    /**
     * 获取默认的 Request
     *
     * @param source {@link AuthSource}
     * @return {@link AuthRequest}
     */
    private AuthRequest getDefaultRequest(String source) {
        AuthDefaultSource authDefaultSource;

        try {
            authDefaultSource = EnumUtil.fromString(AuthDefaultSource.class, source.toUpperCase());
        } catch (IllegalArgumentException e) {
            // 无自定义匹配
            return null;
        }

        AuthConfig config = properties.getType().get(authDefaultSource.name());
        // 找不到对应关系，直接返回空
        if (config == null) {
            return null;
        }

        // 配置 http config
        configureHttpConfig(authDefaultSource.name(), config, properties.getHttpConfig());

        switch (authDefaultSource) {
            case FEISHU:
            case CSDN:
                return null;
            default:
        }

        return getAuthDefaultRequest(config, authDefaultSource, authStateCache);
    }

    /**
     * 获取 {@link AuthDefaultRequest} 的适配器
     *
     * @param config         {@link AuthDefaultRequest} 的 {@link AuthConfig}
     * @param source         {@link AuthDefaultRequest} 的 {@link AuthSource}
     * @param authStateCache {@link AuthDefaultRequest} 的 {@link AuthStateCache}
     * @return {@link AuthDefaultRequest} 相对应的适配器
     */
    private AuthDefaultRequest getAuthDefaultRequest(AuthConfig config,
                                                     AuthDefaultSource source,
                                                     AuthStateCache authStateCache) {

        Object[] arguments = new Object[]{config, authStateCache};

        final Class<?> authDefaultRequestClass = getAuthRequestClassBySource(source);

        if (!AuthDefaultRequest.class.isAssignableFrom(authDefaultRequestClass)) {
            throw new RuntimeException(authDefaultRequestClass.getName() + " Must be a subclass of me.zhyd.oauth.request.AuthDefaultRequest");
        }

        return (AuthDefaultRequest) ReflectUtil.newInstance(authDefaultRequestClass, arguments);

    }

    /**
     * {@link AuthDefaultRequest} 子类的包名
     */
    public static final String AUTH_REQUEST_PACKAGE = "me.zhyd.oauth.request.";
    /**
     * {@link AuthDefaultRequest} 子类类名前缀
     */
    public static final String AUTH_REQUEST_PREFIX = "Auth";
    /**
     * {@link AuthDefaultRequest} 子类类名后缀
     */
    public static final String AUTH_REQUEST_SUFFIX = "Request";
    /**
     * {@link AuthDefaultSource} 枚举名称分隔符
     */
    public static final String SEPARATOR = "_";

    /**
     * 根据 {@link AuthDefaultSource} 获取对应的 {@link AuthDefaultRequest} 子类的 Class
     * @param source    {@link AuthDefaultSource}
     * @return  返回 {@link AuthDefaultSource} 对应的 {@link AuthDefaultRequest} 子类的 Class
     */
    public static Class<?> getAuthRequestClassBySource(AuthDefaultSource source) {
        String[] splits = source.name().split(SEPARATOR);
        String authRequestClassName = AUTH_REQUEST_PACKAGE + toAuthRequestClassName(splits);
        try {
            return Class.forName(authRequestClassName);
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 根据传入的字符串数组转换为类名格式的字符串, 另外 DingTalk -> DingTalk, WECHAT -> WeChat.
     * @param splits    字符串数组, 例如: [WECHAT, OPEN]
     * @return  返回类名格式的字符串, 如传入的数组是: [STACK, OVERFLOW] 那么返回 AuthStackOverflowRequest
     */
    private static String toAuthRequestClassName(String[] splits) {
        StringBuilder sb = new StringBuilder();
        sb.append(AUTH_REQUEST_PREFIX);
        for (String split : splits) {
            split = split.toLowerCase();
            if (AuthDefaultSource.DINGTALK.name().equalsIgnoreCase(split)) {
                sb.append("DingTalk");
                continue;
            }
            if ("wechat".equalsIgnoreCase(split)) {
                sb.append("WeChat");
                continue;
            }
            if (split.length() > 1) {
                sb.append(split.substring(0, 1).toUpperCase()).append(split.substring(1));
            }
            else {
                sb.append(split.toUpperCase());
            }
        }
        sb.append(AUTH_REQUEST_SUFFIX);
        return sb.toString();
    }


    /**
     * 配置 http 相关的配置
     *
     * @param authSource {@link AuthSource}
     * @param authConfig {@link AuthConfig}
     */
    private void configureHttpConfig(String authSource, AuthConfig authConfig, JustAuthProperties.JustAuthHttpConfig httpConfig) {
        if (null == httpConfig) {
            return;
        }
        Map<String, JustAuthProperties.JustAuthProxyConfig> proxyConfigMap = httpConfig.getProxy();
        if (CollectionUtils.isEmpty(proxyConfigMap)) {
            return;
        }
        JustAuthProperties.JustAuthProxyConfig proxyConfig = proxyConfigMap.get(authSource);

        if (null == proxyConfig) {
            return;
        }

        authConfig.setHttpConfig(HttpConfig.builder()
            .timeout(httpConfig.getTimeout())
            .proxy(new Proxy(Proxy.Type.valueOf(proxyConfig.getType()), new InetSocketAddress(proxyConfig.getHostname(), proxyConfig.getPort())))
            .build());
    }
}
