package com.heitian.ssm.controller;

import com.heitian.ssm.model.Book;
import com.heitian.ssm.model.Order;
import com.heitian.ssm.model.Provided;
import com.heitian.ssm.model.User;
import com.heitian.ssm.service.BookService;
import com.heitian.ssm.service.OrderService;
import com.heitian.ssm.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Null;
import javax.ws.rs.POST;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloController {

    private Logger log = Logger.getLogger(HelloController.class);
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;

    @RequestMapping("/acceptOrder")
    public String acceptOrder(@RequestParam("acceptOrder") String ocode,
                              HttpServletRequest request,
                              Model model){
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("sess_username");
        orderService.updateOrderStatus(ocode,3);
        User user = userService.getUserByName(username);
        List<Order> orderListForProducer = orderService.getOrderByPId(user.getUid());
        model.addAttribute("orderList",orderListForProducer);
        return "manager/manager";
    }

    @RequestMapping("/cancelOrder")
    public String cancelOrder(@RequestParam("cancelOrder") String ocode,
                              HttpServletRequest request,
                              Model model){
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("sess_username");
        orderService.updateOrderStatus(ocode,4);
        User user = userService.getUserByName(username);
        model.addAttribute("userinfo",user);
        model.addAttribute("orderhistory",orderService.getOrderByName(username));
        return "user/usercenter";
    }

    @RequestMapping("/deleteOrder")
    public String deleteOrder(@RequestParam("deleteOrder") String ocode,
                              HttpServletRequest request,
                              Model model){
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("sess_username");
        orderService.updateOrderStatus(ocode,0);
        User user = userService.getUserByName(username);
        List<Order> orderListForProducer = orderService.getOrderByPId(user.getUid());
        model.addAttribute("orderList",orderListForProducer);
        return "manager/manager";
    }

    @RequestMapping("/orderManagement")
    public String orderManagement(Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("sess_username");
        User user = userService.getUserByName(username);
        List<Order> orderListForProducer = orderService.getOrderByPId(user.getUid());
        model.addAttribute("orderList",orderListForProducer);
        return "manager/manager";
    }

    @RequestMapping("/productManagement")
    public String productManagement(@RequestParam(value = "type",defaultValue = "zh")String type,
                                    Model model,HttpServletRequest request){
        log.info(type);
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("sess_username");
        User user = userService.getUserByName(username);
        List<Provided> bookIdListForProducer = bookService.getBookIdByPId(user.getUid());
        if(type.equals("zh")){
            List<Book>bookList = bookService.getAllBookCN();
            List<Book>ret = new ArrayList<>();
            for(Book b : bookList){
                for(Provided i : bookIdListForProducer){
                    if(i.getB_id() == b.getBid()){
                        ret.add(b);
                    }
                }
            }
            model.addAttribute("productList",ret);
            model.addAttribute("langType","zh");
            return "manager/manager";
        }else if(type.equals("en")){
            List<Book>bookList = bookService.getAllBook();
            List<Book>ret = new ArrayList<>();
            for(Book b : bookList){
                for(Provided i : bookIdListForProducer){
                    if(i.getB_id() == b.getBid()){
                        ret.add(b);
                    }
                }
            }
            model.addAttribute("productList",ret);
            model.addAttribute("langType","en");
            return "manager/manager";
        }
        return null;
    }

    @RequestMapping("/putOnMarket")
    public String putOnMarket(@RequestParam("bid") Long id){
        log.info(id);
        Book book = bookService.getBookCNByBId(id);
        book.setIsSold(1);
        bookService.updateBookCN(book);
        return "forward:/productManagement?type=zh";
    }

    @RequestMapping(value = "/updateBook",method= RequestMethod.POST)
    public String updateBook(@RequestParam(value = "upbookid",defaultValue = "empty") String bid,
                             @RequestParam(value = "bname",defaultValue = "empty") String bname,
                             @RequestParam(value = "bauthor",defaultValue = "empty") String bauthor,
                             @RequestParam(value = "bcate",defaultValue = "empty") String bcate,
                             @RequestParam(value = "bquan",defaultValue = "empty") String bquan,
                             @RequestParam(value = "bprice",defaultValue = "empty") String bprice,
                             @RequestParam(value = "bdescr",defaultValue = "empty") String bdescr,
                             @RequestParam(value = "isEnglish",defaultValue = "zh") String isEnglish,
                             Model model){

        log.info(bid + " " + bquan);

        Book book;
        if(isEnglish.equals("zh")){
            book = bookService.getBookCNByBId(Long.valueOf(bid));
        }else{
            book = bookService.getBookByBId(Long.valueOf(bid));
        }

        book.setbName(bname.isEmpty() ? book.getbName() : bname);
        book.setbAuthor(bauthor.isEmpty() ? book.getbAuthor() : bauthor);
        book.setbCategory(bcate.isEmpty() ? book.getbCategory() : bcate);
        book.setbQuantity(bquan.isEmpty() ? book.getbQuantity() : Integer.valueOf(bquan));
        book.setbPrice(bprice.isEmpty() ? book.getbPrice() : Double.valueOf(bprice));
        book.setbDiscr(bdescr.isEmpty() ? book.getbDiscr() : bdescr);

        if(isEnglish.equals("zh")){
            bookService.updateBookCN(book);
            return "forward:/productManagement?type=zh";
        }else{
            bookService.updateBook(book);
            return "forward:/productManagement?type=en";
        }
    }
}
