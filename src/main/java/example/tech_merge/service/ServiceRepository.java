package example.tech_merge.service;

import example.tech_merge.domain.Member;
import example.tech_merge.repository.MemberRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceRepository implements InterfaceService{
    private final MemberRedisRepository memberRedisRepository;

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
