package com.heitian.ssm.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.heitian.ssm.lucene.luceneIndex;
import com.heitian.ssm.model.Book;
import com.heitian.ssm.model.Order;
import com.heitian.ssm.service.BookService;
import com.heitian.ssm.service.LuceneSearchService;
import com.heitian.ssm.service.OrderService;
import com.heitian.ssm.utils.MyHttpHeader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChenJiayang on 2017/6/19.
 */

@Controller
public class LuceneController {

    private Logger log = Logger.getLogger(LuceneController.class);
    @Autowired
    private LuceneSearchService luceneSearchService;
    @Autowired
    private OrderService orderService;

    @RequestMapping(value="/luceneSearch")
    public String search(@RequestParam(value="target") String target,
                                            Model model) throws Exception {
        List<Book> target_booklist = luceneSearchService.luceneSearch(target);
        model.addAttribute("target_booklist",target_booklist);
        return "succ";
    }

    @RequestMapping(value = "/orderSearch")
    public String orderSearch(@RequestParam(value = "ordersearch",required = false) String ocode,
                              @RequestParam(value = "submitted",defaultValue = "0",required = false) String submitted,
                              @RequestParam(value = "paid",defaultValue = "0",required = false) String paid,
                              Model model,
                              HttpServletRequest request){

        log.info(ocode + " " + submitted + " " + paid);
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("sess_username");
        if(!ocode.isEmpty()){
            List<Order> tmp = new ArrayList<>();
            Order order = orderService.getOrderByCode(ocode);
            tmp.add(order);
            model.addAttribute("result",tmp);
            return "user/ordersearch";
        }else if(submitted.equals("1") && paid.equals("0")){
            List<Order> tmp = orderService.getOrderByStatus(username,1);
            model.addAttribute("result",tmp);
            return "user/ordersearch";
        }else if(submitted.equals("0") && paid.equals("2")){
            List<Order> tmp = orderService.getOrderByStatus(username,2);
            model.addAttribute("result",tmp);
            return "user/ordersearch";
        }else{
            return "user/ordersearch";
        }
    }
}
