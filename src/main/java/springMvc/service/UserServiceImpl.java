package springMvc.service;

import springMvc.dao.UserDAO;
import springMvc.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Created by hyeondeok on 2018. 3. 5..
 */
@Service
@Transactional
public class UserServiceImpl implements UserService{
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDAO userDAO;

    @Override
    public void createUser(User user){
        try {
            userDAO.createUser(user.getId(), user.getName(), user.getEmail());
        }catch (SQLException e){
            logger.error("SQL Exception : ", e);
        }
    }

    @Override
    public User readOneUser(int id){

        User user = null;
        try {
            user = userDAO.getOneUser(id);
        }catch (SQLException e){
            logger.error("SQL Exception : ", e);
        }

        return user;
    }

    @Override
    public List<User> readAllUser(){
        List<User> userList = Collections.emptyList();

        try {
            userList = userDAO.getAllUser();
        }catch (SQLException e){
            logger.error("SQL Exception : ", e);
        }

        return userList;
    }

    @Override
    public List<User> readPaginationUser(int offset, int count){

        List<User> userList = Collections.emptyList();

        try {
            userList = userDAO.getPaginationUser(offset, count);
        }catch (SQLException e){
            logger.error("SQL Exception : ", e);
        }

        return userList;
    }


    @Override
    public int getAllUserSize(){
        try {
            return userDAO.getAllUserSize();
        }catch (SQLException e){
            logger.error("SQL Exception : ", e);
        }
        return 0;
    }

    @Override
    public void updateUserName(String oldName, String newName){

        try {
            userDAO.updateUserName(oldName, newName);
        }catch (SQLException e){
            logger.error("SQL Exception : ", e);
        }
    }

    @Override
    public void deleteUserIfExist(int id){
        try{
            Optional<User> user = Optional.ofNullable(userDAO.getOneUser(id));
            user.ifPresent(userObj -> {
                deleteUser(userObj.getId());
            });
        }catch(SQLException e){
            logger.error("SQL Exception : ", e);
        }
    }

    @Override
    public void deleteUser(int id){

        try {
            userDAO.deleteUser(id);
        }catch (SQLException e){
            logger.error("SQL Exception : ", e);
        }

    }

    /// Transaction 이 올바르게 동작하는지 확인하기위해서
    /// 고의적으로 중복된 키 값을 두 번 INSERT 함.
    @Override
    public void testTransaction(int id){
        User testUser = new User();
        testUser.setId(id);
        testUser.setName("tom");
        testUser.setEmail("tmon@gmail.com");

        createUser(testUser);
        createUser(testUser);
    }
}
