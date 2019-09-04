# justauth-spring-boot-starter

> Spring Boot 集成 JustAuth 的最佳实践~
>
> JustAuth 脚手架

![Maven Central](https://img.shields.io/maven-central/v/com.xkcoding/justauth-spring-boot-starter.svg?color=brightgreen&label=Maven%20Central)
![Travis (.com)](https://img.shields.io/travis/com/xkcoding/justauth-spring-boot-starter.svg?label=Build%20Status)
![GitHub](https://img.shields.io/github/license/xkcoding/justauth-spring-boot-starter.svg)

## Demo

懒得看文档的，可以直接看demo

https://github.com/xkcoding/justauth-spring-boot-starter-demo

完整版 demo：https://github.com/xkcoding/spring-boot-demo/tree/master/spring-boot-demo-social

## 更新日志

[CHANGELOG](./CHANGELOG.md)

## 快速开始

### 1. 基础配置

- 引用依赖

```xml
<dependency>
  <groupId>com.xkcoding</groupId>
  <artifactId>justauth-spring-boot-starter</artifactId>
  <version>1.1.0</version>
</dependency>
```

- 添加配置，在 `application.yml` 中添加配置配置信息

```yaml
justauth:
  enabled: true
  type:
    QQ:
      client-id: 10**********6
      client-secret: 1f7d08**********5b7**********29e
      redirect-uri: http://oauth.xkcoding.com/demo/oauth/qq/callback
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
        AuthRequest authRequest = factory.get(getAuthSource(type));
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }

    @RequestMapping("/{type}/callback")
    public AuthResponse login(@PathVariable String type, AuthCallback callback) {
        AuthRequest authRequest = factory.get(getAuthSource(type));
        AuthResponse response = authRequest.login(callback);
        log.info("【response】= {}", JSONUtil.toJsonStr(response));
        return response;
    }

    private AuthSource getAuthSource(String type) {
        if (StrUtil.isNotBlank(type)) {
            return AuthSource.valueOf(type.toUpperCase());
        } else {
            throw new RuntimeException("不支持的类型");
        }
    }
}
```

### 2. 缓存配置

> starter 内置了2种缓存实现，一种是上面的默认实现，另一种是基于 Redis 的缓存实现。
>
> 当然了，你也可以自定义实现你自己的缓存。

#### 2.1. 默认缓存实现

在配置文件配置如下内容即可

```yaml
justauth:
  cache:
    type: default
```

#### 2.2. Redis 缓存实现

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

#### 2.3. 自定义缓存实现

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

## 附录

### 1. 基础配置

`justauth` 配置列表

| 属性名             | 类型                                                         | 默认值 | 可选项     | 描述              |
| ------------------ | ------------------------------------------------------------ | ------ | ---------- | ----------------- |
| `justauth.enabled` | `boolean`                                                    | true   | true/false | 是否启用 JustAuth |
| `justauth.type`    | `java.util.Map<me.zhyd.oauth.config.AuthSource,me.zhyd.oauth.config.AuthConfig>` | 无     |            | JustAuth 配置     |
| `justauth.cache`   | `com.xkcoding.justauth.properties.CacheProperties`           |        |            | JustAuth缓存配置  |

`justauth.type` 配置列表

| 属性名                      | 描述                                                         |
| --------------------------- | ------------------------------------------------------------ |
| `justauth.type.keys`        | `justauth.type` 是 `Map` 格式的，key 的取值请参考 [`AuthSource`](https://github.com/zhangyd-c/JustAuth/blob/master/src/main/java/me/zhyd/oauth/config/AuthSource.java) |
| `justauth.type.keys.values` | `justauth.type` 是 `Map` 格式的，value 的取值请参考 [`AuthConfig`](https://github.com/zhangyd-c/JustAuth/blob/master/src/main/java/me/zhyd/oauth/config/AuthConfig.java) |

`justauth.cache` 配置列表

| 属性名                   | 类型                                                         | 默认值            | 可选项               | 描述                                                         |
| ------------------------ | ------------------------------------------------------------ | ----------------- | -------------------- | ------------------------------------------------------------ |
| `justauth.cache.type`    | `com.xkcoding.justauth.properties.CacheProperties.CacheType` | default           | default/redis/custom | 缓存类型，default使用JustAuth默认的缓存实现，redis使用默认的redis缓存实现，custom用户自定义缓存实现 |
| `justauth.cache.prefix`  | `string`                                                     | JUSTAUTH::STATE:: |                      | 缓存前缀，目前只对redis缓存生效，默认 JUSTAUTH::STATE::      |
| `justauth.cache.timeout` | `java.time.Duration`                                         | 3分钟             |                      | 超时时长，目前只对redis缓存生效，默认3分钟                   |

### 2. SNAPSHOT版本

如果需要体验快照版本，可以在你的maven目录找到 `settings.xml`进行如下配置：

```xml
<profiles>
    <profile>
        <id>justauth-test</id>
        <repositories>
            <!--阿里云私服-->
            <repository>
                <id>aliyun</id>
                <name>aliyun</name>
                <url>http://maven.aliyun.com/nexus/content/groups/public</url>
            </repository>
            <!--xkcoding 私服-->
            <repository>
                <id>xkcoding-nexus</id>
                <name>xkcoding nexus</name>
                <url>https://nexus.xkcoding.com/repository/maven-public/</url>
                <releases>
                    <enabled>true</enabled>
                </releases>
                <snapshots>
                    <enabled>true</enabled>
                </snapshots>
            </repository>
        </repositories>
    </profile>
</profiles>
<activeProfiles>
    <activeProfile>justauth-test</activeProfile>
</activeProfiles>
```

