package com.heitian.ssm.controller;

import com.heitian.ssm.dao.AmountDao;
import com.heitian.ssm.model.Amount;
import com.heitian.ssm.model.OrderItem;
import com.heitian.ssm.model.User;
import com.heitian.ssm.service.OrderItemService;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {

    private Logger log = Logger.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private AmountDao amountDao;

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
        Amount amount = userService.getAmountByUId(user.getUid());
        model.addAttribute("nowMoney",amount.getAmount());
        return "user/usercenter";
    }

    @RequestMapping(path = "/updatePerson", method = RequestMethod.POST)
    public String updatePerson(@RequestParam(value="sex") String sex,
                               @RequestParam(value="address") String address,
                               @RequestParam(value="phone") String phone,
                               @RequestParam(value = "topup") String topup,
                               HttpServletRequest request){
        log.info(topup);
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
        if(!topup.isEmpty()){
            Amount amount = userService.getAmountByUId(user.getUid());
            amount.setAmount(amount.getAmount() + Double.valueOf(topup));
            amountDao.updateAmount(amount);
        }

        userService.updateUser(user);
        return "forward:/userCenter";
    }

    @RequestMapping("/addUser")
    public String addUser(@RequestParam("name") String name,
                          @RequestParam("password") String password,
                          @RequestParam("role") String role,
                          @RequestParam("sex") String sex,
                          @RequestParam("address") String address,
                          @RequestParam("phone") String phone,
                          Model model){
        User user = new User();
        user.setuName(name);
        user.setuPassword(DecriptUtil.MD5(password));
        user.setSex(sex.equals("Man") ? 1 : 0);
        user.setPhone(Long.valueOf(phone));
        user.setAddress(address);
        user.setRid(Long.valueOf(role));
        userService.addUser(user);
        List<User> userList = userService.getAllUser();
        model.addAttribute("userList", userList);
        return "admin/admin";
    }

    @RequestMapping("/deleteUser")
    public String deleteUser(@RequestParam("deleteUser") String username,
                             Model model){
        log.info(username);
        User user = userService.getUserByName(username);
        user.setIsDelete(1);
        userService.updateUser(user);
        List<User> userList = userService.getAllUser();
        model.addAttribute("userList", userList);
        return "admin/admin";
    }

    @RequestMapping("/updateUser")
    public String updateUser(@RequestParam("updateUser") String username,
                             @RequestParam(value = "role",defaultValue = "0") String role,
                             Model model){
        role = role.replace(",","");
        log.info(username + " " + role);
        User user = userService.getUserByName(username);
        switch (role)
        {
            case "User":
                user.setRid((long) 1);
                break;
            case "Admin":
                user.setRid((long) 2);
                break;
            case "Producer":
                user.setRid((long) 3);
                break;
        }
        userService.updateUser(user);
        List<User> userList = userService.getAllUser();
        model.addAttribute("userList", userList);
        return "admin/admin";
    }
}
