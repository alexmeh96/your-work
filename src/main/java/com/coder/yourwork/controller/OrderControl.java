package com.coder.yourwork.controller;

import com.coder.yourwork.dto.OrderDto;
import com.coder.yourwork.model.*;
import com.coder.yourwork.service.ExecutorService;
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
    private ExecutorService executorService;

    @Autowired
    public OrderControl(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{orderId}")
    public String getOrder(@AuthenticationPrincipal UserDetailsImpl userDetails,
                           @PathVariable(name = "orderId") Order order,
                           @RequestParam(required = false) boolean subscribe,
                           @RequestParam(required = false) boolean confirm,
                           @RequestParam(required = false) boolean done,
                           Model model) {

        if (subscribe) {
            model.addAttribute("message", "Вы откликнулись на задание");
        } else if(confirm) {
            model.addAttribute("message", "Вы подтвердили исполнителя");
        } else if(done) {
            model.addAttribute("message", "Вы отметили задание как выполненное");
        }

        Executor executor = executorService.getExecutorByAuthId(userDetails.getId());

        model.addAttribute("order", order);
        model.addAttribute("executor", executor);
        model.addAttribute("isSubscribe", orderService.existsBySubscribersIsAuthor_Id(order, executor));
        return "orderId";
    }

    @GetMapping("/offerList/{executorId}")
    public String getOfferList(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable(name = "executorId") Executor executor, Model model) {
        model.addAttribute("isOffer", true);
        List<Order> orderList = orderService.userOrderStatus(userDetails.getId(), Status.ACTIVE);
        model.addAttribute("orders", orderList);
        model.addAttribute("executor", executor);
        return "orderList";
    }

    @PostMapping("/offer")
    public String addOffer(@RequestParam(name = "orderId") Order order, @RequestParam(name = "executorId") Executor executor ) {
        orderService.addOffer(order, executor);
        return "redirect:/executor/" + executor.getId() + "?offerSuccess=true";
    }

    @GetMapping("/category/active")
    public String getActiveOrder(Map<String, Object> model) {
        List<Order> orderList = orderService.getOrdersByStatus(Status.ACTIVE);
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
    public String getOrdersByCategory(@PathVariable Long categoryId, Model model) {
        List<Order> orderList = orderService.categoryOrder(categoryId);
        model.addAttribute("orders", orderList);
        return "orderList";
    }

    @GetMapping("/user")
    public String ownOrders(@AuthenticationPrincipal UserDetailsImpl userDetails, Map<String, Object> model) {

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
        return "redirect:/order/"+order.getId()+"?subscribe=true";
    }

    @PostMapping("/confirm")
    public String confirmOrder(@RequestParam(name = "orderId") Order order, @RequestParam(name = "subscribeId") Executor executor ) {
        if (!orderService.confirm(order, executor)) {

        }
        return "redirect:/order/"+order.getId()+"?confirm=true";
    }

    @PostMapping("/done")
    public String doneOrder(@RequestParam(name = "orderId", required = false) Order order) {
        if (!orderService.doneOrder(order)) {

        }
        return "redirect:/order/"+order.getId()+"?done=true";
    }

    @PostMapping("/offerAnswer")
    public String answerOffer(@RequestParam(name = "orderId") Order order,
                              @RequestParam(name = "executorId", required = false) Executor executor,
                              @RequestParam boolean getOffer) {
        if (getOffer) {
            orderService.confirm(order, executor);
            return "redirect:/executor/" + executor.getId() + "?takeOffer=true";
        }
        orderService.reject(order);
        return "redirect:/executor/" + executor.getId() + "?rejectOffer=true";
    }



}
