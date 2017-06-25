package pl.xsolve.redisfilterdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@Configuration
public class AppConfiguration {

    @Bean
    public ValueOperations<String, String> valueOperations(final RedisTemplate<String, String> redisTemplate) {
        return redisTemplate.opsForValue();
    }
}
