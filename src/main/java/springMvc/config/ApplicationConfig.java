package springMvc.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import springMvc.viewresolver.JsonViewResolver;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyeondeok on 2018. 3. 5..
 */

@Configuration
@EnableWebMvc
@EnableAsync
@Import({

        MemcachedConfig.class,
//        DatabaseConfig.class,
//        MybatisConfig.class
})
//@ComponentScan(basePackages = "springMvc", excludeFilters = {@ComponentScan.Filter(Controller.class)})
//@PropertySource(value = {"classpath:application.properties"})
public class ApplicationConfig extends WebMvcConfigurerAdapter{

    @Autowired
    private Environment env;

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

//
//    @Bean
//    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
//        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
//        resolver.setContentNegotiationManager(manager);
//        List<ViewResolver> resolvers = new ArrayList<>();
//
//
//        resolvers.add(internalResourceViewResolver());
//        resolvers.add(jsonViewResolver());
//        resolver.setViewResolvers(resolvers);
//        return resolver;
//    }
//
//    @Bean
//    public InternalResourceViewResolver internalResourceViewResolver() {
//
//        // 뷰 이름으로 "home"을 리턴하면, "/WEB-INF/view/home.jsp" 파일이 사용됩니다.
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        resolver.setPrefix("/WEB-INF/view/");
//        resolver.setSuffix(".jsp");
//        resolver.setOrder(0);
//
//        return resolver;
//    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/pages/**").addResourceLocations("/WEB-INF/pages/");
//        registry.addResourceHandler("/**").addResourceLocations("/");
//    }
//
//    @Bean
//    public ViewResolver jsonViewResolver() {
//        return new JsonViewResolver();
//    }

}
