package com.heitian.ssm.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.heitian.ssm.lucene.luceneIndex;
import com.heitian.ssm.model.Book;
import com.heitian.ssm.model.Order;
import com.heitian.ssm.model.OrderItem;
import com.heitian.ssm.service.BookService;
import com.heitian.ssm.service.LuceneSearchService;
import com.heitian.ssm.service.OrderItemService;
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
    @Autowired
    private OrderItemService orderItemService;

    @RequestMapping(value="/luceneSearch")
    public String search(@RequestParam(value="target") String target,
                         HttpServletRequest request,
                         Model model) throws Exception {
        HttpSession session = request.getSession();
        if(session.getAttribute("langType").equals("zh")){
            List<Book> target_booklist = luceneSearchService.luceneSearchCN(target);
            model.addAttribute("target_booklist",target_booklist);
            return "succ";
        }else{
            List<Book> target_booklist = luceneSearchService.luceneSearch(target);
            model.addAttribute("target_booklist",target_booklist);
            return "succ";
        }
    }

    @RequestMapping(value = "/orderSearch")
    public String orderSearch(@RequestParam(value = "ordersearch",required = false) String ocode,
                              @RequestParam(value = "submitted",defaultValue = "-1",required = false) String submitted,
                              @RequestParam(value = "paid",defaultValue = "-1",required = false) String paid,
                              @RequestParam(value = "refused",defaultValue = "-1",required = false) String refused,
                              @RequestParam(value = "accepted",defaultValue = "-1",required = false) String accepted,
                              @RequestParam(value = "canceled",defaultValue = "-1",required = false) String canceled,
                              Model model,
                              HttpServletRequest request){

        log.info("submitted:" + submitted + " " + "paid:" + paid + " " +
        "refused:" + refused + " " + "accepted:" + accepted + " " + "canceled:" + canceled);
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("sess_username");
        if(!ocode.isEmpty()){
            List<Order> tmp = new ArrayList<>();
            Order order = orderService.getOrderByCode(ocode);
            tmp.add(order);
            model.addAttribute("result",tmp);
            return "user/ordersearch";
        }else{
            List<Order> tmp = new ArrayList<>();
            List<Order> ans = new ArrayList<>();
            if(submitted.equals("1")){
                tmp = orderService.getOrderByStatus(username,1);
                ans.addAll(tmp);
            }
            if(paid.equals("2")){
                tmp = orderService.getOrderByStatus(username,2);
                ans.addAll(tmp);
            }
            if(refused.equals("0")){
                tmp = orderService.getOrderByStatus(username,0);
                ans.addAll(tmp);
            }
            if(accepted.equals("3")){
                tmp = orderService.getOrderByStatus(username,3);
                ans.addAll(tmp);
            }
            if(canceled.equals("4")){
                tmp = orderService.getOrderByStatus(username,4);
                ans.addAll(tmp);
            }
            model.addAttribute("result",ans);
            return "user/ordersearch";
        }
    }

    @RequestMapping("/viewDetails")
    public String viewDetails(@RequestParam("ocode") String ocode,Model model){
        log.info(ocode);
        List<OrderItem> orderItemsList = orderItemService.selectOrderItemByOCode(ocode);
        model.addAttribute("result",orderItemsList);
        return "user/orderdetails";
    }
}
