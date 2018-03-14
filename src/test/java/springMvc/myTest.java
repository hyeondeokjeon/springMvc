package springMvc;

import org.junit.Before;
import springMvc.model.User;
import springMvc.service.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Random;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by hyeondeok on 2018. 3. 5..
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml", "/dispatcher-servlet.xml"})
public class myTest {
    private static final Logger logger = LoggerFactory.getLogger(myTest.class);

    @Autowired
    UserServiceImpl userService;

    @Test
    public void testCreateNRead(){

        Random random = new Random();
        int id = random.nextInt(Integer.MAX_VALUE - 1);

        User result = null;
        try {

            User user = new User();
            user.setId(id);
            user.setName("testname");
            user.setEmail("email@mail.net");

            userService.createUser(user);


        }catch (Exception e){
            logger.error("error: ", e);
        }

        result = userService.readOneUser(id);

        assertThat(result.getName(), allOf(is(notNullValue()), is("testname")));

        userService.deleteUserIfExist(id);
    }

    @Test
    public void testReadAll(){
        try{
            List<User> user = userService.readAllUser();
            assertThat(user, not(user.isEmpty()));
        }catch (Exception e){
            logger.error("error: ", e);
        }
    }

    @Test
    public void testReadOne(){
        try{
            User user = userService.readOneUser(1);
            assertNotNull(user);
        }catch (Exception e){
            logger.error("error: ", e);
        }
    }

    @Test
    public void testPagination(){
        try{
            int offset = 0;
            int count = 10;

            List<User> user = userService.readPaginationUser(offset, count);

            assertThat(user.isEmpty(), is(false));
        }catch (Exception e){
            logger.error("error: ", e);
        }
    }


    @Test
    public void testTransaction(){

        Random random = new Random();
        int id = random.nextInt(Integer.MAX_VALUE - 1);

        while (userService.readOneUser(id) != null) {
            id = random.nextInt();
        }

        try{
            userService.testTransaction(id);
        }catch (Exception e){
            logger.error("SQL Exception : ", e);
        }

        userService.deleteUserIfExist(id);
        assertThat(userService.readOneUser(id), is(nullValue()));
    }
}
