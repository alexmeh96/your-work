package com.coder.yourwork.controller;

import com.coder.yourwork.model.Executor;
import com.coder.yourwork.model.Order;
import com.coder.yourwork.service.ExecutorService;
import com.coder.yourwork.service.OrderService;
import com.coder.yourwork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainControl {

    private final UserService userService;

    @Autowired
    private ExecutorService executorService;
    @Autowired
    private OrderService orderService;

    @Autowired
    public MainControl(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String greeting(Model model) {

        List<Order> orderList = orderService.getFirstOrders();
        if (orderList != null && !orderList.isEmpty()) {
            model.addAttribute("orders", orderList);
        }

        List<Executor> executorList = executorService.getFirstExecutors();
        if (executorList != null && !executorList.isEmpty()) {
            model.addAttribute("executors", executorList);
        }

        return "main";
    }

}
