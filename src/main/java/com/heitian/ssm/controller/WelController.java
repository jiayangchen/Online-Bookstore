package com.heitian.ssm.controller;

import com.heitian.ssm.model.*;
import com.heitian.ssm.service.BookService;
import com.heitian.ssm.service.OrderService;
import com.heitian.ssm.service.UserService;
import com.heitian.ssm.utils.DecriptUtil;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @RequestMapping("/welcome")
    public String welCome(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          HttpServletRequest request,
                          HttpSession session,
                          Model model){
        //session.invalidate();

        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        /*Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        //2、得到SecurityManager实例 并绑定给SecurityUtils
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        try {
            //4、登录，即身份验证
            subject.login(token);
            model.addAttribute("username",username);
            session.setAttribute("sess_username",username);
            log.info("查询所有图书信息");
            List<Book> bookList = bookService.getAllBook();
            model.addAttribute("bookList",bookList);

            if(subject.hasRole("admin")){
                return "hello";
            }
            return "hello";

        } catch (AuthenticationException e) {
            //5、身份验证失败
            return "refuse";
        }*/

        /*List<User> userList = userService.getAllUser();
        for(User u : userList){
            if(u.getuName().equals(username) && u.getuPassword().equals(password)){
                model.addAttribute("username",username);
                session.setAttribute("sess_username",username);
                log.info("查询所有图书信息");
                List<Book> bookList = bookService.getAllBook();
                model.addAttribute("bookList",bookList);
                return "hello";
            }
        }
        model.addAttribute("username",username);
        return "refuse";*/

        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.isAuthenticated()){
            UsernamePasswordToken upToken = new UsernamePasswordToken(username, password);
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
    public String viewInfo(@RequestParam("addtocartBtn") Long bcid,
                           Model model){
        List<Book> bookList = bookService.getAllBook();
        for(Book b : bookList){
            if(b.getBid().equals(bcid)){
                model.addAttribute("bookInfo",b.getbDiscr());
                return "bookInfo";
            }
        }
        return "refuse";
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
        Order order = new Order();
        order.setOuid(user.getUid());
        order.setAmount(totalAmount);

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(d);
        order.setDate(dateNowStr);
        orderService.addOrder(order);
        return "success";
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
        return "succ";
    }

    @RequestMapping("/perCenter")
    public String perCenter(Model model) {
        /*if (SecurityUtils.getSubject().hasRole("admin")) {
            List<User> userList = userService.getAllUser();
            for (User u : userList) {
                String s = DecriptUtil.MD5(u.getuPassword());
                u.setuPassword(s);
            }
            model.addAttribute("userList", userList);
            return "admin/admin";
        }return "refuse";*/

        List<User> userList = userService.getAllUser();
        for (User u : userList) {
            String s = DecriptUtil.MD5(u.getuPassword());
            u.setuPassword(s);
        }
        model.addAttribute("userList", userList);
        return "admin/admin";
    }

    @RequestMapping("/test")
    public String test(){
        return "admin/test";
    }
}
