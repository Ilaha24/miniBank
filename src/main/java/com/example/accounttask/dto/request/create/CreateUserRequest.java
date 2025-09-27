package com.example.accounttask.dto.request.create;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class CreateUserRequest {

    @NotBlank(message = "Name can not be blank")
    @Size(max = 60)
    String fullName;

    @NotNull(message = "Age can not be null")
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 100, message = "Age must be under 100")
    Integer age;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email can not be blank")
    @Size(max = 160, message = "Email length must be ≤ 160")
    String email;
}
