package example.tech_merge.service;

import example.tech_merge.domain.Member;
import example.tech_merge.repository.MemberRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class MainService {
    @Value("${spring.data.redis.port}")
    private int port;

    @Value("${spring.data.redis.host}")
    private String host;

    private final MemberRedisRepository memberRedisRepository;

    public void save() {
        for (int i = 0; i < 20; i++) {
            String flag = new Random()
                    .ints(20, 40)
                    .limit(1)
                    .findAny().toString();

            Member member = new Member("kim" + flag, flag);

            try {
                memberRedisRepository.save(member);
            } catch (Exception e) {
                throw new IllegalStateException("fail");
            }
        }
    }
}
