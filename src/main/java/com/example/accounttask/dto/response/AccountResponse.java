package com.example.accounttask.dto.response;

import com.example.accounttask.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class AccountResponse {

    Long id;

    Status status;

    BigDecimal balance;

    Long userId;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;
}
