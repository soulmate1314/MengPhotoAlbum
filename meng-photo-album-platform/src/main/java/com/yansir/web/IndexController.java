package com.yansir.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("")
public class IndexController {

    /**
     * @author YANSIR
     * @Description:加载首页,目前是单页面,没用采用iframe嵌入,如果要多页面请将此处返回修改为index
     * @date 2020/7/27 15:44
     */
    @GetMapping("/")
    public String indexView() {
        return "index";
    }

    @GetMapping("/organization")
    public String organizationView() {
        return "organization";
    }

    @GetMapping("/user")
    public String userView() {
        return "user";
    }



}
