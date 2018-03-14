package springMvc.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Created by hyeondeok on 2018. 3. 5..
 */

@Configuration
//@ComponentScan(basePackages = "springMvc", excludeFilters = {@ComponentScan.Filter(Controller.class)})
//@PropertySource(value = {"classpath:application.properties"})
public class ApplicationConfig extends WebMvcConfigurerAdapter{

//    @Autowired
//    private Environment env;
//
//    @Bean
//    public DataSource dataSource(){
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//        dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driverClassName"));
//        dataSource.setUrl(env.getRequiredProperty("jdbc.url"));
//        dataSource.setUsername(env.getRequiredProperty("jdbc.username"));
//        dataSource.setPassword(env.getRequiredProperty("jdbc.password"));
//
//        return dataSource;
//    }



    @Bean
    public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        jsonConverter.setObjectMapper(objectMapper);
        return jsonConverter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(customJackson2HttpMessageConverter());
    }

}
