package com.heitian.ssm.controller;

import com.heitian.ssm.model.Book;
import com.heitian.ssm.service.BookService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChenJiayang on 2017/7/12.
 */

@Controller
public class BookController {

    private Logger log = Logger.getLogger(BookController.class);
    @Autowired
    private BookService bookService;

    @RequestMapping("/getLiterature")
    public String getLiterature(HttpServletRequest request,
                                Model model,
                                @RequestParam(value="cate") String cate){
        log.info(cate);
        HttpSession session = request.getSession();
        List<Book> literatureList = new ArrayList<>();
        if(session.getAttribute("langType").equals("zh")){
            switch (cate)
            {
                case "literature":
                    literatureList = bookService.getAllBookCNByCategory("literature");
                    model.addAttribute("bookList",literatureList);
                    return "hello";
                case "science":
                    literatureList = bookService.getAllBookCNByCategory("science");
                    model.addAttribute("bookList",literatureList);
                    return "hello";
                case "novel":
                    literatureList = bookService.getAllBookCNByCategory("novel");
                    model.addAttribute("bookList",literatureList);
                    return "hello";
                case "fiction":
                    literatureList = bookService.getAllBookCNByCategory("fiction");
                    model.addAttribute("bookList",literatureList);
                    return "hello";
                case "IT":
                    literatureList = bookService.getAllBookCNByCategory("IT");
                    model.addAttribute("bookList",literatureList);
                    return "hello";
                case "biography":
                    literatureList = bookService.getAllBookCNByCategory("biography");
                    model.addAttribute("bookList",literatureList);
                    return "hello";
                case "all":
                    literatureList = bookService.getAllBookCN();
                    model.addAttribute("bookList",literatureList);
                    return "hello";
                default:
                    literatureList = bookService.getAllBookCN();
                    model.addAttribute("bookList",literatureList);
                    return "hello";
            }

        }else{
            switch (cate)
            {
                case "literature":
                    literatureList = bookService.getAllBookByCategory("literature");
                    model.addAttribute("bookList",literatureList);
                    return "hello";
                case "science":
                    literatureList = bookService.getAllBookByCategory("science");
                    model.addAttribute("bookList",literatureList);
                    return "hello";
                case "novel":
                    literatureList = bookService.getAllBookByCategory("novel");
                    model.addAttribute("bookList",literatureList);
                    return "hello";
                case "fiction":
                    literatureList = bookService.getAllBookByCategory("fiction");
                    model.addAttribute("bookList",literatureList);
                    return "hello";
                case "IT":
                    literatureList = bookService.getAllBookByCategory("IT");
                    model.addAttribute("bookList",literatureList);
                    return "hello";
                case "biography":
                    literatureList = bookService.getAllBookByCategory("biography");
                    model.addAttribute("bookList",literatureList);
                    return "hello";
                case "all":
                    literatureList = bookService.getAllBook();
                    model.addAttribute("bookList",literatureList);
                    return "hello";
                default:
                    literatureList = bookService.getAllBook();
                    model.addAttribute("bookList",literatureList);
                    return "hello";
            }
        }
    }
}
