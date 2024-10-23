package com.comunicamosmas.api.config;

import java.time.Duration;
import java.util.Arrays;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration; 
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate; 
import org.springframework.data.redis.serializer.RedisSerializationContext; 
 

@Configuration
@EnableCaching
public class CacheConfig {
    
    @Bean
    public RedisConnectionFactory redisConnectionFactory()
    {
        return new LettuceConnectionFactory();
    }

    

    @Bean
    public CacheManager cacheManager(RedisTemplate<String ,Object> redisTemplate){
        
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.builder(redisTemplate.getConnectionFactory());
 
        builder.cacheDefaults(redisCacheConfiguration(redisTemplate));

        return builder.build();
    }

    private RedisCacheConfiguration redisCacheConfiguration(RedisTemplate<String, Object> redisTemplate){
        
        return RedisCacheConfiguration.defaultCacheConfig()
                    .entryTtl(Duration.ofMinutes(60))
                    .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getValueSerializer()));
    }

    @Bean
    public KeyGenerator keyGenerator(){
        return (target , method , params) -> {
            return method.getName() + Arrays.toString(params);
        };
    }
     

   
}
