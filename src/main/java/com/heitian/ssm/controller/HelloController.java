package com.heitian.ssm.controller;

import com.heitian.ssm.dao.AmountDao;
import com.heitian.ssm.dao.BookDao;
import com.heitian.ssm.model.*;
import com.heitian.ssm.service.BookService;
import com.heitian.ssm.service.OrderItemService;
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
    @Autowired
    private AmountDao amountDao;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private BookDao bookDao;

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
        Order order = orderService.getOrderByCode(ocode);

        //返还钱
        Amount amount = amountDao.getAmountByUId(order.getOuid());
        amount.setAmount(amount.getAmount() + order.getO_amount());
        amountDao.updateAmount(amount);

        //返还库存
        List<OrderItem> list = orderItemService.selectOrderItemByOCode(ocode);
        for(OrderItem ot : list){
            if(session.getAttribute("langType").equals("zh")){
                Book bookcn = bookService.getBookCNByBId(ot.getOt_bid());
                bookService.updateBookCNStock(ot.getOt_bid(),bookcn.getbQuantity() + ot.getOt_quantity());
            }else {
                Book book = bookService.getBookByBId(ot.getOt_bid());
                bookService.updateBookStock(ot.getOt_bid(), book.getbQuantity() + ot.getOt_quantity());
            }
        }

        User user = userService.getUserByName(username);
        model.addAttribute("userinfo",user);
        model.addAttribute("orderhistory",orderService.getOrderByName(username));
        model.addAttribute("nowMoney",amount.getAmount());
        return "user/usercenter";
    }

    @RequestMapping("/payWhenSubmitted")
    public String payWhenSubmitted(@RequestParam("payOrder") String ocode,
                                   HttpServletRequest request,
                                   Model model){
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("sess_username");
        orderService.updateOrderStatus(ocode,2);
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
        Order order = orderService.getOrderByCode(ocode);
        //返还钱
        Amount amount = amountDao.getAmountByUId(order.getOuid());
        amount.setAmount(amount.getAmount() + order.getO_amount());
        amountDao.updateAmount(amount);

        //返还库存
        List<OrderItem> list = orderItemService.selectOrderItemByOCode(ocode);
        for(OrderItem ot : list){
            if(session.getAttribute("langType").equals("zh")){
                Book bookcn = bookService.getBookCNByBId(ot.getOt_bid());
                bookService.updateBookCNStock(ot.getOt_bid(),bookcn.getbQuantity() + ot.getOt_quantity());
            }else {
                Book book = bookService.getBookByBId(ot.getOt_bid());
                bookService.updateBookStock(ot.getOt_bid(), book.getbQuantity() + ot.getOt_quantity());
            }
        }

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

    @RequestMapping(value = "/putOnMarket",method= RequestMethod.POST)
    public String putOnMarket(@RequestParam("putOnMarket") String id,
                              @RequestParam("isEnglish") String isEnglish,
                              HttpServletRequest request,
                              Model model){
        log.info(id + " " + isEnglish);
        if(isEnglish.equals("zh")){
            Book bookcn = bookService.getBookCNByBId(Long.valueOf(id));
            bookcn.setIsSold(1);
            bookService.updateBookCN(bookcn);
            return "forward:/productManagement?type=zh";
        }else{
            Book book = bookService.getBookByBId(Long.valueOf(id));
            book.setIsSold(1);
            bookService.updateBook(book);
            return "forward:/productManagement?type=en";
        }
    }

    @RequestMapping(value = "/deleteBook",method= RequestMethod.POST)
    public String deleteBook(@RequestParam("deleteBook") String id,
                             @RequestParam("isEnglish") String isEnglish){
        if(isEnglish.equals("zh")){
            bookDao.deleteBookCN(Long.parseLong(id));
            return "forward:/productManagement?type=zh";
        }else{
            bookDao.deleteBook(Long.parseLong(id));
            return "forward:/productManagement?type=en";
        }
    }

    @RequestMapping(value = "/putDownMarket",method= RequestMethod.POST)
    public String putDownMarket(@RequestParam("putDownMarket") String id,
                              @RequestParam("isEnglish") String isEnglish,
                              HttpServletRequest request,
                              Model model){
        log.info(id + " " + isEnglish);
        if(isEnglish.equals("zh")){
            Book bookcn = bookService.getBookCNByBId(Long.valueOf(id));
            bookcn.setIsSold(0);
            bookService.updateBookCN(bookcn);
            return "forward:/productManagement?type=zh";
        }else{
            Book book = bookService.getBookByBId(Long.valueOf(id));
            book.setIsSold(0);
            bookService.updateBook(book);
            return "forward:/productManagement?type=en";
        }
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

        log.info(bid);
        bname = bname.replace(",","");
        bauthor = bauthor.replace(",","");
        bcate = bcate.replace(",","");
        bquan = bquan.replace(",","");
        bprice = bprice.replace(",","");
        bdescr = bdescr.replace(",","");
        Book book;
        if(isEnglish.contains("zh")){
            book = bookService.getBookCNByBId(Long.valueOf(bid));
        }else{
            book = bookService.getBookByBId(Long.valueOf(bid));
        }

        book.setbName(bname.equals("") ? book.getbName() : bname);
        book.setbAuthor(bauthor.equals("") ? book.getbAuthor() : bauthor);
        book.setbCategory(bcate.equals("") ? book.getbCategory() : bcate);
        book.setbQuantity(bquan.equals("") ? book.getbQuantity() : Integer.valueOf(bquan));
        book.setbPrice(bprice.equals("") ? book.getbPrice() : Double.valueOf(bprice));
        book.setbDiscr(bdescr.equals("") ? book.getbDiscr() : bdescr);

        if(isEnglish.contains("zh")){
            bookService.updateBookCN(book);
            return "forward:/productManagement?type=zh";
        }else{
            bookService.updateBook(book);
            return "forward:/productManagement?type=en";
        }
    }
}
