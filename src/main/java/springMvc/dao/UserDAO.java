package springMvc.dao;

import springMvc.model.User;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hyeondeok on 2018. 3. 5..
 */

@Repository
public class UserDAO {
    private static final String NAMESPACE = "database";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private SqlSession sql;

    public void createUser(int id, String name, String email) throws SQLException{
        //UserMapper mapper = sql.getMapper(UserMapper.class);

        Map<String, String> data = new HashMap<String, String>();
        data.put("id", Integer.toString(id));
        data.put("name", name);
        data.put("email", email);
        data.put("date", LocalDateTime.now().format(DATE_TIME_FORMATTER).toString());

        sql.insert(NAMESPACE + ".createUser", data);
    }

    public List<User> getAllUser() throws SQLException {
        //UserMapper mapper = sql.getMapper(UserMapper.class);
        return sql.selectList(NAMESPACE + ".getAllUser");
    }

    public User getOneUser(int id) throws SQLException{
        return sql.selectOne(NAMESPACE + ".getOneUser", id);
    }

    public List<User> getPaginationUser(int offset, int count) throws SQLException{
        Map<String, Integer> data = new HashMap<String, Integer>();

        data.put("offset", offset);
        data.put("count", count);

        return sql.selectList(NAMESPACE + ".getPageUser", data);
    }

    public int getAllUserSize() throws SQLException{
        return sql.selectOne(NAMESPACE + ".getUserCount");
    }

    public void updateUserName(String oldName, String newName) throws SQLException{
        Map<String, String> data = new HashMap<String, String>();

        data.put("oldName", oldName);
        data.put("newName", newName);

        sql.update(NAMESPACE + ".updateUserName", data);
    }

    public void deleteUser(int id) throws SQLException{
        sql.delete(NAMESPACE + ".deleteUser", id);
    }

}
