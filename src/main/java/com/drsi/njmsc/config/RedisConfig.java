package com.drsi.njmsc.config;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@ConditionalOnClass(RedisOperations.class)
public class RedisConfig {
   @Bean
   public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
      RedisTemplate<String, Object> template = new RedisTemplate();
      template.setConnectionFactory(redisConnectionFactory);
      Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
      ObjectMapper om = new ObjectMapper();
      om.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
      om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, DefaultTyping.NON_FINAL, As.PROPERTY);
      jackson2JsonRedisSerializer.setObjectMapper(om);
      StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
      template.setKeySerializer(stringRedisSerializer);
      template.setHashKeySerializer(stringRedisSerializer);
      template.setValueSerializer(jackson2JsonRedisSerializer);
      template.setHashValueSerializer(jackson2JsonRedisSerializer);
      template.afterPropertiesSet();
      return template;
   }
}
