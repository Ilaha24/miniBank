package com.example.accounttask.service.concrete;

import com.example.accounttask.dao.entity.UserEntity;
import com.example.accounttask.dao.repository.UserRepository;
import com.example.accounttask.dto.request.create.CreateUserRequest;
import com.example.accounttask.dto.request.update.UpdateUserRequest;
import com.example.accounttask.dto.response.UserResponse;
import com.example.accounttask.exception.BadRequestException;
import com.example.accounttask.exception.NotFoundException;
import com.example.accounttask.mapper.UserMapper;
import com.example.accounttask.service.abstacts.UserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.accounttask.enums.Status.*;
import static lombok.AccessLevel.*;
@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)

public class UserServiceHandler implements UserService {

    UserRepository userRepository;

    @Override
    public UserResponse create(CreateUserRequest request) {

        log.info("Creating user with email= {} and fullName= {}",
                request.getEmail(),  request.getFullName());

        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("User creation failed: email {} already exists", request.getEmail());
            throw new BadRequestException("Email already exists: " + request.getEmail());
        }

        UserEntity entity = UserMapper.USER_MAPPER.toEntityCreate(request);

        UserEntity saved = userRepository.save(entity);

        log.info("User created successfully with id= {}", saved.getId());

        return UserMapper.USER_MAPPER.toResponse(saved);
    }

    @Override
    public UserResponse update(Long id, UpdateUserRequest request) {

        log.info("Updating user with id= {}", id);

        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        UserMapper.USER_MAPPER.updateEntity(entity,  request);

        UserEntity saved = userRepository.save(entity);

        log.info("User updated successfully with id= {}", saved.getId());


        return UserMapper.USER_MAPPER.toResponse(saved);
    }

    @Override
    public void delete(Long id) {

        log.info("Deleting user with id= {}", id);

        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        entity.setStatus(DELETED);

        userRepository.save(entity);

        log.info("User deleted successfully with id= {}", id);
    }

    @Override
    public UserResponse getById(Long id) {
        log.info("Fetching user by id={}", id);

        var active = List.of(ACTIVE, IN_PROGRESS);

        UserEntity entity = userRepository.findById(id, active)
                .orElseThrow(() -> new NotFoundException("User not found: " + id));

        UserResponse resp = UserMapper.USER_MAPPER.toResponse(entity);

        log.info("Fetched user id={} (status={})", resp.getId(), resp.getStatus());

        return resp;
    }

    @Override
        public List<UserResponse> getAll() {
            log.info("Fetching all users");

        var active = List.of(ACTIVE, IN_PROGRESS);

            List<UserResponse> list = userRepository.findAll(active)
                .stream()
                .map(UserMapper.USER_MAPPER::toResponse)
                .toList();

        log.info("Fetched {} users", list.size());

        return list;
    }
}
