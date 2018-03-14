package springMvc.service;

import springMvc.model.Deal;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by hyeondeok on 2018. 3. 13..
 */
public interface DealService {
    public void createDeal(Deal deal) throws SQLException;
    public Deal readOneDeal(long id) throws SQLException;
    public List<Deal> readAllDeal() throws SQLException;
    public List<Deal> readPaginationDeal(long offset, long count) throws SQLException;
    public long getAllDealSize() throws SQLException;
    public void updateDealName(String oldKeyWord, String newKeyWord) throws SQLException;
    public void deleteDealIfExist(long id) throws SQLException;
    public void deleteDeal(long id) throws SQLException;
    public void testTransaction(long id) throws SQLException;
}
