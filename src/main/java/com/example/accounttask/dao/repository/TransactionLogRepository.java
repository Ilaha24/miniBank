package com.example.accounttask.dao.repository;

import com.example.accounttask.dao.entity.TransactionLogEntity;
import com.example.accounttask.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransactionLogRepository extends JpaRepository<TransactionLogEntity, Long> {

    @Query("SELECT t FROM TransactionLogEntity t WHERE t.id = :id AND t.status IN (:statuses)")
    Optional<TransactionLogEntity> findById(@Param("id") Long id, @Param("statuses") List<Status> statuses);

    @Query("SELECT t FROM TransactionLogEntity t WHERE t.status IN (:statuses) ORDER BY t.createdAt DESC")
    List<TransactionLogEntity> findAll(@Param("statuses") List<Status> statuses);

    @Query("""
       SELECT t FROM TransactionLogEntity t
       WHERE (t.fromAccountId = :accountId OR t.toAccountId = :accountId)
         AND t.status IN (:statuses)
       ORDER BY t.createdAt DESC
       """)
    List<TransactionLogEntity> findAllByAccountId(@Param("accountId") Long accountId,
                                                  @Param("statuses") List<Status> statuses);
}
