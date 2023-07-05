package example.tech_merge.test1;

import example.tech_merge.test.ResultHistory;
import example.tech_merge.test.ResultRedisRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@DataRedisTest
public class ResultRedisRepositoryTest {
    @Autowired
    private ResultRedisRepository redisRepository;
    private final Logger log = LoggerFactory.getLogger(ResultRedisRepositoryTest.class);

    @AfterEach
    void afterAll() {
        redisRepository.deleteAll();
    }

    @Test
    void save() throws Exception {
        ResultHistory result = ResultHistory.builder()
                .ip("127.0.0.1")
                .originalText("안녕하세요.")
                .translatedText("hello")
                .createDateTime(LocalDateTime.now())
                .build();

        ResultHistory save = redisRepository.save(result);

        ResultHistory find = redisRepository.findById(save.getId()).get();
        log.info("id:{}", find.getId());
        log.info("original text:{}", find.getOriginalText());
        log.info("translated text:{}", find.getTranslatedText());


        Assertions.assertThat(save.getIp()).isEqualTo(find.getIp());
        Assertions.assertThat(save.getOriginalText()).isEqualTo(find.getOriginalText());
        Assertions.assertThat(save.getTranslatedText()).isEqualTo(find.getTranslatedText());
    }

    @Test
    void save_multi() throws Exception {
        ResultHistory result1 = ResultHistory.builder()
                .ip("127.0.0.1")
                .originalText("안녕하세요.")
                .translatedText("hello")
                .createDateTime(LocalDateTime.now())
                .build();

        ResultHistory result2 = ResultHistory.builder()
                .ip("127.0.0.1")
                .originalText("반갑습니다.")
                .translatedText("Nice to meet you.")
                .createDateTime(LocalDateTime.now())
                .build();

        ResultHistory result3 = ResultHistory.builder()
                .ip("127.1.1.1")
                .originalText("반갑습니다.")
                .translatedText("Nice to meet you.")
                .createDateTime(LocalDateTime.now())
                .build();

        redisRepository.save(result1);
        redisRepository.save(result2);
        redisRepository.save(result3);

        List<ResultHistory> results = redisRepository.findByIpOrderByCreateDateTimeAsc("127.0.0.1").get();
        Assertions.assertThat(results.size()).isEqualTo(2);
    }

    @Test
    void search_order_by() throws Exception {
        // given
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        ResultHistory result1 = ResultHistory.builder()
                .ip("127.0.0.1")
                .originalText("안녕하세요.")
                .translatedText("hello")
                .createDateTime(LocalDateTime.parse("2023-01-05 20:20:20", dateTimeFormatter))
                .build();

        ResultHistory result2 = ResultHistory.builder()
                .ip("127.0.0.1")
                .originalText("반갑습니다.")
                .translatedText("Nice to meet you.")
                .createDateTime(LocalDateTime.parse("2023-01-05 20:21:20", dateTimeFormatter))
                .build();

        ResultHistory result3 = ResultHistory.builder()
                .ip("127.0.0.1")
                .originalText("반갑습니다.")
                .translatedText("Nice to meet you.")
                .createDateTime(LocalDateTime.parse("2023-01-05 20:22:20", dateTimeFormatter))
                .build();

        // when
        redisRepository.save(result1);
        redisRepository.save(result2);
        redisRepository.save(result3);

        // then
        List<ResultHistory> results = redisRepository.findByIpOrderByCreateDateTimeAsc("127.0.0.1").get();
        Assertions.assertThat(results.get(0).getCreateDateTime())
                .isEqualTo(LocalDateTime.parse("2023-01-05 20:20:20", dateTimeFormatter));
    }
}
