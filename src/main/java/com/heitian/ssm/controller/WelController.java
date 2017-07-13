package com.heitian.ssm.controller;

import com.alibaba.fastjson.JSONObject;
import com.heitian.ssm.model.*;
import com.heitian.ssm.service.*;
import com.heitian.ssm.utils.DecriptUtil;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import org.springframework.beans.factory.annotation.Qualifier;
import javax.jms.Destination;

@Controller
public class WelController {

    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private Cart cart;
    @Autowired
    private ProductService productService;
    @Autowired
    @Qualifier("queueDestination")
    private Destination destination;

    private Logger logger = Logger.getLogger(WelController.class);
    
    @RequestMapping("/welcome")
    public String welCome(@RequestParam(value="username") String username,
                          @RequestParam(value="password") String password,
                          HttpServletRequest request,
                          Model model){
        //SecurityUtils.getSubject().logout();
        logger.info(request.getSession().getAttribute("langType"));
        HttpSession session = request.getSession();
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.isAuthenticated()){
            UsernamePasswordToken upToken = new UsernamePasswordToken(username, DecriptUtil.MD5(password));
            upToken.setRememberMe(false);
            try {
                currentUser.login(upToken);
                session.setAttribute("sess_username",username);
                model.addAttribute("username",username);
                List<Book> bookList = bookService.getAllBook();
                List<Book> bookListCN = bookService.getAllBookCN();
                model.addAttribute("bookList", bookList);

                if(currentUser.hasRole("manager"))
                {
                    User user = userService.getUserByName(username);
                    List<Order> orderListForProducer = orderService.getOrderByPId(user.getUid());
                    model.addAttribute("orderList",orderListForProducer);
                    return "manager/manager";
                }

                else if(currentUser.hasRole("user"))
                {
                    session.setAttribute("role","user");
                    if(session.getAttribute("langType").equals("zh")){
                        model.addAttribute("bookList", bookListCN);
                        return "hello";
                    }else{
                        model.addAttribute("bookList", bookList);
                        return "hello";
                    }
                }

                else if(currentUser.hasRole("admin"))
                {
                    List<User> userList = userService.getAllUser();
                    model.addAttribute("userList", userList);
                    return "admin/admin";
                }
            } catch (IncorrectCredentialsException ice) {
                //System.out.println("Wrong Username Or Pwd");
                model.addAttribute("WUP","Wrong Username Or Pwd");
                return "refuse";
            } catch (LockedAccountException lae) {
                //System.out.println("Account Locked");
                model.addAttribute("AL","Account Locked");
                return "refuse";
            } catch (AuthenticationException ae) {
                //System.out.println(ae.getMessage());
                model.addAttribute("OTHER",ae.getMessage());
                return "refuse";
            }
        }
        return "refuse";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpSession httpSession,
                         Model model){

        logger.info("logout ....");
        httpSession.invalidate();
        cart.setContens(null);
        SecurityUtils.getSubject().logout();
        return "index2";
    }

    @RequestMapping("/viewInfo")
    @ResponseBody
    public String viewInfo(@RequestParam("addtocartBtn") Long bcid,
                           HttpServletRequest request){
        logger.info("view book information ....");
        if(request.getSession().getAttribute("langType").equals("en")) {
            List<Book> bookList = bookService.getAllBook();
            for (Book b : bookList) {
                if (b.getBid().equals(bcid)) {
                    return b.getbDiscr();
                }
            }
        }
        else{
            List<Book> bookList = bookService.getAllBookCN();
            for (Book b : bookList) {
                if (b.getBid().equals(bcid)) {
                    return b.getbDiscr();
                }
            }
        }
        return null;
    }

    /*@RequestMapping("/api/viewInfo")
    @ResponseBody
    public String viewInfoAjax(@RequestParam("addtocartBtn") Long bcid,
                               HttpServletRequest request){
        logger.info("add into cart ....");
        if(request.getSession().getAttribute("langType").equals("en")) {
            List<Book> bookList = bookService.getAllBook();
            for (Book b : bookList) {
                if (b.getBid().equals(bcid)) {
                    return b.getbDiscr();
                }
            }
        }
        else{
            List<Book> bookList = bookService.getAllBookCN();
            for (Book b : bookList) {
                if (b.getBid().equals(bcid)) {
                    return b.getbDiscr();
                }
            }
        }
        return null;
    }*/

    @RequestMapping("/addCart")
    public String addCart(@RequestParam("addtocartBtn") Long bcid,
                          HttpServletRequest request) {

        logger.info("add into cart ....");

        HttpSession session = request.getSession();

        if (bcid == null || bcid <= 0) {
            return "forward:/cart";
        }
        if (cart.getContens() == null) {
            cart.setContens(new ArrayList<Book>());
        }

        List<Book> bookList = new ArrayList<>();

        if(session.getAttribute("langType").equals("zh")){
            bookList = bookService.getAllBookCN();
        }else{
            bookList = bookService.getAllBook();
        }

        List<Book> cartBookList = new ArrayList<Book>();
        for(Book b : bookList){
            if(b.getBid().equals(bcid)){
                cartBookList.add(b);
            }
        }
        cart.getContens().addAll(cartBookList);
        return "forward:/cart";
    }

    @RequestMapping("/cart")
    public ModelAndView getCart() {
        if (cart.getContens() == null) {
            cart.setContens(new ArrayList<Book>());
        }
        return new ModelAndView("showCart").addObject("cart", cart.getContens());
    }

    @RequestMapping(path="/pay", method= RequestMethod.POST)
    public String pay(CartQuantityList cartList, HttpSession session,
                      Model model) {

        logger.info("pay ....");

        synchronized (this) {
            String username = (String) session.getAttribute("sess_username");
            User user = userService.getUserByName(username);

            Timestamp d = new Timestamp(System.currentTimeMillis());

            String orderCode = d.toString() + user.getUid();

            double totalAmount = 0.00;
            if (cart.getContens() == null) {
                cart.setContens(new ArrayList<Book>());
            }

            for (Book book : cart.getContens()) {
                int buyNum = cartList.fetchWithDefault(book.getBid(), 0);
                book.setbQuantity(buyNum);
                if (book.getbQuantity() < buyNum) {
                    buyNum = book.getbQuantity();
                }
                totalAmount += book.getbPrice() * buyNum;
                bookService.updateBookStock(book.getBid(), book.getbQuantity() - buyNum);
                bookService.updateBookCNStock(book.getBid(), book.getbQuantity() - buyNum);

                OrderItem ot = new OrderItem();
                ot.setO_code(orderCode);
                ot.setOt_bid(book.getBid());
                ot.setOt_quantity(buyNum);
                orderItemService.addOrderItem(ot);
            }

            Date date = new Date();
            Timestamp tp = new Timestamp(date.getTime());

            JSONObject orderJSON = new JSONObject();
            orderJSON.put("ocode", orderCode);
            orderJSON.put("ouid", user.getUid());
            orderJSON.put("opid", 0);
            orderJSON.put("o_create_time", tp);
            orderJSON.put("o_status", 1); // 已提交
            orderJSON.put("o_amount", totalAmount);
            productService.sendMessage(destination, orderJSON.toString());

            model.addAttribute("ordername", username);
            model.addAttribute("orderaddress", "Shanghai Minhang District DongChuan Rd 800.");
            model.addAttribute("orderlist", cart.getContens());
            session.setAttribute("orderCode", orderCode);
            return "orderProcess";
        }
    }

    @RequestMapping("/updateOrderStatus")
    public String updateOrderStatus(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        String orderCode = (String) session.getAttribute("orderCode");
        orderService.updateOrderStatus(orderCode,2);
        return "forward:/back";
    }

    @RequestMapping("/resetCart")
    public String resetCart(){
        logger.info("reset cart ....");
        cart.setContens(null);
        return "forward:/cart";
    }

    @RequestMapping("/confirmpay")
    public String confirmpay(@RequestParam("paypwd") String paypwd,
                             Model model){

        logger.info("confirm pay ....");

        model.addAttribute("paypwd",DecriptUtil.MD5(paypwd));
        return "orderProcess";
    }

    @RequestMapping("/perCenter")
    public String perCenter(Model model) {
        logger.info("view perCenter ....");

        List<User> userList = userService.getAllUser();
        model.addAttribute("userList", userList);
        return "admin/admin";
    }


    @RequestMapping("/back")
    public String back(Model model,
                       HttpServletRequest request) {

        logger.info("back ....");
        HttpSession session = request.getSession();
        model.addAttribute("username",session.getAttribute("sess_username"));
        List<Book> bookList = bookService.getAllBook();
        List<Book> booklistCN = bookService.getAllBookCN();
        String role = (String) session.getAttribute("role");
        if (role.equals("user")) {
            if(session.getAttribute("langType").equals("zh")){
                model.addAttribute("bookList", booklistCN);
                return "hello";
            }else{
                model.addAttribute("bookList", bookList);
                return "hello";
            }
        } else if(role.equals("admin")){
            /*List<Book>aBookList = new ArrayList<Book>();
            for(Book b : bookList){
                if(b.getbCategory().equals("Science") || b.getbCategory().equals("Literature")){
                    aBookList.add(b);
                }
            }
            model.addAttribute("bookList", aBookList);
            return "hello";*/
            List<User> userList = userService.getAllUser();
            model.addAttribute("userList", userList);
            return "admin/admin";
        }
        else{
            model.addAttribute("bookList", bookList);
            return "hello";
        }
    }

    @RequestMapping("/chatroom")
    public String chatroom(){
        return "chatroom";
    }

    @RequestMapping("/chat")
    public String chat(){
        return "chat";
    }
}
