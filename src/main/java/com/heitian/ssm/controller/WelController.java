package com.heitian.ssm.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.heitian.ssm.model.*;
import com.heitian.ssm.service.BookService;
import com.heitian.ssm.service.OrderService;
import com.heitian.ssm.service.ProductService;
import com.heitian.ssm.service.UserService;
import com.heitian.ssm.utils.DecriptUtil;
import com.heitian.ssm.utils.FormatModel;
import com.heitian.ssm.utils.LogInterceptor;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.CharacterIterator;
import java.text.SimpleDateFormat;
import java.util.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.JstlUtils;
import org.springframework.web.servlet.support.RequestContext;
import sun.plugin.javascript.ocx.JSObject;

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

    private Logger logger = Logger.getLogger(WelController.class);
    
    @RequestMapping("/welcome")
    public String welCome(@RequestParam(value="username",defaultValue="5140379040") String username,
                          @RequestParam(value="password",defaultValue="cao") String password,
                          HttpServletRequest request,
                          HttpSession session,
                          Model model,
                          @RequestParam(value="langType", defaultValue="zh") String langType){

        logger.info("login ....");

        if(!model.containsAttribute("contentModel")){

            if(langType.equals("zh")){
                Locale locale = new Locale("zh", "CN");
                request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
            }
            else if(langType.equals("en")){
                Locale locale = new Locale("en", "US");
                request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
            }
            else {
                request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, LocaleContextHolder.getLocale());
            }
            //从后台代码获取国际化信息
            RequestContext requestContext = new RequestContext(request);

            /*FormatModel formatModel=new FormatModel();
            formatModel.setMoney(12345.678);
            formatModel.setDate(new Date());
            model.addAttribute("contentModel", formatModel);*/
        }

        if(username.equals("5140379040") && password.equals("cao")) {
            session.setAttribute("role", "user");
            List<Book> uBookList = new ArrayList<Book>();

            if(langType.equals("zh")){
                List<Book> bookList = bookService.getAllBookCN();
                for (Book b : bookList) {
                    if (b.getbCategory().equals("IT") || b.getbCategory().equals("Fiction")) {
                        uBookList.add(b);
                    }
                }
            }else{
                List<Book> bookList = bookService.getAllBook();
                for (Book b : bookList) {
                    if (b.getbCategory().equals("IT") || b.getbCategory().equals("Fiction")) {
                        uBookList.add(b);
                    }
                }
            }
            model.addAttribute("bookList", uBookList);
            return "hello";
        }else if(username.equals("Eason") && password.equals("123")){
            session.setAttribute("role","admin");
            List<Book>aBookList = new ArrayList<Book>();

            if(langType.equals("zh")){
                List<Book> bookList = bookService.getAllBookCN();
                for(Book b : bookList){
                    if(b.getbCategory().equals("Science") || b.getbCategory().equals("Literature")){
                        aBookList.add(b);
                    }
                }
            }else{
                List<Book> bookList = bookService.getAllBook();
                for(Book b : bookList){
                    if(b.getbCategory().equals("Science") || b.getbCategory().equals("Literature")){
                        aBookList.add(b);
                    }
                }
            }
            model.addAttribute("bookList", aBookList);
            return "hello";
        }else if(username.equals("Jack") && password.equals("12356")){
            session.setAttribute("role","manager");

            if(langType.equals("zh")){
                List<Book> bookList = bookService.getAllBookCN();
                model.addAttribute("bookList", bookList);
            }else{
                List<Book> bookList = bookService.getAllBook();
                model.addAttribute("bookList", bookList);
            }
            return "hello";
        }else{
            return "refuse";
        }

        /*Subject currentUser = SecurityUtils.getSubject();
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
                *//*model.addAttribute("bookList", bookList);
                return "hello";*//*

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
        return "refuse";*/
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpSession httpSession,
                         Model model){

        logger.info("logout ....");

        httpSession.invalidate();
        cart.setContens(null);
        //SecurityUtils.getSubject().logout();
        return "index2";
    }

    @RequestMapping("/viewInfo")
    @ResponseBody
    public String viewInfo(@RequestParam("addtocartBtn") Long bcid){
        logger.info("view book information ....");
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
        logger.info("add into cart ....");

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

        logger.info("add into cart ....");

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

        logger.info("pay ....");

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

    @RequestMapping("/test")
    public String test(){
        return "admin/test";
    }

    @RequestMapping("/back")
    public String back(Model model,
                       HttpSession session) {

        logger.info("back ....");

        List<Book> bookList = bookService.getAllBook();
        String role = (String) session.getAttribute("role");
        if (role.equals("user")) {
            List<Book> uBookList = new ArrayList<Book>();
            for (Book b : bookList) {
                if (b.getbCategory().equals("IT") || b.getbCategory().equals("Fiction")) {
                    uBookList.add(b);
                }
            }
            model.addAttribute("bookList", uBookList);
            return "hello";
        } else if(role.equals("admin")){
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
    public String testJms() {
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

    @RequestMapping("/sBook")
    public String sBook(@RequestParam(value="sBookName") String sBookName,
                        Model model) throws IOException {

        logger.info("search book ....");

        List<Book>bList = new ArrayList<>();
        List<Book> bookList = bookService.getAllBookCN();
        String result = getConnection("http://localhost:8080/web-ssm/rest/getbook/getBookById/",sBookName);

        if(result.isEmpty()){
            model.addAttribute("getResult","no such book");
            return "sResult";
        }

        model.addAttribute("getResult",result);
        return "sResult";
    }

    private String getConnection(String addr, String id) throws IOException {
        //URL url = new URL("http://localhost:8080/web-ssm/rest/getbook/getBookById/"+id);
        URL url = new URL(addr+id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setConnectTimeout(15000);// 连接超时 单位毫秒
        conn.setReadTimeout(15000);// 读取超时 单位毫秒
        byte bytes[]=new byte[1024];
        InputStream inStream=conn.getInputStream();
        inStream.read(bytes, 0, inStream.available());
        String str = new String(bytes, "utf-8");
        return str;
    }

    /*@RequestMapping(value="/global", method = {RequestMethod.GET})
    public String test(HttpServletRequest request,Model model, @RequestParam(value="langType", defaultValue="zh") String langType){
        if(!model.containsAttribute("contentModel")){

            if(langType.equals("zh")){
                Locale locale = new Locale("zh", "CN");
                request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
            }
            else if(langType.equals("en")){
                Locale locale = new Locale("en", "US");
                request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
            }
            else
                request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, LocaleContextHolder.getLocale());

            //从后台代码获取国际化信息
            RequestContext requestContext = new RequestContext(request);
            model.addAttribute("money", requestContext.getMessage("money"));
            model.addAttribute("date", requestContext.getMessage("date"));

            FormatModel formatModel=new FormatModel();

            formatModel.setMoney(12345.678);
            formatModel.setDate(new Date());

            model.addAttribute("contentModel", formatModel);
        }
        return "globaltest";
    }*/
}
