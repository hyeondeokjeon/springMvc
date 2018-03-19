package springMvc.dao;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import springMvc.model.Deal;

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
public class DealDAO {
    private static final String NAMESPACE = "deal";

//    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private SqlSession sql;

    public void createDeal(long deal_srl, String keyword, String keyword_org) throws SQLException{
        //DealMapper mapper = sql.getMapper(DealMapper.class);

        Map<String, String> data = new HashMap<String, String>();
        data.put("deal_srl", Long.toString(deal_srl));
        data.put("keyword", keyword);
        data.put("keyword_org", keyword_org);

        sql.insert(NAMESPACE + ".createDeal", data);
    }

    public List<Deal> getAllDeal() throws SQLException {
        //DealMapper mapper = sql.getMapper(DealMapper.class);
        return sql.selectList(NAMESPACE + ".getAllDeal");
    }

    public Deal getOneDeal(long id) throws SQLException{
        return sql.selectOne(NAMESPACE + ".getOneDeal", id);
    }

    public long getCount() throws SQLException{
        return sql.selectOne(NAMESPACE + ".getDealCount");
    }

    public List<Deal> getPaginationDeal(long offset, long count) throws SQLException{
        Map<String, Long> data = new HashMap<String, Long>();

        data.put("offset", offset);
        data.put("count", count);

        return sql.selectList(NAMESPACE + ".getPageDeal", data);
    }

    public int getAllDealSize() throws SQLException{
        return sql.selectOne(NAMESPACE + ".getDealCount");
    }

    public void updateDealName(String oldName, String newName) throws SQLException{
        Map<String, String> data = new HashMap<String, String>();

        data.put("newKeyWord", newName);
        data.put("oldKeyWord", oldName);

        sql.update(NAMESPACE + ".updateDealName", data);
    }

    public void deleteDeal(long id) throws SQLException{
        sql.delete(NAMESPACE + ".deleteDeal", id);
    }

}
