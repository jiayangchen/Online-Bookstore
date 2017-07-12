package com.heitian.ssm.controller;

import com.heitian.ssm.model.Order;
import com.heitian.ssm.model.User;
import com.heitian.ssm.service.OrderService;
import com.heitian.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HelloController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

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
}
