package com.coder.yourwork.controller;

import com.coder.yourwork.dto.OrderDto;
import com.coder.yourwork.dto.UserDto;
import com.coder.yourwork.model.Order;
import com.coder.yourwork.model.User;
import com.coder.yourwork.service.OrderService;
import com.coder.yourwork.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderControl {

    private final OrderService orderService;

    @Autowired
    public OrderControl(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/all")
    public String allOrder(Map<String, Object> model) {
        List<Order> orderList = orderService.allOrder();
        model.put("orders", orderList);
        return "order";
    }

    @GetMapping("/user")
    public String userOrders(@AuthenticationPrincipal UserDetailsImpl userDetails, Map<String, Object> model) {

        List<Order> orderList = orderService.userOrder(userDetails.getId());
        model.put("orders", orderList);
        return "order";
    }

    @GetMapping("/create")
    public String createOrder() {
        return "createOrder";
    }

    @PostMapping("/create")
    public String addOrder(@AuthenticationPrincipal UserDetailsImpl userDetails,
                           @Valid OrderDto orderDto, BindingResult bindingResult,
                           Model model) {

        User user = orderService.findUser(userDetails.getUsername());

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);

            return "createOrder";
        }

        if (!orderService.createOrder(user, orderDto)) {
            model.addAttribute("orderError", "Order not create!");
            return "createOrder";
        }

        return "createOrder";
    }

}
