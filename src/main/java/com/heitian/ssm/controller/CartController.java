package com.heitian.ssm.controller;

import com.heitian.ssm.model.Book;
import com.heitian.ssm.service.BookService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
