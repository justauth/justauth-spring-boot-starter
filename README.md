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

## 快速开始

- 引用依赖

```xml
<dependency>
  <groupId>com.xkcoding</groupId>
  <artifactId>justauth-spring-boot-starter</artifactId>
  <version>1.0.0</version>
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

- 如果需要自定义 State 的缓存(此处举例 Redis，其余缓存同理)

  > 1. 自定义缓存实现 `AuthStateCache` 接口
  > 2. 将自定义缓存加入 Spring 容器

  1.添加Redis依赖

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

  2.自定义缓存 `RedisStateCache`

  ```java
  /**
   * <p>
   * Redis作为JustAuth的State的缓存
   * </p>
   *
   * @author yangkai.shen
   * @date Created in 2019-08-02 15:10
   */
  @RequiredArgsConstructor
  public class RedisStateCache implements AuthStateCache {
      private final RedisTemplate<String, String> redisTemplate;
      private static final long DEF_TIMEOUT = 3 * 60 * 1000;
  
      /**
       * 存入缓存
       *
       * @param key   缓存key
       * @param value 缓存内容
       */
      @Override
      public void cache(String key, String value) {
          this.cache(key, value, DEF_TIMEOUT);
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
          redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MILLISECONDS);
      }
  
      /**
       * 获取缓存内容
       *
       * @param key 缓存key
       * @return 缓存内容
       */
      @Override
      public String get(String key) {
          return redisTemplate.opsForValue().get(key);
      }
  
      /**
       * 是否存在key，如果对应key的value值已过期，也返回false
       *
       * @param key 缓存key
       * @return true：存在key，并且value没过期；false：key不存在或者已过期
       */
      @Override
      public boolean containsKey(String key) {
          Long expire = redisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
          if (expire == null) {
              expire = 0L;
          }
          return expire > 0;
      }
  }
  ```

  3.自动装配 `JustAuthConfig`

  ```java
  /**
   * <p>
   * JustAuth配置类
   * </p>
   *
   * @author yangkai.shen
   * @date Created in 2019-08-02 15:08
   */
  @Configuration
  public class JustAuthConfig {
      /**
       * 默认情况下的模板只能支持RedisTemplate<String, String>，也就是只能存入字符串，因此支持序列化
       */
      @Bean
      public RedisTemplate<String, Serializable> redisCacheTemplate(LettuceConnectionFactory redisConnectionFactory) {
          RedisTemplate<String, Serializable> template = new RedisTemplate<>();
          template.setKeySerializer(new StringRedisSerializer());
          template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
          template.setConnectionFactory(redisConnectionFactory);
          return template;
      }
  
      @Bean
      public AuthStateCache authStateCache(RedisTemplate<String,String> redisCacheTemplate) {
          return new RedisStateCache(redisCacheTemplate);
      }
  
  }
  ```

## 附录

`justauth` 配置列表

| 属性名             | 类型                                                         | 默认值 | 可选项     | 描述              |
| ------------------ | ------------------------------------------------------------ | ------ | ---------- | ----------------- |
| `justauth.enabled` | `boolean`                                                    | true   | true/false | 是否启用 JustAuth |
| `justauth.type`    | `java.util.Map<me.zhyd.oauth.config.AuthSource,me.zhyd.oauth.config.AuthConfig>` | 无     |            | JustAuth 配置     |

`justauth.type` 配置列表

| 属性名                      | 描述                                                         |
| --------------------------- | ------------------------------------------------------------ |
| `justauth.type.keys`        | `justauth.type` 是 `Map` 格式的，key 的取值请参考 [`AuthSource`](https://github.com/zhangyd-c/JustAuth/blob/master/src/main/java/me/zhyd/oauth/config/AuthSource.java) |
| `justauth.type.keys.values` | `justauth.type` 是 `Map` 格式的，value 的取值请参考 [`AuthConfig`](https://github.com/zhangyd-c/JustAuth/blob/master/src/main/java/me/zhyd/oauth/config/AuthConfig.java) |

