package com.coder.yourwork.service;

import com.coder.yourwork.dto.OrderDto;
import com.coder.yourwork.model.*;
import com.coder.yourwork.repo.CategoryRepo;
import com.coder.yourwork.repo.OrderRepo;
import com.coder.yourwork.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepo orderRepo;
    private final CategoryRepo categoryRepo;
    private final UserRepo userRepo;

    @Autowired
    public OrderService(OrderRepo orderRepo, CategoryRepo categoryRepo, UserRepo userRepo) {
        this.orderRepo = orderRepo;
        this.categoryRepo = categoryRepo;
        this.userRepo = userRepo;
    }


    public User findUser(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

    public List<Order> allOrder() {
        return orderRepo.findAll();
    }

    public List<Category> allCategory() {
        return categoryRepo.findAll();
    }


    @Transactional
    public boolean createOrder(User user, OrderDto orderDto) {

        Category category = categoryRepo.findByName(orderDto.getCategoryName()).orElse(null);

        if (category == null) return false;

        Order order = new Order(
                orderDto.getDescribe()
        );

        order.setCategory(category);
        category.getOrders().add(order);

        order.setAuthor(user);
        user.getOrders().add(order);

        orderRepo.save(order);
        return true;

    }

    public List<Order> userOrder(Long userId) {
        return orderRepo.findAllByAuthor_Id(userId);
    }

    public List<Order> categoryOrder(Long categoryId) {
        return orderRepo.findAllByCategory_Id(categoryId);
    }

    public boolean subscribeUser(Order order, User user) {
        Executor executor = user.getExecutor();
        order.getExecutors().add(executor);
        executor.getOrders().add(order);
        orderRepo.save(order);
        return true;
    }

    public boolean updateOrder(Order order, OrderDto orderDto) {
        Category category = categoryRepo.findByName(orderDto.getCategoryName()).orElse(null);

        if (category == null) return false;

        order.setCategory(category);
        order.setDescribe(orderDto.getDescribe());

        orderRepo.save(order);

        return true;
    }
}
