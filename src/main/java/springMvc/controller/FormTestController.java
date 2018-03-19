package springMvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springMvc.model.Deal;
import springMvc.service.DealService;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by hyeondeok on 2018. 3. 5..
 */
@Controller
@RequestMapping(value = "/form")
public class FormTestController {
    private static final Logger logger = LoggerFactory.getLogger(FormTestController.class);

    @Autowired DealService dealService;

    @RequestMapping(value = { "", "/" })
    public String mainPage(){
        return "form_js";
    }

    @RequestMapping(value = "/create", params = {"deal_srl", "keyword", "keyword_org"}, method = RequestMethod.POST)
    @ResponseBody
    public String createData(@RequestParam(value = "deal_srl") long deal_srl,
            @RequestParam(value = "keyword") String keyword,
            @RequestParam(value = "keyword_org") String keyword_org){
        Deal deal = new Deal();
        deal.setDeal_srl(deal_srl);
        deal.setKeyword(keyword);
        deal.setKeyword_org(keyword_org);

        try {
            dealService.createDeal(deal);
        } catch (SQLException e) {
            logger.error("SqlException :", e);
        }
        return "create success";
    }

    @RequestMapping(value = "/read", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String readDataWithPagination(
            @RequestParam(value = "page", required = false, defaultValue = "1") long page,
            @RequestParam(value = "count", required = false, defaultValue = "10") long count){


        String result = "{status: \"error\"}";

        try{
            long maxCount = dealService.getAllDealSize();
            long totalPage = maxCount / count;
            if(maxCount % count > 0){
                totalPage++;
            }
            if(totalPage < page){
                page = totalPage;
            }
            if(page < 0){
                page = 1;
            }

            long startPage = ((page - 1 ) / 10) * 10 + 1;
            long endPage = startPage + 10 - 1;


            List<Deal> dealList = dealService.readPaginationDeal((page - 1) * count, count);

            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("status", "success");
            resultMap.put("dealList", dealList);
            resultMap.put("currentPage", page);
            resultMap.put("count", count);
            resultMap.put("startPage", startPage);
            resultMap.put("endPage", endPage);
            resultMap.put("totalPage", totalPage);

            result = new ObjectMapper().writeValueAsString(resultMap);

        } catch (Exception e) {
            logger.error("Exception :", e);
        }

        return result;
    }

    @RequestMapping(value = "/update", params = {"oldName", "newName"})
    @ResponseBody
    public String updateData(@RequestParam(value = "oldName") String oldName, @RequestParam(value = "newName") String newName){
        try{
            dealService.updateDealName(oldName, newName);

        } catch (SQLException e) {
            logger.error("SqlException :", e);
        }
        return "update success";
    }

    @RequestMapping(value = "/delete", params = {"id"})
    @ResponseBody
    public String deleteData(@RequestParam(value = "id") int id){
        try {
            dealService.deleteDeal(id);

        } catch (SQLException e) {
            logger.error("SqlException :", e);
        }
        return "delete success";
    }

    @RequestMapping(value = "/test")
    @ResponseBody
    public String transactionTest(){
        Random random = new Random();
        int id = random.nextInt(Integer.MAX_VALUE - 1);

        try {
            while (dealService.readOneDeal(id) != null) {
                id = random.nextInt();
            }

            dealService.testTransaction(id);

            if (dealService.readOneDeal(id) != null) {
                dealService.deleteDealIfExist(id);
            } else {
                return "Transaction Success";
            }
        } catch (Exception e) {
            logger.error("SqlException :", e);
        }

        return "Transaction Failed";
    }

}
