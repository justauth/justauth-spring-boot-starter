# justauth-spring-boot-starter

> Spring Boot 集成 JustAuth 的最佳实践~
>
> JustAuth 脚手架

![Maven Central](https://img.shields.io/maven-central/v/com.xkcoding.justauth/justauth-spring-boot-starter.svg?color=brightgreen&label=Maven%20Central)![Travis (.com)](https://img.shields.io/travis/com/xkcoding/justauth-spring-boot-starter.svg?label=Build%20Status)![GitHub](https://img.shields.io/github/license/xkcoding/justauth-spring-boot-starter.svg)

## 1. Demo

懒得看文档的，可以直接看demo

https://github.com/xkcoding/justauth-spring-boot-starter-demo

完整版 demo：https://github.com/xkcoding/spring-boot-demo/tree/master/spring-boot-demo-social

## 2. 更新日志

[CHANGELOG](./CHANGELOG.md)

## 3. 快速开始

### 3.1. 基础配置

- 引用依赖

```xml
<dependency>
  <groupId>com.xkcoding.justauth</groupId>
  <artifactId>justauth-spring-boot-starter</artifactId>
  <version>1.3.4.beta</version>
</dependency>
```

- 添加配置，在 `application.yml` 中添加配置配置信息

注意：

- `justauth.type`节点的配置，请根据项目实际情况选择，多余的可以删除
- 如果使用 QQ 登录，并且需要获取`unionId`，则必须传`union-id`配置，并置为`true`
- 如果使用支付宝登录，必传`alipay-public-key`
- 如果使用 Stack Overflow 登录，必传`stack-overflow-key`
- 如果使用企业微信登录，必传`agent-id`
- 如果使用 CODING 登录，必传`coding-group-name`

```yaml
justauth:
  enabled: true
  type:
    QQ:
      client-id: 10**********6
      client-secret: 1f7d08**********5b7**********29e
      redirect-uri: http://oauth.xkcoding.com/demo/oauth/qq/callback
      union-id: false
    WEIBO:
      client-id: 10**********6
      client-secret: 1f7d08**********5b7**********29e
      redirect-uri: http://oauth.xkcoding.com/demo/oauth/weibo/callback
    GITEE:
      client-id: 10**********6
      client-secret: 1f7d08**********5b7**********29e
      redirect-uri: http://oauth.xkcoding.com/demo/oauth/gitee/callback
    DINGTALK:
      client-id: 10**********6
      client-secret: 1f7d08**********5b7**********29e
      redirect-uri: http://oauth.xkcoding.com/demo/oauth/dingtalk/callback
    BAIDU:
      client-id: 10**********6
      client-secret: 1f7d08**********5b7**********29e
      redirect-uri: http://oauth.xkcoding.com/demo/oauth/baidu/callback
    CSDN:
      client-id: 10**********6
      client-secret: 1f7d08**********5b7**********29e
      redirect-uri: http://oauth.xkcoding.com/demo/oauth/csdn/callback
    CODING:
      client-id: 10**********6
      client-secret: 1f7d08**********5b7**********29e
      redirect-uri: http://oauth.xkcoding.com/demo/oauth/coding/callback
      coding-group-name: xx
    OSCHINA:
      client-id: 10**********6
      client-secret: 1f7d08**********5b7**********29e
      redirect-uri: http://oauth.xkcoding.com/demo/oauth/oschina/callback
    ALIPAY:
      client-id: 10**********6
      client-secret: 1f7d08**********5b7**********29e
      redirect-uri: http://oauth.xkcoding.com/demo/oauth/alipay/callback
      alipay-public-key: MIIB**************DAQAB
    WECHAT_OPEN:
      client-id: 10**********6
      client-secret: 1f7d08**********5b7**********29e
      redirect-uri: http://oauth.xkcoding.com/demo/oauth/wechat_open/callback
    WECHAT_MP:
      client-id: 10**********6
      client-secret: 1f7d08**********5b7**********29e
      redirect-uri: http://oauth.xkcoding.com/demo/oauth/wechat_mp/callback
    WECHAT_ENTERPRISE:
      client-id: 10**********6
      client-secret: 1f7d08**********5b7**********29e
      redirect-uri: http://oauth.xkcoding.com/demo/oauth/wechat_enterprise/callback
      agent-id: 1000002
    TAOBAO:
      client-id: 10**********6
      client-secret: 1f7d08**********5b7**********29e
      redirect-uri: http://oauth.xkcoding.com/demo/oauth/taobao/callback
    GOOGLE:
      client-id: 10**********6
      client-secret: 1f7d08**********5b7**********29e
      redirect-uri: http://oauth.xkcoding.com/demo/oauth/google/callback
    FACEBOOK:
      client-id: 10**********6
      client-secret: 1f7d08**********5b7**********29e
      redirect-uri: http://oauth.xkcoding.com/demo/oauth/facebook/callback
    DOUYIN:
      client-id: 10**********6
      client-secret: 1f7d08**********5b7**********29e
      redirect-uri: http://oauth.xkcoding.com/demo/oauth/douyin/callback
    LINKEDIN:
      client-id: 10**********6
      client-secret: 1f7d08**********5b7**********29e
      redirect-uri: http://oauth.xkcoding.com/demo/oauth/linkedin/callback
    MICROSOFT:
      client-id: 10**********6
      client-secret: 1f7d08**********5b7**********29e
      redirect-uri: http://oauth.xkcoding.com/demo/oauth/microsoft/callback
    MI:
      client-id: 10**********6
      client-secret: 1f7d08**********5b7**********29e
      redirect-uri: http://oauth.xkcoding.com/demo/oauth/mi/callback
    TOUTIAO:
      client-id: 10**********6
      client-secret: 1f7d08**********5b7**********29e
      redirect-uri: http://oauth.xkcoding.com/demo/oauth/toutiao/callback
    TEAMBITION:
      client-id: 10**********6
      client-secret: 1f7d08**********5b7**********29e
      redirect-uri: http://oauth.xkcoding.com/demo/oauth/teambition/callback
    RENREN:
      client-id: 10**********6
      client-secret: 1f7d08**********5b7**********29e
      redirect-uri: http://oauth.xkcoding.com/demo/oauth/renren/callback
    PINTEREST:
      client-id: 10**********6
      client-secret: 1f7d08**********5b7**********29e
      redirect-uri: http://oauth.xkcoding.com/demo/oauth/pinterest/callback
    STACK_OVERFLOW:
      client-id: 10**********6
      client-secret: 1f7d08**********5b7**********29e
      redirect-uri: http://oauth.xkcoding.com/demo/oauth/stack_overflow/callback
      stack-overflow-key: asd*********asd
    HUAWEI:
      client-id: 10**********6
      client-secret: 1f7d08**********5b7**********29e
      redirect-uri: http://oauth.xkcoding.com/demo/oauth/huawei/callback
    KUJIALE:
      client-id: 10**********6
      client-secret: 1f7d08**********5b7**********29e
      redirect-uri: http://oauth.xkcoding.com/demo/oauth/kujiale/callback
    GITLAB:
      client-id: 10**********6
      client-secret: 1f7d08**********5b7**********29e
      redirect-uri: http://oauth.xkcoding.com/demo/oauth/gitlab/callback
    MEITUAN:
      client-id: 10**********6
      client-secret: 1f7d08**********5b7**********29e
      redirect-uri: http://oauth.xkcoding.com/demo/oauth/meituan/callback
    ELEME:
      client-id: 10**********6
      client-secret: 1f7d08**********5b7**********29e
      redirect-uri: http://oauth.xkcoding.com/demo/oauth/eleme/callback
    TWITTER:
      client-id: 10**********6
      client-secret: 1f7d08**********5b7**********29e
      redirect-uri: http://oauth.xkcoding.com/demo/oauth/twitter/callback
  cache:
    type: default
```



- 然后就开始玩耍吧~

```java
@Slf4j
@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TestController {
    private final AuthRequestFactory factory;

    @GetMapping
    public List<String> list() {
        return factory.oauthList();
    }

    @GetMapping("/login/{type}")
    public void login(@PathVariable String type, HttpServletResponse response) throws IOException {
        AuthRequest authRequest = factory.get(type);
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }

    @RequestMapping("/{type}/callback")
    public AuthResponse login(@PathVariable String type, AuthCallback callback) {
        AuthRequest authRequest = factory.get(type);
        AuthResponse response = authRequest.login(callback);
        log.info("【response】= {}", JSONUtil.toJsonStr(response));
        return response;
    }

}
```

### 3.2. 缓存配置

> starter 内置了2种缓存实现，一种是上面的默认实现，另一种是基于 Redis 的缓存实现。
>
> 当然了，你也可以自定义实现你自己的缓存。

#### 3.2.1. 默认缓存实现

在配置文件配置如下内容即可

```yaml
justauth:
  cache:
    type: default
```

#### 3.2.2. Redis 缓存实现

1.添加 Redis 相关依赖

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>

<!-- 对象池，使用redis时必须引入 -->
<dependency>
  <groupId>org.apache.commons</groupId>
  <artifactId>commons-pool2</artifactId>
</dependency>
```

2.配置文件配置如下内容即可

```yaml
justauth:
  cache:
    type: redis
    # 缓存前缀，目前只对redis缓存生效，默认 JUSTAUTH::STATE::
    prefix: ''
    # 超时时长，目前只对redis缓存生效，默认3分钟
    timeout: 1h
spring:
  redis:
    host: localhost
    # 连接超时时间（记得添加单位，Duration）
    timeout: 10000ms
    # Redis默认情况下有16个分片，这里配置具体使用的分片
    # database: 0
    lettuce:
      pool:
        # 连接池最大连接数（使用负值表示没有限制） 默认 8
        max-active: 8
        # 连接池最大阻塞等待时间（使用负值表示没有限制） 默认 -1
        max-wait: -1ms
        # 连接池中的最大空闲连接 默认 8
        max-idle: 8
        # 连接池中的最小空闲连接 默认 0
        min-idle: 0
```

#### 3.2.3. 自定义缓存实现

1.配置文件配置如下内容

```yaml
justauth:
  cache:
    type: custom
```

2.自定义缓存实现 `AuthStateCache` 接口

```java
/**
 * <p>
 * 自定义缓存实现
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019/8/31 12:53
 */
public class MyAuthStateCache implements AuthStateCache {
    /**
     * 存入缓存
     *
     * @param key   缓存key
     * @param value 缓存内容
     */
    @Override
    public void cache(String key, String value) {
        // TODO: 自定义存入缓存
    }

    /**
     * 存入缓存
     *
     * @param key     缓存key
     * @param value   缓存内容
     * @param timeout 指定缓存过期时间（毫秒）
     */
    @Override
    public void cache(String key, String value, long timeout) {
        // TODO: 自定义存入缓存
    }

    /**
     * 获取缓存内容
     *
     * @param key 缓存key
     * @return 缓存内容
     */
    @Override
    public String get(String key) {
        // TODO: 自定义获取缓存内容
        return null;
    }

    /**
     * 是否存在key，如果对应key的value值已过期，也返回false
     *
     * @param key 缓存key
     * @return true：存在key，并且value没过期；false：key不存在或者已过期
     */
    @Override
    public boolean containsKey(String key) {
        // TODO: 自定义判断key是否存在
        return false;
    }
}
```

3.自动装配 `JustAuthConfig`

```java
/**
 * <p>
 * 自定义缓存装配
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019/8/31 12:29
 */
@Configuration
public class AuthStateConfiguration {
    @Bean
    public AuthStateCache authStateCache() {
        return new MyAuthStateCache();
    }
}
```

### 3.3. 自定义第三方平台配置

1.创建自定义的平台枚举类

```java
/**
 * <p>
 * 扩展的自定义 source
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019/10/9 14:14
 */
public enum ExtendSource implements AuthSource {

    /**
     * 测试
     */
    TEST {
        /**
         * 授权的api
         *
         * @return url
         */
        @Override
        public String authorize() {
            return "http://authorize";
        }

        /**
         * 获取accessToken的api
         *
         * @return url
         */
        @Override
        public String accessToken() {
            return "http://accessToken";
        }

        /**
         * 获取用户信息的api
         *
         * @return url
         */
        @Override
        public String userInfo() {
            return null;
        }

        /**
         * 取消授权的api
         *
         * @return url
         */
        @Override
        public String revoke() {
            return null;
        }

        /**
         * 刷新授权的api
         *
         * @return url
         */
        @Override
        public String refresh() {
            return null;
        }
    }
}
```

2.创建自定义的请求处理

```java
/**
 * <p>
 * 测试用自定义扩展的第三方request
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019/10/9 14:19
 */
public class ExtendTestRequest extends AuthDefaultRequest {

    public ExtendTestRequest(AuthConfig config) {
        super(config, ExtendSource.TEST);
    }

    public ExtendTestRequest(AuthConfig config, AuthStateCache authStateCache) {
        super(config, ExtendSource.TEST, authStateCache);
    }

    /**
     * 获取access token
     *
     * @param authCallback 授权成功后的回调参数
     * @return token
     * @see AuthDefaultRequest#authorize()
     * @see AuthDefaultRequest#authorize(String)
     */
    @Override
    protected AuthToken getAccessToken(AuthCallback authCallback) {
        return AuthToken.builder().openId("openId").expireIn(1000).idToken("idToken").scope("scope").refreshToken("refreshToken").accessToken("accessToken").code("code").build();
    }

    /**
     * 使用token换取用户信息
     *
     * @param authToken token信息
     * @return 用户信息
     * @see AuthDefaultRequest#getAccessToken(AuthCallback)
     */
    @Override
    protected AuthUser getUserInfo(AuthToken authToken) {
        return AuthUser.builder().username("test").nickname("test").gender(AuthUserGender.MALE).token(authToken).source(this.source.toString()).build();
    }

    /**
     * 撤销授权
     *
     * @param authToken 登录成功后返回的Token信息
     * @return AuthResponse
     */
    @Override
    public AuthResponse revoke(AuthToken authToken) {
        return AuthResponse.builder().code(AuthResponseStatus.SUCCESS.getCode()).msg(AuthResponseStatus.SUCCESS.getMsg()).build();
    }

    /**
     * 刷新access token （续期）
     *
     * @param authToken 登录成功后返回的Token信息
     * @return AuthResponse
     */
    @Override
    public AuthResponse refresh(AuthToken authToken) {
        return AuthResponse.builder().code(AuthResponseStatus.SUCCESS.getCode()).data(AuthToken.builder().openId("openId").expireIn(1000).idToken("idToken").scope("scope").refreshToken("refreshToken").accessToken("accessToken").code("code").build()).build();
    }
}
```

3.在配置文件配置相关信息

```yaml
justauth:
  enabled: true
  extend:
    enum-class: com.xkcoding.justauthspringbootstarterdemo.extend.ExtendSource
    config:
      TEST:
        request-class: com.xkcoding.justauthspringbootstarterdemo.extend.ExtendTestRequest
        client-id: xxxxxx
        client-secret: xxxxxxxx
        redirect-uri: http://oauth.xkcoding.com/demo/oauth/test/callback
```

## 4. http 代理配置

修改配置文件，增加如下配置：

```yaml
justauth:
  http-config:
    timeout: 30000
    proxy:
      GOOGLE:
        type: HTTP
        hostname: 127.0.0.1
        port: 10080
```

注：当项目中使用了自定义的第三方登录，并且需要使用代理时，也要在 `http-config` 节点下添加相关配置，格式参考上面示例

## 5. 自定义 Scopes

修改配置文件，增加如下配置：

```yml
justauth:
  enabled: true
  type:
    QQ:
      client-id: 10**********6
      client-secret: 1f7d08**********5b7**********29e
      redirect-uri: http://oauth.xkcoding.com/demo/oauth/qq/callback
      union-id: false
      scopes:
       - get_user_info
       - xxxx
```

注：你可以前往 `me.zhyd.oauth.enums.scope` 包下查看各个渠道所支持的 scopes，当然你可以不配置该项，JustAuth 会默认添加上一些基础 scope

## 6. 附录

### 6.1. `justauth` 配置列表

| 属性名             | 类型                                                         | 默认值 | 可选项     | 描述                   |
| ------------------ | ------------------------------------------------------------ | ------ | ---------- | ---------------------- |
| `justauth.enabled` | `boolean`                                                    | true   | true/false | 是否启用 JustAuth      |
| `justauth.type`    | `java.util.Map<me.zhyd.oauth.config.AuthSource,me.zhyd.oauth.config.AuthConfig>` | 无     |            | JustAuth 配置          |
| `justauth.httpConfig`    | `java.util.Map<me.zhyd.oauth.config.AuthSource,com.xkcoding.justauth.autoconfigure.JustAuthProperties.JustAuthHttpConfig>` | 无     |            | http 相关配置          |
| `justauth.cache`   | `com.xkcoding.justauth.autoconfigure.CacheProperties`        |        |            | JustAuth缓存配置       |
| `justauth.extend`  | `com.xkcoding.justauth.autoconfigure.ExtendProperties`       | 无     |            | JustAuth第三方平台配置 |

#### `justauth.type` 配置列表

| 属性名                      | 描述                                                         |
| --------------------------- | ------------------------------------------------------------ |
| `justauth.type.keys`        | `justauth.type` 是 `Map` 格式的，key 的取值请参考 [`AuthDefaultSource`](https://github.com/zhangyd-c/JustAuth/blob/master/src/main/java/me/zhyd/oauth/config/AuthDefaultSource.java) |
| `justauth.type.keys.values` | `justauth.type` 是 `Map` 格式的，value 的取值请参考 [`AuthConfig`](https://github.com/zhangyd-c/JustAuth/blob/master/src/main/java/me/zhyd/oauth/config/AuthConfig.java) |

##### `justauth.type.keys.values` 所有可选配置如下：

| 属性名                      | 描述                                                         | 备注                             |
| --------------------------- | -----------------------------------------------------------  | ------------------------------- |
| `client-id`        |  客户端id，对应各平台的appKey  | **必填** |
| `client-secret` |  客户端Secret，对应各平台的appSecret  | **必填** |
| `redirect-uri` |  登录成功后的回调地址  | **必填** |
| `alipay-public-key` |  支付宝公钥  | 当使用支付宝登录时， **该值必填**，对应“RSA2(SHA256)密钥”中的“支付宝公钥” |
| `union-id` |  是否需要申请unionid  | 当使用QQ登录时，该值**选填**，如果置为`true`则qq开发者应用必须具备相应权限，参考链接：[查看详情](http://wiki.connect.qq.com/unionid%E4%BB%8B%E7%BB%8D) |
| `stack-overflow-key` |  Stack Overflow Key  | 当使用Stack Overflow登录时， **该值必填** |
| `agent-id` |  企业微信，授权方的网页应用ID  | 当使用企业微信登录时， **该值必填** |
| `coding-group-name` |  团队域名前缀 | 使用 Coding 登录时， **该值必填** |

#### `justauth.httpConfig` 配置列表

| 属性名                      | 描述                                                         |
| --------------------------- | ------------------------------------------------------------ |
| `justauth.httpConfig.keys`        | `justauth.type` 是 `Map` 格式的，key 的取值请参考 [`AuthDefaultSource`](https://github.com/zhangyd-c/JustAuth/blob/master/src/main/java/me/zhyd/oauth/config/AuthDefaultSource.java) |
| `justauth.httpConfig.keys.values` | `justauth.type` 是 `Map` 格式的，value 的取值请参考 `JustAuthProperties.JustAuthHttpConfig` |

##### `justauth.httpConfig.keys.values` 所有可选配置如下：

| 属性名                      | 描述                                                         | 备注                             |
| --------------------------- | -----------------------------------------------------------  | ------------------------------- |
| `timeout`        |  请求超时时间  |  |
| `proxy` |  代理的相关配置，针对国外平台，需要配置代理  | **必填** |

##### `justauth.httpConfig.proxy` 所有可选配置如下：

| 属性名                      | 描述                                                         | 备注                             |
| --------------------------- | -----------------------------------------------------------  | ------------------------------- |
| `type`        |  代理类型，可选值：`HTTP`、`DIRECT`、`SOCKS`，默认为 `HTTP`  |  |
| `hostname` |  代理 IP 地址  |  |
| `port` |  代理端口  |  |

#### `justauth.cache` 配置列表

| 属性名                   | 类型                                                         | 默认值            | 可选项               | 描述                                                         |
| ------------------------ | ------------------------------------------------------------ | ----------------- | -------------------- | ------------------------------------------------------------ |
| `justauth.cache.type`    | `com.xkcoding.justauth.autoconfigure.CacheProperties.CacheType` | default           | default/redis/custom | 缓存类型，default使用JustAuth默认的缓存实现，redis使用默认的redis缓存实现，custom用户自定义缓存实现 |
| `justauth.cache.prefix`  | `java.lang.String`                                           | JUSTAUTH::STATE:: |                      | 缓存前缀，目前只对redis缓存生效，默认 `JUSTAUTH::STATE::`    |
| `justauth.cache.timeout` | `java.time.Duration`                                         | 3分钟             |                      | 超时时长，目前只对redis缓存生效，默认`3分钟`                 |

#### `justauth.extend` 配置列表

| 属性名                       | 类型                                         | 默认值 | 可选项 | 描述         |
| ---------------------------- | -------------------------------------------- | ------ | ------ | ------------ |
| `justauth.extend.enum-class` | `Class<? extends AuthSource>`                | 无     |        | 枚举类全路径 |
| `justauth.extend.config`     | `java.util.Map<String, ExtendRequestConfig>` | 无     |        | 对应配置信息 |

##### `justauth.extend.config` 配置列表

| 属性名                          | 类型                                                         | 默认值 | 可选项 | 描述                                                         |
| ------------------------------- | ------------------------------------------------------------ | ------ | ------ | ------------------------------------------------------------ |
| `justauth.extend.config.keys`   | `java.lang.String`                                           | 无     |        | key 必须在 `justauth.extend.enum-class` 配置的枚举类中声明   |
| `justauth.extend.config.values` | `com.xkcoding.justauth.autoconfigure.ExtendProperties.ExtendRequestConfig` | 无     |        | value 就是 `AuthConfig` 的子类，增加了一个 `request-class` 属性配置请求的全类名，具体参考类[`ExtendProperties.ExtendRequestConfig`](https://github.com/justauth/justauth-spring-boot-starter/blob/master/src/main/java/com/xkcoding/justauth/autoconfigure/ExtendProperties.java#L49-L54) |

### 6.2. SNAPSHOT版本

![https://img.shields.io/badge/snapshots-1.4.0--SNAPSHOT-green](https://img.shields.io/badge/snapshots-1.4.0--SNAPSHOT-green)如果需要体验快照版本，可以在你的 `pom.xml`进行如下配置：

```xml
<repositories>
    <!--阿里云私服-->
    <repository>
      <id>aliyun</id>
      <name>aliyun</name>
      <url>http://maven.aliyun.com/nexus/content/groups/public</url>
    </repository>
    <!--中央仓库-->
    <repository>
      <id>oss</id>
      <name>oss</name>
      <url>http://oss.sonatype.org/content/repositories/snapshots</url>
      <releases>
        <enabled>true</enabled>
      </releases>
      <snapshots>
        <enabled>true</enabled>
      </snapshots>
    </repository>
</repositories>
```

