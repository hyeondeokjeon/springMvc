package springMvc.config;

import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyeondeok on 2018. 3. 19..
 */
@Configuration
@PropertySource({"classpath:/config/memcached.properties"})
public class MemcachedConfig {

    @Autowired
    private Environment env;

    @Bean
    public MemcachedClient memcachedClient() throws Exception {
        List<InetSocketAddress> addressList = new ArrayList<>();
        addressList.add(new InetSocketAddress(env.getProperty("memcached.hostname"), Integer.parseInt(env.getProperty("memcached.port"))));

        MemcachedClient memcachedClient = new MemcachedClient(addressList);

        return memcachedClient;
    }
}
