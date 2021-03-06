package springMvc.redis;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springMvc.config.RedisConfig;
import springMvc.redis.domain.Box;
import springMvc.redis.service.SpringRedis;

import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RedisConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ObjectTest {

    @Autowired
    SpringRedis springRedis;

    final static Logger logger = LoggerFactory.getLogger(ObjectTest.class);

    @Test
    public void _0_테스트_준비() {
        springRedis.flushAll();
    }

    @Test
    public void _1_BOX_KEY_VALUE_테스트() throws Exception {
        this.springRedis.setObject("001", new Box("001", "RED", Arrays.asList("RED", "DARKRED", "LIGHTRED")));
        Box box1 = (Box)this.springRedis.getObject("001", new TypeReference<Box>() {});
        Box box2 = this.springRedis.getObject("001", Box.class);
        logger.info("\n{}\n{}", box1, box2);
    }

    @Test
    public void _2_STRING_KEY_VALUE_테스트() throws Exception {
        this.springRedis.setObject("001", new Box("001", "RED", Arrays.asList("RED", "DARKRED", "LIGHTRED")));
        Box box1 = (Box)this.springRedis.getObject("001", new TypeReference<Box>() {});
        Box box2 = this.springRedis.getObject("001", Box.class);
        logger.info("\n{}\n{}", box1, box2);
    }

    @Test
    public void _99_테스트_종료() throws Exception {

    }
}
