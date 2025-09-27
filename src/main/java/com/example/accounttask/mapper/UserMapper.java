package com.example.accounttask.mapper;

import com.example.accounttask.dao.entity.UserEntity;
import com.example.accounttask.dto.request.create.CreateUserRequest;
import com.example.accounttask.dto.request.update.UpdateUserRequest;
import com.example.accounttask.dto.response.UserResponse;
import com.example.accounttask.enums.Status;

import static com.example.accounttask.enums.Status.*;

public enum UserMapper {
    USER_MAPPER;

    public UserEntity toEntityCreate(CreateUserRequest request) {
        return UserEntity.builder()
                .status(ACTIVE)
                .fullName(request.getFullName())
                .age(request.getAge())
                .email(request.getEmail())
                .build();
    }

    public void updateEntity(UserEntity entity, UpdateUserRequest request) {

        entity.setStatus(Status.IN_PROGRESS);

        if (request.getFullName() != null) {
            entity.setFullName(request.getFullName());
        }
        if (request.getAge() != null) {
            entity.setAge(request.getAge());
        }
        if (request.getEmail() != null) {
            entity.setEmail(request.getEmail());
        }
    }

    public UserResponse toResponse(UserEntity entity) {
        return UserResponse.builder()
                .id(entity.getId())
                .status(entity.getStatus())
                .fullName(entity.getFullName())
                .age(entity.getAge())
                .email(entity.getEmail())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
