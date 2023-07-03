package example.tech_merge;

import example.tech_merge.domain.Member;
import example.tech_merge.repository.MemberRedisRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;

@DataRedisTest
public class RedisRepositoryTest {

    @Autowired
    MemberRedisRepository memberRedisRepository;

    @Test
    public void save() {
        Member member = new Member("kim", "23");

        memberRedisRepository.save(member);
        Member findMember = memberRedisRepository.findById(member.getId()).orElseThrow();

        Assertions.assertEquals(findMember.getName(), member.getName());
    }
}
