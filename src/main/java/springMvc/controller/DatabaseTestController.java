package springMvc.controller;

import springMvc.model.User;
import springMvc.service.UserService;
import springMvc.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Random;

/**
 * Created by hyeondeok on 2018. 3. 5..
 */
@Controller
@RequestMapping(value = "/db")
public class DatabaseTestController {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseTestController.class);

    @Autowired UserService userService;

    @RequestMapping(value = "/")
    public String mainPage(){
        return "main";
    }

    @RequestMapping(value = "/create", params = {"id", "name", "email"})
    @ResponseBody
    public String createData(@RequestParam(value = "id") int id,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "email") String email){
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);

        userService.createUser(user);
        return "create success";
    }

    @RequestMapping(value = "/read")
    public String readDataWithPagination(Model model,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "count", required = false, defaultValue = "10") int count){

        int maxCount = userService.getAllUserSize();
        int totalPage = maxCount / count;
        if(maxCount % count > 0){
            totalPage++;
        }
        if(totalPage < page){
            page = totalPage;
        }
        if(page < 0){
            page = 1;
        }

        int startPage = ((page - 1 ) / 10) * 10 + 1;
        int endPage = startPage + 10 - 1;


        List<User> userList = userService.readPaginationUser((page - 1) * count, count);
        model.addAttribute("userList", userList);
        model.addAttribute("currentPage", page);
        model.addAttribute("count", count);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPage", totalPage);


        return "list";
    }

    @RequestMapping(value = "/update", params = {"oldName", "newName"})
    @ResponseBody
    public String updateData(@RequestParam(value = "oldName") String oldName, @RequestParam(value = "newName") String newName){
        userService.updateUserName(oldName, newName);
        return "update success";
    }

    @RequestMapping(value = "/delete", params = {"id"})
    @ResponseBody
    public String deleteData(@RequestParam(value = "id") int id){
        userService.deleteUser(id);
        return "delete success";
    }

    @RequestMapping(value = "/test")
    @ResponseBody
    public String transactionTest(){
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

        if(userService.readOneUser(id) == null){
            return "Transaction Success";
        }else{
            userService.deleteUserIfExist(id);
            return "Transaction Failed";
        }
    }

}
