package com.example.accounttask.dao.entity;

import com.example.accounttask.enums.Operation;
import com.example.accounttask.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
@Table(name = "transaction_logs")
public class TransactionLogEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    @Enumerated(STRING)
    Status status;

    @Enumerated(STRING)
    Operation operationType;

    BigDecimal amount;

    Long fromAccountId;

    Long toAccountId;

    BigDecimal balanceAfter;

    String description;

    @CreationTimestamp
    LocalDateTime createdAt;

}
