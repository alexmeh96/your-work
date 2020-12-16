package com.coder.yourwork.service;

import com.coder.yourwork.dto.ExecutorDto;
import com.coder.yourwork.model.*;
import com.coder.yourwork.repo.CategoryRepo;
import com.coder.yourwork.repo.ExecutorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExecutorService {
    private final ExecutorRepo executorRepo;
    private final CategoryRepo categoryRepo;

    @Autowired
    public ExecutorService(ExecutorRepo executorRepo, CategoryRepo categoryRepo) {
        this.executorRepo = executorRepo;
        this.categoryRepo = categoryRepo;
    }



    public boolean addExecutor(User user,  ExecutorDto executorDto) {
        System.out.println(executorDto.getCategoryName());

        if (executorDto.getCategoryName() == null) return false;

        List<Category> categories = categoryRepo.findByNameIn(executorDto.getCategoryName());

        Executor executor = new Executor(
            executorDto.getFirstName(),
                executorDto.getLastName(),
                executorDto.getDescribe()
        );

        executor.setStatus(Status.ACTIVE);

        executor.setAuth(user);
        user.setExecutor(executor);

        executor.setCategories(categories);

        executorRepo.save(executor);

        return true;
    }

    public Executor getExecutor(Long id) {
        return executorRepo.findAllByAuth_Id(id);
    }

    public List<String> getExecutorCategories(Long id) {
        Executor executor = executorRepo.findAllByAuth_Id(id);
        return executor.getCategories().stream().map(Category::getName).collect(Collectors.toList());
    }

    public boolean updateExecute(Executor executor, ExecutorDto executorDto) {

        List<Category> categoryList = categoryRepo.findByNameIn(executorDto.getCategoryName());

        if (categoryList == null) return false;

        executor.setFirstName(executorDto.getFirstName());
        executor.setLastName(executorDto.getLastName());
        executor.setDescribe(executor.getDescribe());
        executor.setCategories(categoryList);

        executorRepo.save(executor);

        return true;
    }

    public List<Executor> allExecutors() {
        return executorRepo.findAll();
    }

    public List<Executor> statusExecutors(Status status) {
        return executorRepo.findAllByStatus(status);
    }

    public Executor getExecutorByAuthId(Long id) {
        return executorRepo.findAllByAuth_Id(id);
    }

//    public void addOrder(Order order, Executor executor) {
//        order.setStatus(Status.PROCESSING);
//        order.setOfferExecutor(null);
//        order.setExecutor(executor);
//
//    }
}
