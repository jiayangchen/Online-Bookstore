package com.heitian.ssm.controller;

import com.heitian.ssm.service.BookService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;

@Controller
public class CartController {
    private Logger log = Logger.getLogger(CartController.class);
    @Resource
    private BookService bookService;

    @RequestMapping("/viewCart")
    public String showBook(){
        log.info("添加进购物车");
        return "forward:/cart";
    }
}
