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
                           @RequestParam(required = false) boolean doneSuccess,
                           @RequestParam(required = false) boolean loseSuccess,
                           @RequestParam(required = false) boolean createExecutor,
                           Model model) {

        if (subscribe) {
            model.addAttribute("message", "Вы откликнулись на задание");
        } else if (confirm) {
            model.addAttribute("message", "Вы подтвердили исполнителя");
        } else if (doneSuccess) {
            model.addAttribute("message", "Вы отметили задание как выполненное");
        } else if (loseSuccess) {
            model.addAttribute("message", "Вы отметили задание как проваленное");
        } else if (createExecutor) {
            model.addAttribute("message", "Создайте исполнителя");
        }

        Executor executor = executorService.getExecutorByAuthId(userDetails.getId());

        if (executor == null) {
            model.addAttribute("isSubscribe", false);
        } else {
            model.addAttribute("isSubscribe", orderService.existsByIdAndSubscribers(order, executor));
        }

        model.addAttribute("order", order);
        model.addAttribute("executor", executor);
        return "orderDir/orderId";
    }

    @GetMapping("/offerList/{executorId}")
    public String getOfferList(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable(name = "executorId") Executor executor, Model model) {
        model.addAttribute("isOffer", true);
        List<Order> orderList = orderService.userOrderStatus(userDetails.getId(), Status.ACTIVE);
        model.addAttribute("orders", orderList);
        model.addAttribute("executor", executor);
        return "orderDir/orderList";
    }

    @PostMapping("/offer")
    public String addOffer(@RequestParam(name = "orderId") Order order, @RequestParam(name = "executorId") Executor executor) {
        orderService.addOffer(order, executor);
        return "redirect:/executor/" + executor.getId() + "?offerSuccess=true";
    }

    @GetMapping("/category/active")
    public String getActiveOrder(@AuthenticationPrincipal UserDetailsImpl userDetails, Map<String, Object> model) {
        List<Order> orderList;


        if (userDetails == null) {
            orderList = orderService.getAllOrdersByStatus(Status.ACTIVE);
        } else {
            Executor executor = executorService.getExecutor(userDetails.getId());
            if (executor == null) {
                orderList = orderService.getOrdersByStatusAndNotAuth(Status.ACTIVE, userDetails.getId());

            } else {
                orderList = orderService.getOrdersByStatus(Status.ACTIVE, userDetails.getId(), executor);

            }
        }
        model.put("orders", orderList);
        return "orderDir/orderList";
    }

    @GetMapping("/category")
    public String categoryList(Model model) {
        List<Category> categoryList = orderService.allCategory();
        model.addAttribute("categories", categoryList);
        model.addAttribute("isCategory", true);
        return "orderDir/orderList";
    }

    @GetMapping("/category/{categoryId}")
    public String getOrdersByCategory(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @PathVariable Long categoryId,
                                      Model model) {
        List<Order> orderList;

        if (userDetails == null) {
            orderList = orderService.categoryOrder(categoryId, Status.ACTIVE, -1L);
        } else {
            orderList = orderService.categoryOrder(categoryId, Status.ACTIVE, userDetails.getId());
        }
        model.addAttribute("orders", orderList);
        return "orderDir/orderList";
    }

    @GetMapping("/user")
    public String ownOrders(@AuthenticationPrincipal UserDetailsImpl userDetails,
                            @RequestParam(required = false) boolean createExecutor,
                            @RequestParam(required = false) boolean cancel,
                            @RequestParam(required = false) boolean delete,
                            Model model) {

        if (createExecutor) {
            model.addAttribute("message", "Создайте исполнителя!");
        } else if(cancel) {
            model.addAttribute("message", "Вы оменили предложение задания");
        } else if (delete) {
            model.addAttribute("message", "Вы удалили задание");

        }

        List<Order> orderList = orderService.userOrder(userDetails.getId());
        model.addAttribute("orders", orderList);
        return "orderDir/myOrders";
    }

    @GetMapping("/other")
    public String otherOrders(@AuthenticationPrincipal UserDetailsImpl userDetails,

                              Map<String, Object> model) {

        Executor executor = executorService.getExecutor(userDetails.getId());
        if (executor == null) {
            return "redirect:/order/user?createExecutor=true";
        }

        List<Order> orderList = orderService.getOtherOrders(executor);
        model.put("orders", orderList);
        model.put("executor", executor);
        return "orderDir/offerOrders";
    }

    @GetMapping("/create")
    public String createOrder(@RequestParam(required = false) boolean success, Map<String, Object> model) {
        if (success) {
            model.put("message", "Order was created");
        }
        List<Category> categories = orderService.allCategory();
        model.put("categories", categories);
        model.put("isCreate", true);
        return "orderDir/order";
    }

    @PostMapping("/create")
    public String addOrder(@AuthenticationPrincipal UserDetailsImpl userDetails,
                           @Valid OrderDto orderDto, BindingResult bindingResult,
                           Model model) {
        List<Category> categories = orderService.allCategory();
        model.addAttribute("categories", categories);

        model.addAttribute("isCreate", true);

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);

            return "orderDir/order";
        }

        if (!orderService.createOrder(userDetails.getId(), orderDto)) {
            model.addAttribute("orderError", "Order not create!");
            return "orderDir/order";
        }
        return "redirect:/order/create?success=true";
    }

    @PostMapping("/delete")
    public String deleteOrder(@AuthenticationPrincipal UserDetailsImpl userDetails,
                           @RequestParam Long orderId) {
        orderService.deleteOrder(orderId, userDetails.getId());

        return "redirect:/order/user?delete=true";
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
        return "orderDir/order";
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

            return "orderDir/order";
        }

        if (!orderService.updateOrder(order, orderDto)) {
            model.addAttribute("orderError", "Order not update!");
            return "orderDir/order";
        }
        return "redirect:/order/update/" + order.getId() + "?success=true";
    }

    @PostMapping("/subscribe")
    public String subscribeOrder(@RequestParam(name = "orderId", required = false) Order order, @RequestParam(name = "userId") User user) {
        if (!orderService.subscribeUser(order, user)) {
            return "redirect:/order/" + order.getId() + "?createExecutor=true";
        }
        return "redirect:/order/" + order.getId() + "?subscribe=true";
    }

    @PostMapping("/confirm")
    public String confirmOrder(@AuthenticationPrincipal UserDetailsImpl userDetails,
                               @RequestParam(name = "orderId") Order order,
                               @RequestParam(name = "subscribeId") Executor executor) {
        if (!orderService.confirm(order, executor, userDetails.getId())) {

        }
        return "redirect:/order/" + order.getId() + "?confirm=true";
    }

    @PostMapping("/done")
    public String doneOrder(@RequestParam(name = "orderId", required = false) Order order,
                            @RequestParam(name = "executorId", required = false) Executor executor,
                            @RequestParam boolean doneSuccess) {
        if (doneSuccess) {
            orderService.doneOrder(order, executor);
            return "redirect:/order/" + order.getId() + "?doneSuccess=true";
        } else {
            orderService.loseOrder(order, executor);
            return "redirect:/order/" + order.getId() + "?loseSuccess=true";
        }
    }

    @PostMapping("/offerAnswer")
    public String answerOffer(@AuthenticationPrincipal UserDetailsImpl userDetails,
                              @RequestParam(name = "orderId") Order order,
                              @RequestParam(name = "executorId", required = false) Executor executor,
                              @RequestParam boolean getOffer) {
        if (getOffer) {
            orderService.confirm(order, executor, userDetails.getId());
            return "redirect:/executor/" + executor.getId() + "?takeOffer=true";
        }
        orderService.reject(order);
        return "redirect:/executor/" + executor.getId() + "?rejectOffer=true";
    }

    @PostMapping("/cancel")
    public String answerOffer(@RequestParam(name = "orderId") Order order){

        orderService.cancelOrder(order);

        return "redirect:/order/user?cancel=true";
    }

}
