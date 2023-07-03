package example.tech_merge.repository;

import example.tech_merge.domain.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRedisRepository extends CrudRepository<Member, String> {

}
