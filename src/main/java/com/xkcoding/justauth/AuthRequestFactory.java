package com.xkcoding.justauth;

import com.xkcoding.justauth.properties.JustAuthProperties;
import lombok.RequiredArgsConstructor;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.config.AuthSource;
import me.zhyd.oauth.request.*;
import me.zhyd.oauth.utils.AuthState;

/**
 * <p>
 * AuthRequest工厂类
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-07-22 14:21
 */
@RequiredArgsConstructor
public class AuthRequestFactory {
    private final JustAuthProperties properties;

    /**
     * 返回AuthRequest对象
     *
     * @param source {@link AuthSource}
     * @return {@link AuthRequest}
     */
    public AuthRequest get(AuthSource source) {
        return get(source, null);
    }

    /**
     * 返回AuthRequest对象
     *
     * @param source {@link AuthSource}
     * @param state  {@link AuthSource}
     * @return {@link AuthRequest}
     */
    public AuthRequest get(AuthSource source, Object state) {
        AuthConfig config = properties.getType().get(source);
        config.setState(state == null ? AuthState.create(source) : AuthState.create(source, state));
        switch (source) {
            case GITHUB:
                return new AuthGithubRequest(config);
            case WEIBO:
                return new AuthWeiboRequest(config);
            case GITEE:
                return new AuthGiteeRequest(config);
            case DINGTALK:
                return new AuthDingTalkRequest(config);
            case BAIDU:
                return new AuthBaiduRequest(config);
            case CSDN:
                return new AuthCsdnRequest(config);
            case CODING:
                return new AuthCodingRequest(config);
            case TENCENT_CLOUD:
                return new AuthTencentCloudRequest(config);
            case OSCHINA:
                return new AuthOschinaRequest(config);
            case ALIPAY:
                return new AuthAlipayRequest(config);
            case QQ:
                return new AuthQqRequest(config);
            case WECHAT:
                return new AuthWeChatRequest(config);
            case TAOBAO:
                return new AuthTaobaoRequest(config);
            case GOOGLE:
                return new AuthGoogleRequest(config);
            case FACEBOOK:
                return new AuthFacebookRequest(config);
            case DOUYIN:
                return new AuthDouyinRequest(config);
            case LINKEDIN:
                return new AuthLinkedinRequest(config);
            case MICROSOFT:
                return new AuthMicrosoftRequest(config);
            case MI:
                return new AuthMiRequest(config);
            case TOUTIAO:
                return new AuthToutiaoRequest(config);
            case TEAMBITION:
                return new AuthTeambitionRequest(config);
            case RENREN:
                return new AuthRenrenRequest(config);
            case PINTEREST:
                return new AuthPinterestRequest(config);
            case STACK_OVERFLOW:
                return new AuthStackOverflowRequest(config);
            default:
                return null;
        }
    }
}
