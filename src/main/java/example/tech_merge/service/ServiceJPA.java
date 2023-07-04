package example.tech_merge.service;

import example.tech_merge.RedisConfig;
import example.tech_merge.domain.Member;
import example.tech_merge.repository.MemberRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceJPA implements InterfaceService{
    @Value("${spring.data.redis.port}")
    private int port;

    @Value("${spring.data.redis.host}")
    private String host;

    private final MemberRedisRepository memberRedisRepository;
    private final RedisConfig redisConfig;

    private final RestClient restClient;

    @Transactional
    public void save() {
        for (int i = 0; i < 5; i++) {
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

    public String findMember(String member) {
        Optional<Member> findMember = memberRedisRepository.findById(member);
        log.info(findMember.get().getName());
        return "ok";
    }
}
