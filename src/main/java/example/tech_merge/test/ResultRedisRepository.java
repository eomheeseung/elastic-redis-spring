package example.tech_merge.test;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultRedisRepository extends CrudRepository<ResultHistory, String> {
    Optional<List<ResultHistory>> findByIpOrderByCreateDateTimeAsc(String ip);
}
