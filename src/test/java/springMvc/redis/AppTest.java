package springMvc.redis;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springMvc.config.RedisConfig;
import springMvc.redis.service.SpringRedis;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RedisConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AppTest {

    @Autowired
    SpringRedis springRedis;

    final static Logger logger = LoggerFactory.getLogger(AppTest.class);

    @Test
    public void _0_테스트_준비() {
        springRedis.flushAll();
    }

    @Test
    public void _1_Key_Value_테스트() throws Exception {
        final String key = "001";

        springRedis.setValue(key, "헬로");
        springRedis.setValue(key, "월드");

        logger.info("{}", springRedis.getValue(key));

        springRedis.delKey(key);

        logger.info("{}", springRedis.getValue(key));

        springRedis.setValue(key, "expire test value", 3, TimeUnit.SECONDS);

        for (int i = 0; i < 5; i++) {
            logger.info("{} {}", i, springRedis.getValue(key));
            Thread.sleep(1500);
        }
    }

    @Test
    public void _2_HashSet_테스트() {
        springRedis.hashSetValue("MBC", "CHANNEL", "11");
        springRedis.hashSetValue("MBC", "RADIO", "95.9MHz");

        springRedis.hashSetValue("SBS", "CHANNEL", "06");
        springRedis.hashSetValue("SBS", "RADIO", "103.5MHz");

        logger.info("MBC RADIO {}", springRedis.hashGetValue("MBC", "RADIO"));
        logger.info("SBS RADIO {}", springRedis.hashGetValue("SBS", "RADIO"));

        logger.info("=== find MBC entries ===");
        Map<Object, Object> entries = springRedis.hashEntries("MBC");
        entries.forEach((key, value) -> {
            logger.info("{} {}", key, value);
        });

        springRedis.hashDelValue("MBC", "RADIO");

        logger.info("=== find MBC entries 2 ===");
        Map<Object, Object> entries2 = springRedis.hashEntries("MBC");
        entries2.forEach((key, value) -> {
            logger.info("{} {}", key, value);
        });
    }

    @Test
    public void _3_Rank_테스트() {
        final String key = "colorRank";

        springRedis.zIncrement(key, "red", 1);
        springRedis.zIncrement(key, "red", 1);
        springRedis.zIncrement(key, "red", 1);

        springRedis.zIncrement(key, "green", 1);
        springRedis.zIncrement(key, "green", 1);
        springRedis.zIncrement(key, "green", 1);

        springRedis.zIncrement(key, "blue", 1);
        springRedis.zIncrement(key, "blue", 1);
        springRedis.zIncrement(key, "blue", 1);

        springRedis.zIncrement(key, "black", 1);
        springRedis.zIncrement(key, "white", 1);
        springRedis.zIncrement(key, "navy", 1);
        springRedis.zIncrement(key, "pink", 1);

        springRedis.zAdd(key, "gold", 100_000);
        springRedis.zAdd(key, "gold", 200_000);

        Set<Object> rank = springRedis.getTop(key, 3);
        logger.info("rank {}", rank);

        logger.info("======= get top 3 =======");

        Set<ZSetOperations.TypedTuple<Object>> rankWithScore = springRedis.getTopWithScore(key, 3);
        rankWithScore.forEach(tuple -> {
            logger.info("{} {}", tuple.getValue(), tuple.getScore());
        });

        logger.info("======= get top 3 ~ 5 =======");

        // 3위부터 5위까지 출력
        rankWithScore = springRedis.getTopWithScore(key, 3, 5);
        rankWithScore.forEach(tuple -> {
            logger.info("{} {}", tuple.getValue(), tuple.getScore());
        });
    }
}
