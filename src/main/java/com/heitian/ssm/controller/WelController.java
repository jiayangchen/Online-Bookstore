package com.heitian.ssm.controller;

import com.alibaba.fastjson.JSONObject;
import com.heitian.ssm.model.*;
import com.heitian.ssm.service.BookService;
import com.heitian.ssm.service.OrderService;
import com.heitian.ssm.service.ProductService;
import com.heitian.ssm.service.UserService;
import com.heitian.ssm.utils.DecriptUtil;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;
import org.springframework.beans.factory.annotation.Qualifier;
import javax.jms.Destination;

@Controller
public class WelController {

    private Logger log = Logger.getLogger(CartController.class);
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private Cart cart;
    @Autowired
    private ProductService productService;
    @Autowired
    @Qualifier("queueDestination")
    private Destination destination;

    @RequestMapping("/welcome")
    public String welCome(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          HttpServletRequest request,
                          HttpSession session,
                          Model model){

        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.isAuthenticated()){
            UsernamePasswordToken upToken = new UsernamePasswordToken(username, DecriptUtil.MD5(password));
            upToken.setRememberMe(false);
            try {
                currentUser.login(upToken);
                //session = currentUser.getSession();
                session.setAttribute("sess_username",username);
                model.addAttribute("username",username);
                log.info("查询所有图书信息");
                List<Book> bookList = bookService.getAllBook();
                /*model.addAttribute("bookList", bookList);
                return "hello";*/

                if(currentUser.hasRole("manager")) {
                    session.setAttribute("role","manager");
                    model.addAttribute("bookList", bookList);
                    return "hello";
                }else if(currentUser.hasRole("user")){
                    session.setAttribute("role","user");
                    List<Book>uBookList = new ArrayList<Book>();
                    for(Book b : bookList){
                        if(b.getbCategory().equals("IT") || b.getbCategory().equals("Fiction")){
                            uBookList.add(b);
                        }
                    }
                    model.addAttribute("bookList", uBookList);
                    return "hello";
                }else if(currentUser.hasRole("admin")){
                    session.setAttribute("role","admin");
                    List<Book>aBookList = new ArrayList<Book>();
                    for(Book b : bookList){
                        if(b.getbCategory().equals("Science") || b.getbCategory().equals("Literature")){
                            aBookList.add(b);
                        }
                    }
                    model.addAttribute("bookList", aBookList);
                    return "hello";
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
        httpSession.invalidate();
        cart.setContens(null);
        SecurityUtils.getSubject().logout();
        return "index2";
    }

    @RequestMapping("/viewInfo")
    @ResponseBody
    public String viewInfo(@RequestParam("addtocartBtn") Long bcid){
        List<Book> bookList = bookService.getAllBook();
        for(Book b : bookList){
            if(b.getBid().equals(bcid)){
                return b.getbDiscr();
            }
        }
        return null;
    }

    @RequestMapping("/api/viewInfo")
    @ResponseBody
    public String viewInfoAjax(@RequestParam("addtocartBtn") Long bcid){
        List<Book> bookList = bookService.getAllBook();
        for(Book b : bookList){
            if(b.getBid().equals(bcid)){
                return b.getbDiscr();
            }
        }
        return null;
    }

    @RequestMapping("/addCart")
    public String addCart(@RequestParam("addtocartBtn") Long bcid) {
        if (bcid == null || bcid <= 0) {
            return "forward:/cart";
        }
        if (cart.getContens() == null) {
            cart.setContens(new ArrayList<Book>());
        }

        List<Book> bookList = bookService.getAllBook();
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

    @RequestMapping(path="/pay",method= RequestMethod.POST)
    public String pay(CartQuantityList cartList, HttpSession session) {
        double totalAmount = 0.00;
        if (cart.getContens() == null) {
            cart.setContens(new ArrayList<Book>());
        }
        for (Book book : cart.getContens()) {
            totalAmount += book.getbPrice() * cartList.fetchWithDefault(book.getBid(), 0);
        }

        String username = (String) session.getAttribute("sess_username");
        User user = userService.getUserByName(username);
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(d);
        JSONObject orderJSON = new JSONObject();
        orderJSON.put("ouid",user.getUid());
        orderJSON.put("totalAmount",totalAmount);
        orderJSON.put("date",dateNowStr);
        productService.sendMessage(destination, orderJSON.toString());
        System.out.println("--------------- producer has sent order -----------------");
        return "orderProcess";
    }

    @RequestMapping("/resetCart")
    public String resetCart(){
        cart.setContens(null);
        return "forward:/cart";
    }

    @RequestMapping("/confirmpay")
    public String confirmpay(@RequestParam("paypwd") String paypwd,
                             Model model){

        model.addAttribute("paypwd",DecriptUtil.MD5(paypwd));
        return "orderProcess";
    }

    @RequestMapping("/perCenter")
    public String perCenter(Model model) {
        List<User> userList = userService.getAllUser();
        model.addAttribute("userList", userList);
        return "admin/admin";
    }

    @RequestMapping("/test")
    public String test(){
        return "admin/test";
    }

    @RequestMapping("/back")
    public String back(Model model) {
        List<Book> bookList = bookService.getAllBook();
        if (SecurityUtils.getSubject().hasRole("user")) {
            List<Book> uBookList = new ArrayList<Book>();
            for (Book b : bookList) {
                if (b.getbCategory().equals("IT") || b.getbCategory().equals("Fiction")) {
                    uBookList.add(b);
                }
            }
            model.addAttribute("bookList", uBookList);
            return "hello";
        } else if(SecurityUtils.getSubject().hasRole("admin")){
            List<Book>aBookList = new ArrayList<Book>();
            for(Book b : bookList){
                if(b.getbCategory().equals("Science") || b.getbCategory().equals("Literature")){
                    aBookList.add(b);
                }
            }
            model.addAttribute("bookList", aBookList);
            return "hello";
        }

        else{
            model.addAttribute("bookList", bookList);
            return "hello";
        }
    }

    @RequestMapping("/testJms")
    public String testJms(){
        for (int i=0; i<2; i++) {
            productService.sendMessage(destination, "Hello,Producer!This is message:" + (i+1));
        }
        return "OrderProcess";
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
