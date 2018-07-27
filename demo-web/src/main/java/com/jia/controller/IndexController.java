package com.jia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Auther: jia
 * @Date: 2018/7/27 13:50
 * @Description:
 */
@Controller
public class IndexController {

    @RequestMapping(value = {"/","/index"}, method = RequestMethod.GET)
    public String sayHello() {
        return "index";
    }
}
