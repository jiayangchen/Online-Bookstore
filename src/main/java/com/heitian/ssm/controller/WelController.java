package com.heitian.ssm.controller;

import com.heitian.ssm.model.*;
import com.heitian.ssm.service.BookService;
import com.heitian.ssm.service.OrderService;
import com.heitian.ssm.service.UserService;
import com.heitian.ssm.utils.DecriptUtil;
import org.apache.log4j.Logger;
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
    @Resource
    private BookService bookService;
    @Resource
    private UserService userService;
    @Resource
    private OrderService orderService;
    @Autowired
    private Cart cart;

    @RequestMapping("/welcome")
    public String welCome(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          HttpServletRequest request,
                          HttpSession session,
                          Model model){

        List<User> userList = userService.getAllUser();
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
        return "hello";

        /*Subject currentUser = SecurityUtils.getSubject();
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
                model.addAttribute("bookList",bookList);
                return "hello";
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
        return "refuse";*/
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpSession httpSession,
                         Model model){
        httpSession.invalidate();
        cart.setContens(null);
        return "index2";
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

        model.addAttribute("paypwd",paypwd);
        return "succ";
    }

    @RequestMapping("/perCenter")
    public String perCenter(Model model){
        List<User> userList = userService.getAllUser();
        for (User u : userList){
            String s = DecriptUtil.MD5(u.getuPassword());
            u.setuPassword(s);
        }
        model.addAttribute("userList", userList);
        return "admin";
    }
}
