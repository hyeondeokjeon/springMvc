package springMvc.controller;

import springMvc.config.exception.BarException;
import springMvc.config.exception.FooException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by hyeondeok on 2018. 3. 9..
 */
@Controller
@RequestMapping(value = "/exception")
public class ExceptionTestController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String hello() {
        return "Hello Word";
    }

    @RequestMapping(value = "/foo", method = RequestMethod.GET)
    @ResponseBody
    public void foo() {
        throw new FooException("foo exception raised");
    }

    @RequestMapping(value = "/bar", method = RequestMethod.GET)
    @ResponseBody
    public void bar() {
        throw new BarException("bar exception raised");
    }

    @RequestMapping(value = "/etc", method = RequestMethod.GET)
    @ResponseBody
    public void etc() {
        throw new RuntimeException("etc exception raised");
    }
}
