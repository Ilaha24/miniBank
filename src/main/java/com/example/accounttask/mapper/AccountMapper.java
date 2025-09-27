package com.example.accounttask.mapper;

import com.example.accounttask.dao.entity.AccountEntity;
import com.example.accounttask.dto.request.create.CreateAccountRequest;
import com.example.accounttask.dto.request.update.UpdateAccountRequest;
import com.example.accounttask.dto.response.AccountResponse;

import java.math.BigDecimal;

import static com.example.accounttask.enums.Status.ACTIVE;

public enum AccountMapper {

    ACCOUNT_MAPPER;

    public AccountEntity toEntity(CreateAccountRequest request) {
        return AccountEntity.builder()
                .userId(request.getUserId())
                .balance(BigDecimal.ZERO)
                .status(ACTIVE)
                .build();
    }

    public void updateOwner(AccountEntity entity, UpdateAccountRequest req) {
        if(req.getUserId() != null) {
            entity.setUserId(req.getUserId());
        }
    }

    public AccountResponse toResponse(AccountEntity entity) {
        return AccountResponse.builder()
                .id(entity.getId())
                .status(entity.getStatus())
                .balance(entity.getBalance())
                .userId(entity.getUserId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

}
