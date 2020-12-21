package com.coder.yourwork.service;

import com.coder.yourwork.dto.OrderDto;
import com.coder.yourwork.model.*;
import com.coder.yourwork.repo.CategoryRepo;
import com.coder.yourwork.repo.OrderRepo;
import com.coder.yourwork.repo.ProfileRepo;
import com.coder.yourwork.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepo orderRepo;
    private final CategoryRepo categoryRepo;
    private final UserRepo userRepo;
    private final ProfileRepo profileRepo;

    @Autowired
    public OrderService(OrderRepo orderRepo, CategoryRepo categoryRepo, UserRepo userRepo, ProfileRepo profileRepo) {
        this.orderRepo = orderRepo;
        this.categoryRepo = categoryRepo;
        this.userRepo = userRepo;
        this.profileRepo = profileRepo;
    }


    public User findUser(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

    public List<Order> allOrder() {
        return orderRepo.findAll();
    }

    public List<Order> getOrdersByStatus(Status status, Long id, Executor executor) {
        return orderRepo.findAllByStatusAndAuthor_IdNotAndSubscribersNotContaining(status, id, executor);
    }

    public List<Category> allCategory() {
        return categoryRepo.findAll();
    }


    @Transactional
    public boolean createOrder(Long userId, OrderDto orderDto) {

        User user = userRepo.findById(userId).orElse(null);
        Profile profile = user.getProfile();
        profile.setAmountMakeOrders(profile.getAmountMakeOrders() + 1);

        Category category = categoryRepo.findByName(orderDto.getCategoryName()).orElse(null);

        if (category == null) return false;

        Order order = new Order(
                orderDto.getName(),
                orderDto.getDescribe()
        );
        order.setNameOwner(orderDto.getNameOwner());
        order.setEmailOwner(orderDto.getEmailOwner());
        order.setPhoneOwner(orderDto.getPhoneOwner());
        order.setPrice(orderDto.getPrice());
        order.setDate(new Date());
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

    public List<Order> categoryOrder(Long categoryId, Status status, Long userId) {
        return orderRepo.findAllByCategory_IdAndStatusAndAuthor_IdNot(categoryId, status, userId);
    }

    public boolean subscribeUser(Order order, User user) {
        Executor executor = user.getExecutor();
        if (executor == null) {
            return false;
        }
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

    public boolean confirm(Order order, Executor executor, Long userId) {
        Profile profile = profileRepo.getProfileByAuth_Id(userId).orElse(null);
        profile.setAmountExecutionOrders(profile.getAmountExecutionOrders() + 1);
        order = orderRepo.findById(order.getId()).orElse(null);
        order.setExecutor(executor);
        order.setOfferExecutor(null);
        order.getSubscribers().clear();
        order.setStatus(Status.PROCESSING);
//        profileRepo.save(profile);
        orderRepo.save(order);
        return true;
    }

    public boolean existsByIdAndSubscribers(Order order, Executor executor) {
        return orderRepo.existsByIdAndSubscribersContaining(order.getId(), executor);
    }

    public void doneOrder(Order order, Executor executor) {
        Profile profile = profileRepo.getProfileByAuth_Id(executor.getAuth().getId()).orElse(null);
        profile.setAmountExecutedOrdersSuccess(profile.getAmountExecutedOrdersSuccess() + 1);
        profile.setAmountExecutionOrders(profile.getAmountExecutionOrders() - 1);
        order.setStatus(Status.DONE);
        orderRepo.save(order);
//        profileRepo.save(profile);
    }

    public void loseOrder(Order order, Executor executor) {
        Profile profile = profileRepo.getProfileByAuth_Id(executor.getAuth().getId()).orElse(null);
        profile.setAmountExecutedOrdersWrong(profile.getAmountExecutedOrdersWrong() + 1);
        profile.setAmountExecutionOrders(profile.getAmountExecutionOrders() - 1);
        order.setStatus(Status.LOSE);
        orderRepo.save(order);
//        profileRepo.save(profile);
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

    public List<Order> getOtherOrders(Executor executor) {
        return orderRepo.findAllByExecutor_IdOrSubscribersContainingOrOfferExecutor_Id(executor.getId(), executor, executor.getId());
    }

    public List<Order> getAllOrdersByStatus(Status status) {
        return orderRepo.findAllByStatus(status);
    }

    public List<Order> getOrdersByStatusAndNotAuth(Status status, Long id) {
        return orderRepo.findAllByStatusAndAuthor_IdNot(status, id);
    }

    public void cancelOrder(Order order) {
        order.setStatus(Status.ACTIVE);
        order.setOfferExecutor(null);
        orderRepo.save(order);
    }

    public void deleteOrder(Long orderId, Long userId) {

        Profile profile = profileRepo.getProfileByAuth_Id(userId).orElse(null);

        profile.setAmountMakeOrders(profile.getAmountMakeOrders()-1);
//        profileRepo.save(profile);

        orderRepo.deleteById(orderId);
    }

    public List<Order> getFirstOrders() {
        return orderRepo.findFirst5ByOrderByDate();
    }
}
