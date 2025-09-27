package com.example.accounttask.controller;

import com.example.accounttask.dto.request.create.CreateUserRequest;
import com.example.accounttask.dto.request.update.UpdateUserRequest;
import com.example.accounttask.dto.response.UserResponse;
import com.example.accounttask.service.abstacts.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @Operation(summary = "createUser",
            description = "Yeni istifadəçi yaradır",
            operationId = "createUser")
    @PostMapping
    @ResponseStatus(CREATED)
    public UserResponse create(@Valid @RequestBody CreateUserRequest request) {
        log.info("REST: create user {}", request.getEmail());
        return userService.create(request);
    }

    @Operation(summary = "updateUser",
            description = "Mövcud istifadəçinin məlumatlarını yeniləyir",
            operationId = "updateUser"
    )
    @PutMapping("/{id}")
    @ResponseStatus(OK)
    public UserResponse update(@PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request) {
        log.info("REST: update user {}", id);
        return userService.update(id, request);
    }

    @Operation(summary = "deleteUser",
            description = "İstifadəçini silir (soft)",
            operationId = "deleteUser")
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.info("REST: delete user {}", id);
        userService.delete(id);
    }

    @Operation(summary = "getUserById",
            description = "ID-yə görə istifadəçini gətirir",
            operationId = "getUserById")
    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id) {
        log.info("REST: get user {}", id);
        return userService.getById(id);
    }

    @Operation(summary = "listUsers",
            description = "İstifadəçilərin siyahısı",
            operationId = "listUsers")
    @GetMapping
    public List<UserResponse> getAll() {
        log.info("REST: get all users");
        return userService.getAll();
    }
}
