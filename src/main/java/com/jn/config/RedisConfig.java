package com.jn.config;


import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.net.UnknownHostException;
import java.util.Arrays;


@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Bean("myKeyGenerator")
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                return method.getName() + "[" + Arrays.asList(objects).toString() + "]";
            }
        };
    }

    //Jackson2JsonRedisSerializer
    //如果使用Jackson2JsonRedisSerializer在反序列化时会遇到问题，因为没有具体泛型或泛型为Object时，
    // 会将缓存中的数据反序列化为LinkedHashMap，而假如我们需要的是User对象，因此就会抛出一个异常。
    //java.lang.ClassCastException: java.base/java.util.LinkedHashMap cannot be cast to com.springboot2.domain.User
  /*  @Bean(name = "jackson2JsonRedisSerializer")
    public Jackson2JsonRedisSerializer jackson2JsonRedisSerializer() {
        return new Jackson2JsonRedisSerializer(Object.class);
    }
    @Bean
    public RedisTemplate<Object, Object> empRedisTemplate(RedisConnectionFactory redisConnectionFactory,
                                                          Jackson2JsonRedisSerializer jackson2JsonRedisSerializer) throws UnknownHostException {
        RedisTemplate<Object, Object> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        //设置序列化方法
        template.setDefaultSerializer(jackson2JsonRedisSerializer);
        return template;
    }*/


    @Bean(name="genericJackson2JsonRedisSerializer")
    public GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

    @Bean(name = "myRedisTemplate")
    public RedisTemplate<Object, Object> myRedisTemplate(RedisConnectionFactory redisConnectionFactory,
                                                         GenericJackson2JsonRedisSerializer ser) throws UnknownHostException {
        RedisTemplate<Object, Object> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        //设置序列化方法
        template.setDefaultSerializer(ser);
        return template;
    }



   /*
     SpringBoot 1.X 中自定义序列化器通常是声明一个RedisCacheManager并在其构造中传一个RedisTemplate，
    接着对RedisTemplate配置自定义序列化器就可达到自定义序列化器的目的。
    @Bean
    public RedisCacheManager empCacheManager(RedisTemplate<Object, Employee> empRedisTemplate) {
        RedisCacheManager redisCacheManager = new RedisCacheManaegr(empRedisTemplate);
        //使用前缀，默认使用CacheName作为前缀
        redisCacheManager.setUserPrefix(true);
        return redisCacheManager;
    }
   */

    /*
       但是SpringBoot 2.X 中你会发现RedisCacheManager的构造方法完全变样了，不再是依赖RedisTemplate。
       GenericJackson2JsonRedisSerializer 可以对不同的JAVABEAN进行序列化和反序列化操作。通用性更强。
    */
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        return RedisCacheConfiguration
                .defaultCacheConfig()
                .serializeKeysWith(
                        RedisSerializationContext
                                .SerializationPair
                                .fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(
                        RedisSerializationContext
                                .SerializationPair
                                .fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }

    /*@Bean
    public RedisCacheManager mycacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
        RedisCacheManager redisCacheManager = RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .transactionAware()
                .build();
        return redisCacheManager;
    }*/

}