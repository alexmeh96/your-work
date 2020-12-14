package com.coder.yourwork.repo;

import com.coder.yourwork.model.Executor;
import com.coder.yourwork.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExecutorRepo extends JpaRepository<Executor, Long> {
    boolean existsByAuth_Id(Long id);
    Executor findAllByAuth_Id(Long id);
}
