package de.springframework.monitoring.hystrix.producer.repository.cache;

import de.springframework.monitoring.hystrix.producer.entity.Person;
import de.springframework.monitoring.hystrix.producer.repository.CachePersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DefaultCachePersonRepository implements CachePersonRepository {

    private static final String KEY_PATTERN = "*";

    private final RedisTemplate<String, Person> redisTemplate;
    private ValueOperations<String, Person> opsForValue;

    @PostConstruct
    private void init() {
        opsForValue = redisTemplate.opsForValue();
    }

    @Override
    public void save(Person person) {
        opsForValue.set(person.getId().toString(), person);
    }

    @Override
    public Optional<Person> findById(String id) {
        return Optional.ofNullable(opsForValue.get(id));
    }

    @Override
    public boolean isEmpty() {
        return Optional.ofNullable(redisTemplate.keys(KEY_PATTERN))
                .map(keys -> keys.size() == 0)
                .orElse(true);
    }

    @Override
    public List<Person> findAll() {
        List<Person> persons = new ArrayList<>();

        Set<String> keys = redisTemplate.keys(KEY_PATTERN);

        if (keys != null) {
            keys.forEach(key ->
                    findById(key).ifPresent(persons::add));
        }

        return persons;
    }

    @Override
    public void delete(Person user) {
        opsForValue.getOperations().delete(user.getId().toString());
    }

}
