package com.coder.yourwork.repo;

import com.coder.yourwork.model.Category;
import com.coder.yourwork.model.Executor;
import com.coder.yourwork.model.Order;
import com.coder.yourwork.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findAllByAuthor_Id(Long id);
    List<Order> findAllByAuthor_IdAndStatus(Long id, Status status);
    List<Order> findAllByCategory_Id(Long id);
    List<Order> findAllByStatus(Status status);
    boolean existsByIdAndSubscribersContaining(Long id, Executor executor);
    List<Order> findAllByStatusAndAuthor_IdNot(Status status, Long id);
    List<Order> findAllByCategory_IdAndStatusAndAuthor_IdNot(Long id, Status status, Long userId);

    List<Order> findAllByExecutor_IdOrSubscribersContainingOrOfferExecutor_Id(Long id1, Executor executor, Long id3);
    List<Order> findAllByStatusAndAuthor_IdNotAndSubscribersNotContaining(Status status, Long id, Executor executor);

    List<Order> findFirst5ByOrderByDate();


}
