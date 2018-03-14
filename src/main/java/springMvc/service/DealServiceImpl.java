package springMvc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springMvc.dao.DealDAO;
import springMvc.model.Deal;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by hyeondeok on 2018. 3. 13..
 */
@Service
@Transactional
public class DealServiceImpl implements DealService {
    private static final Logger logger = LoggerFactory.getLogger(DealService.class);

    @Autowired
    private DealDAO dealDAO;


    @Override public void createDeal(Deal deal) throws SQLException{

        dealDAO.createDeal(deal.getDeal_srl(), deal.getKeyword(), deal.getKeyword_org());
    }

    @Override public Deal readOneDeal(long id)  throws SQLException{
        return dealDAO.getOneDeal(id);
    }

    @Override public List<Deal> readAllDeal()  throws SQLException{
        return dealDAO.getAllDeal();
    }

    @Override public List<Deal> readPaginationDeal(long offset, long count)  throws SQLException{
        return dealDAO.getPaginationDeal(offset, count);
    }

    @Override public long getAllDealSize()  throws SQLException{
        return dealDAO.getCount();
    }

    @Override public void updateDealName(String oldKeyWord, String newKeyWord)  throws SQLException{
        dealDAO.updateDealName(oldKeyWord, newKeyWord);
    }

    @Override public void deleteDealIfExist(long id) throws SQLException{
        if(dealDAO.getOneDeal(id) != null) {
            dealDAO.deleteDeal(id);
        }
    }

    @Override public void deleteDeal(long id)  throws SQLException{
        dealDAO.deleteDeal(id);
    }

    @Override public void testTransaction(long id)  throws SQLException{
        Deal testDeal = new Deal();
        testDeal.setDeal_srl(id);
        testDeal.setKeyword("test keyword");
        testDeal.setKeyword_org("origin keyword");

        createDeal(testDeal);
        createDeal(testDeal);
    }
}
