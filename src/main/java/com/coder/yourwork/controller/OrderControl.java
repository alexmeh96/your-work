package com.coder.yourwork.controller;

import com.coder.yourwork.dto.OrderDto;
import com.coder.yourwork.model.Category;
import com.coder.yourwork.model.Order;
import com.coder.yourwork.model.User;
import com.coder.yourwork.service.OrderService;
import com.coder.yourwork.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/category/all")
    public String allOrder(Map<String, Object> model) {
        List<Order> orderList = orderService.allOrder();
        model.put("orders", orderList);
        return "orderList";
    }

    @GetMapping("/category")
    public String categoryList(Model model) {
        List<Category> categoryList = orderService.allCategory();
        model.addAttribute("categories", categoryList);
        model.addAttribute("isCategory", true);
        return "orderList";
    }

    @GetMapping("/category/{categoryId}")
    public String categoryOrder(@PathVariable Long categoryId, Model model) {
        List<Order> orderList = orderService.categoryOrder(categoryId);
        model.addAttribute("orders", orderList);
        return "orderList";
    }

    @GetMapping("/user")
    public String userOrders(@AuthenticationPrincipal UserDetailsImpl userDetails, Map<String, Object> model) {

        List<Order> orderList = orderService.userOrder(userDetails.getId());
        model.put("orders", orderList);
        return "orderList";
    }

    @GetMapping("/create")
    public String createOrder(@RequestParam(required = false) boolean success,  Map<String, Object> model)
    {
        if (success) {
            model.put("message", "Order was created");
        }
        List<Category> categories = orderService.allCategory();
        model.put("categories", categories);
        model.put("isCreate", true);
        return "order";
    }

    @PostMapping("/create")
    public String addOrder(@AuthenticationPrincipal UserDetailsImpl userDetails,
                           @Valid OrderDto orderDto, BindingResult bindingResult,
                           Model model) {
        List<Category> categories = orderService.allCategory();
        model.addAttribute("categories", categories);

        User user = orderService.findUser(userDetails.getUsername());

        model.addAttribute("isCreate", true);

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);

            return "order";
        }

        if (!orderService.createOrder(user, orderDto)) {
            model.addAttribute("orderError", "Order not create!");
            return "order";
        }
        return "redirect:/order/create?success=true";
    }

    @GetMapping("/update/{orderId}")
    public String updateOrder(@RequestParam(required = false) boolean success,
                              @PathVariable(name = "orderId", required = false) Order order,
                              Model model) {
        if (success) {
            model.addAttribute("message", "Order was update");
        }
        List<Category> categories = orderService.allCategory();
        model.addAttribute("categories", categories);
        model.addAttribute("isCreate", false);
        model.addAttribute("order", order);
        return "order";
    }

    @PostMapping("/update")
    public String editOrder(@RequestParam(name = "orderId") Order order,
                            @Valid OrderDto orderDto, BindingResult bindingResult,
                            Model model) {

        List<Category> categories = orderService.allCategory();
        model.addAttribute("categories", categories);
        model.addAttribute("isCreate", false);
        model.addAttribute("order", order);

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);

            return "order";
        }

        if (!orderService.updateOrder(order, orderDto)) {
            model.addAttribute("orderError", "Order not update!");
            return "order";
        }
        return "redirect:/order/update/" + order.getId() + "?success=true";
    }

    @PostMapping("/subscribe")
    public String subscribeOrder(@RequestParam(name = "orderId", required = false) Order order, @RequestParam(name = "userId") User user ) {
        if (!orderService.subscribeUser(order, user)) {

        }
        System.out.println(order.getDescribe());
        return "redirect:/order/category";
    }



}
