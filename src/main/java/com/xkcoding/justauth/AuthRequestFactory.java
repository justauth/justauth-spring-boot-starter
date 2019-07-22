package com.xkcoding.justauth;

import com.xkcoding.justauth.properties.JustAuthProperties;
import lombok.RequiredArgsConstructor;
import me.zhyd.oauth.config.AuthSource;
import me.zhyd.oauth.request.*;

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
        switch (source) {
            case GITHUB:
                return new AuthGithubRequest(properties.getType().get(source));
            case WEIBO:
                return new AuthWeiboRequest(properties.getType().get(source));
            case GITEE:
                return new AuthGiteeRequest(properties.getType().get(source));
            case DINGTALK:
                return new AuthDingTalkRequest(properties.getType().get(source));
            case BAIDU:
                return new AuthBaiduRequest(properties.getType().get(source));
            case CSDN:
                return new AuthCsdnRequest(properties.getType().get(source));
            case CODING:
                return new AuthCodingRequest(properties.getType().get(source));
            case TENCENT_CLOUD:
                return new AuthTencentCloudRequest(properties.getType().get(source));
            case OSCHINA:
                return new AuthOschinaRequest(properties.getType().get(source));
            case ALIPAY:
                return new AuthAlipayRequest(properties.getType().get(source));
            case QQ:
                return new AuthQqRequest(properties.getType().get(source));
            case WECHAT:
                return new AuthWeChatRequest(properties.getType().get(source));
            case TAOBAO:
                return new AuthTaobaoRequest(properties.getType().get(source));
            case GOOGLE:
                return new AuthGoogleRequest(properties.getType().get(source));
            case FACEBOOK:
                return new AuthFacebookRequest(properties.getType().get(source));
            case DOUYIN:
                return new AuthDouyinRequest(properties.getType().get(source));
            case LINKEDIN:
                return new AuthLinkedinRequest(properties.getType().get(source));
            case MICROSOFT:
                return new AuthMicrosoftRequest(properties.getType().get(source));
            case MI:
                return new AuthMiRequest(properties.getType().get(source));
            case TOUTIAO:
                return new AuthToutiaoRequest(properties.getType().get(source));
            case TEAMBITION:
                return new AuthTeambitionRequest(properties.getType().get(source));
            case RENREN:
                return new AuthRenrenRequest(properties.getType().get(source));
            case PINTEREST:
                return new AuthPinterestRequest(properties.getType().get(source));
            case STACK_OVERFLOW:
                return new AuthStackOverflowRequest(properties.getType().get(source));
            default:
                return null;
        }
    }
}
