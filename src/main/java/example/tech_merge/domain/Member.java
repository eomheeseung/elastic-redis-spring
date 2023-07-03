package example.tech_merge.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * member이라는 이름의 set이 았고, set내부에 20개의 key가 존재한다.
 * type member => sets
 * => smembers member이라고 하면 20개의 id가 출력된다.
 *
 * type member:id값 => hash
 * => hget key field
 * => hget member:id값 name or age // 이렇게 하면 값이 나옴
 */
@Getter
@RedisHash(value = "member", timeToLive = 300L)
public class Member {
    @Id
    private String id;

    private String name;
    private String age;

    public Member(String name, String age) {
        this.name = name;
        this.age = age;
    }
}
