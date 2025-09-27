package com.example.accounttask.dao.repository;

import com.example.accounttask.dao.entity.AccountEntity;
import com.example.accounttask.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    @Query("SELECT a FROM AccountEntity a WHERE a.id = :id AND a.status IN (:statuses)")
    Optional<AccountEntity> findById(@Param("id") Long id,
                                     @Param("statuses") List<Status> statuses);

    @Query("SELECT a FROM AccountEntity a WHERE a.status IN (:statuses)")
    List<AccountEntity> findAll(@Param("statuses") List<Status> statuses);

    @Query("SELECT a FROM AccountEntity a WHERE a.userId = :userId AND a.status IN (:statuses)")
    List<AccountEntity> findAllByUserId(@Param("userId") Long userId,
                                        @Param("statuses") List<Status> statuses);


}
