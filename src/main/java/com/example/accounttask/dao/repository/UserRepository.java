package com.example.accounttask.dao.repository;

import com.example.accounttask.dao.entity.UserEntity;
import com.example.accounttask.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);

    @Query("SELECT u FROM UserEntity u WHERE u.id = :id AND u.status IN (:statuses)")
    Optional<UserEntity> findById(@Param("id") Long id,
                                  @Param("statuses") List<Status> statuses);

    @Query("SELECT u FROM UserEntity u WHERE u.status IN (:statuses)")
    List<UserEntity> findAll(@Param("statuses") List<Status> statuses);
}
