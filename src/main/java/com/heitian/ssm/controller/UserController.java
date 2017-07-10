package com.heitian.ssm.controller;

import com.heitian.ssm.model.User;
import com.heitian.ssm.service.OrderService;
import com.heitian.ssm.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {

    private Logger log = Logger.getLogger(UserController.class);
    @Resource
    private UserService userService;
    @Resource
    private OrderService orderService;

    @RequestMapping("/showUser")
    public String showUser(HttpServletRequest request, Model model){
        List<User> userList = userService.getAllUser();
        User user = userService.getUserByName("Jack");
        model.addAttribute("userList",userList);
        model.addAttribute("testitem",user.getuName());
        return "showUser";
    }

    @RequestMapping("/userCenter")
    public String userCenter(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("sess_username");
        User user = userService.getUserByName(username);
        model.addAttribute("userinfo",user);
        model.addAttribute("orderhistory",orderService.getOrderByName(username));
        return "user/usercenter";
    }

    @RequestMapping(path = "/updatePerson", method = RequestMethod.POST)
    public String updatePerson(@RequestParam(value="sex") String sex,
                               @RequestParam(value="address") String address,
                               @RequestParam(value="phone") String phone,
                               HttpServletRequest request){
        log.info(sex + " " + address + " " + phone);
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("sess_username");
        User user = userService.getUserByName(username);
        if(!sex.isEmpty()){
            user.setSex(Integer.parseInt(sex));
        }
        if(!address.isEmpty()){
            user.setAddress(address);
        }
        if(!phone.isEmpty()){
            user.setPhone(Long.valueOf(phone));
        }

        userService.updateUser(user);
        return "forward:/userCenter";
    }
}
