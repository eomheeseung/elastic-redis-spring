package example.tech_merge.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Service
public class ServiceTemplate implements InterfaceService{
    private final StringRedisTemplate redisTemplate;

    @Transactional
    public void save(Map<String, String> map) {
        ValueOperations<String, String> stringObjectValueOperations = redisTemplate.opsForValue();

        try {
            map.keySet().stream().forEach(k -> stringObjectValueOperations.set(k, map.get(k)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String findMember(String target) {
        ValueOperations<String, String> stringObjectValueOperations = redisTemplate.opsForValue();
        Object o = stringObjectValueOperations.get(target);

        if (o == null) {
            return "fail";
        } else {
            return o.toString();
        }
    }
}
