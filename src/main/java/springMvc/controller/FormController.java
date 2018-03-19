package springMvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springMvc.domain.Member;

import java.util.HashMap;

@Controller
@RequestMapping(value = "/form")
public class FormController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/saveForm", method = RequestMethod.POST)
    @ResponseBody
    public Member saveForm(@RequestParam("name") String name,
                            @RequestParam("age") Integer age) {
        logger.info("name {}, age {}", name, age);
        return new Member(name.toUpperCase(), age);
    }

    @PostMapping(value = "/saveJson", consumes = "application/json")
    @ResponseBody
    public Member saveJson(@RequestBody Member member) {
        logger.info("name {}, age {}", member.getName(), member.getAge());
        return new Member(member.getName().toUpperCase(), member.getAge());
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public Member uploadFile(@RequestParam("name") String name,
                             @RequestParam("age") Integer age,
                             @RequestParam("file") MultipartFile file) {

        if (!file.isEmpty()) {
            logger.info("name {} age {} fileName {}", name, age, file.getOriginalFilename());
        } else {
            logger.info("name {} age {} ", name, age);
        }

        return new Member(name.toUpperCase(), age);
    }

    @RequestMapping(value = "/uploadMultipleFile", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadMultipleFile(@RequestParam("file") MultipartFile[] files) {

        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            String name = files[i].getName();
            logger.info("name {} fileName {}", name, file.getOriginalFilename());
        }

        return new HashMap<String, String>() {{
            put("result", "success");
        }};
    }
}
