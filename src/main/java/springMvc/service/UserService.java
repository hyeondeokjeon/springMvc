package springMvc.service;

import springMvc.model.User;

import java.util.List;

/**
 * Created by hyeondeok on 2018. 3. 12..
 */
public interface UserService {
    public void createUser(User user);
    public User readOneUser(int id);
    public List<User> readAllUser();
    public List<User> readPaginationUser(int offset, int count);
    public int getAllUserSize();
    public void updateUserName(String oldName, String newName);
    public void deleteUserIfExist(int id);
    public void deleteUser(int id);
    public void testTransaction(int id);
}
