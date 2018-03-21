package springMvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by hyeondeok on 2018. 3. 20..
 */
@Configuration
@EnableCaching
@ComponentScan(basePackages = {"springMvc.redis"})
@PropertySource({"classpath:/config/redis.properties"})
public class RedisConfig {

    @Autowired
    Environment env;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
        jedisConFactory.setHostName(env.getProperty("redis.hostname"));
        jedisConFactory.setPort(Integer.parseInt(env.getProperty("redis.port")));
        return jedisConFactory;
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(){
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());

        // 한글깨짐 방지
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericToStringSerializer<Object> (Object.class));
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericToStringSerializer<Object> (Object.class));

        //template.setValueSerializer(new Jackson2JsonRedisSerializer<Member>(Member.class));

        return template;
    }

    @Bean(name = "cacheManager")
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        cacheManager.setDefaultExpiration(10);  // 단위 : 초, 기본값 : 0 (무제한)
        return cacheManager;
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(JedisConnectionFactory jedisConnectionFactory){
        final RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();

        redisMessageListenerContainer.setConnectionFactory((jedisConnectionFactory));

        return redisMessageListenerContainer;
    }
}
