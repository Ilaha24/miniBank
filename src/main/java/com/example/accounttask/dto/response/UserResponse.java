package com.example.accounttask.dto.response;

import com.example.accounttask.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class UserResponse {

    Long id;

    Status status;

    String fullName;

    Integer age;

    String email;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;
}
