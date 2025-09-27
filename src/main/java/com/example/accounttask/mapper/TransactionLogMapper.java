package com.example.accounttask.mapper;

import com.example.accounttask.dao.entity.TransactionLogEntity;
import com.example.accounttask.dto.response.TransactionLogResponse;
import com.example.accounttask.enums.Operation;
import com.example.accounttask.enums.Status;

import java.math.BigDecimal;

public enum TransactionLogMapper {

    TRANSACTION_LOG_MAPPER;

    public TransactionLogResponse toResponse(TransactionLogEntity entity) {
        return TransactionLogResponse.builder()
                .id(entity.getId())
                .status(entity.getStatus())
                .operationType(entity.getOperationType())
                .amount(entity.getAmount())
                .fromAccountId(entity.getFromAccountId())
                .toAccountId(entity.getToAccountId())
                .balanceAfter(entity.getBalanceAfter())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .build();


    }

}
