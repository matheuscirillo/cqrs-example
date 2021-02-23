package br.com.matheuscirillo.cqrs.example.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TokenRepository {

    @Autowired
    private StringRedisTemplate template;

    public void store(Integer id, String token) {
	template.opsForValue().set(String.valueOf(id), token);
    }

    public void remove(Integer id) {
	template.opsForValue().getOperations().delete(String.valueOf(id));
    }

}
