package com.coder.yourwork.service;

import com.coder.yourwork.dto.ExecutorDto;
import com.coder.yourwork.model.*;
import com.coder.yourwork.repo.CategoryRepo;
import com.coder.yourwork.repo.ExecutorRepo;
import com.coder.yourwork.repo.ImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExecutorService {
    private final ExecutorRepo executorRepo;
    private final CategoryRepo categoryRepo;
    @Autowired
    private ImageRepo imageRepo;
    @Autowired
    private ImageService imageService;

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
            executorDto.getName(),
                executorDto.getDescribe(),
                executorDto.isActive()
        );
        executor.setEmail(executorDto.getEmail());
        executor.setPhone(executorDto.getPhone());

        Image image = null;
        try {
            image = imageService.loadImage(executorDto.getAvatar());
            Long id = imageRepo.save(image).getId();
            executor.setAvatarId(id);

        } catch (IOException e) {
            e.printStackTrace();
        }

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

        executor.setName(executorDto.getName());
        executor.setDescribe(executorDto.getDescribe());
        executor.setCategories(categoryList);
        executor.setActive(executorDto.isActive());
        executor.setPhone(executorDto.getPhone());
        executor.setEmail(executorDto.getEmail());

        if (executorDto.getAvatar() !=null ) {
            Image image = null;
            try {
                image = imageService.loadImage(executorDto.getAvatar());
                Long id = imageRepo.save(image).getId();
                executor.setAvatarId(id);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        executorRepo.save(executor);

        return true;
    }

    public List<Executor> allExecutors() {
        return executorRepo.findAll();
    }

    public List<Executor> activeExecutors() {
        return executorRepo.findAllByActiveTrue();
    }

    public Executor getExecutorByAuthId(Long id) {
        return executorRepo.findAllByAuth_Id(id);
    }

    public List<Executor> getFirstExecutors() {
        return executorRepo.findFirst5ByOrderByProfile_amountExecutedOrdersSuccess();
    }



//    public void addOrder(Order order, Executor executor) {
//        order.setStatus(Status.PROCESSING);
//        order.setOfferExecutor(null);
//        order.setExecutor(executor);
//
//    }
}
