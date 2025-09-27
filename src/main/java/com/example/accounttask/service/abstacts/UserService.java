package com.example.accounttask.service.abstacts;

import com.example.accounttask.dto.request.create.CreateUserRequest;
import com.example.accounttask.dto.request.update.UpdateUserRequest;
import com.example.accounttask.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse create(CreateUserRequest request);

    UserResponse update(Long id, UpdateUserRequest request);

    void delete(Long id);

    UserResponse getById(Long id);

    List<UserResponse> getAll();
}
