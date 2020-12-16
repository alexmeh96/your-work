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

    public List<Order> getOrdersByStatus(Status status) {
        return orderRepo.findAllByStatus(status);
    }

    public List<Category> allCategory() {
        return categoryRepo.findAll();
    }


    @Transactional
    public boolean createOrder(User user, OrderDto orderDto) {

        Category category = categoryRepo.findByName(orderDto.getCategoryName()).orElse(null);

        if (category == null) return false;

        Order order = new Order(
                orderDto.getName(),
                orderDto.getDescribe()
        );
        order.setStatus(Status.ACTIVE);

        order.setCategory(category);
        category.getOrders().add(order);

        order.setAuthor(user);
        user.getOwnOrders().add(order);

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
        order.getSubscribers().add(executor);
        executor.getSubscriptions().add(order);
        orderRepo.save(order);
        return true;
    }

    public boolean updateOrder(Order order, OrderDto orderDto) {
        Category category = categoryRepo.findByName(orderDto.getCategoryName()).orElse(null);

        if (category == null) return false;

        order.setCategory(category);
        order.setName(orderDto.getName());
        order.setDescribe(orderDto.getDescribe());

        orderRepo.save(order);

        return true;
    }

    public boolean confirm(Order order, Executor executor) {
        order = orderRepo.findById(order.getId()).orElse(null);
        order.setExecutor(executor);
        order.setOfferExecutor(null);
        order.getSubscribers().clear();
        order.setStatus(Status.PROCESSING);
        orderRepo.save(order);
        return true;
    }

    public boolean existsBySubscribersIsAuthor_Id(Order order, Executor executor) {
        return orderRepo.existsByIdAndSubscribers(order.getId(), executor);
    }

    public boolean doneOrder(Order order) {
        order.setStatus(Status.DONE);
        orderRepo.save(order);
        return true;
    }

    public List<Order> userOrderStatus(Long id, Status status) {

        return orderRepo.findAllByAuthor_IdAndStatus(id, status);
    }

    public void addOffer(Order order, Executor executor) {
        order.setStatus(Status.OFFER);
        order.setOfferExecutor(executor);
        executor.getOffers().add(order);
        orderRepo.save(order);
    }

    public void reject(Order order) {
        order.setStatus(Status.ACTIVE);
        order.setOfferExecutor(null);
        orderRepo.save(order);
    }
}
