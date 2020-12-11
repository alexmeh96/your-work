package com.coder.yourwork.repo;

import com.coder.yourwork.model.Category;
import com.coder.yourwork.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findAllByAuthor_Id(Long id);
}
