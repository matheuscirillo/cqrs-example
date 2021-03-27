package br.com.matheuscirillo.cqrs.example.infrastructure.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TokenRepository {

    @Autowired
    private StringRedisTemplate template;

    public String get(Integer id) {
        return template.opsForValue().get(String.valueOf(id));
    }

}