package com.example.accounttask.controller;

import com.example.accounttask.dto.request.DepositWithdrawRequest;
import com.example.accounttask.dto.request.TransferRequest;
import com.example.accounttask.dto.request.create.CreateAccountRequest;
import com.example.accounttask.dto.request.update.UpdateAccountRequest;
import com.example.accounttask.dto.response.AccountResponse;
import com.example.accounttask.service.abstacts.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/v1/accounts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {

    AccountService accountService;

    @Operation(summary = "createAccount", description = "Yeni account yaradır")
    @PostMapping
    @ResponseStatus(CREATED)
    public AccountResponse create(@Valid @RequestBody CreateAccountRequest request) {
        log.info("REST: create account for userId={}", request.getUserId());
        return accountService.create(request);
    }

    @Operation(summary = "depositToAccount", description = "Mövcud account-a pul yatırır")
    @PostMapping("/{id}/deposit")
    @ResponseStatus(NO_CONTENT)
    public void deposit(@PathVariable Long id,
                        @Valid @RequestBody DepositWithdrawRequest request) {
        log.info("REST: deposit into accountId={} amount={}", id, request.getAmount());
        accountService.deposit(id, request);
    }

    @Operation(summary = "withdrawFromAccount", description = "Mövcud account-dan pul çıxarır")
    @PostMapping("/{id}/withdraw")
    @ResponseStatus(NO_CONTENT)
    public void withdraw(@PathVariable Long id,
                         @Valid @RequestBody DepositWithdrawRequest request) {
        log.info("REST: withdraw from accountId={} amount={}", id, request.getAmount());
        accountService.withdraw(id, request);
    }

    @Operation(summary = "transferBetweenAccounts", description = "Bir account-dan digərinə pul köçürür")
    @PostMapping("/transfer")
    @ResponseStatus(NO_CONTENT)
    public void transfer(@Valid @RequestBody TransferRequest request) {
        log.info("REST: transfer from={} to={} amount={}",
                request.getFromAccountId(), request.getToAccountId(), request.getAmount());
        accountService.transfer(request);
    }

    @Operation(summary = "getAccountById", description = "ID-yə görə account-u gətirir")
    @GetMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public AccountResponse getById(@PathVariable Long id) {
        log.info("REST: get account {}", id);
        return accountService.getById(id);
    }

    @Operation(summary = "getAllAccounts", description = "Bütün account-ları qaytarır")
    @GetMapping
    @ResponseStatus(NO_CONTENT)
    public List<AccountResponse> getAll() {
        log.info("REST: get all accounts");
        return accountService.getAll();
    }

    @Operation(summary = "listAccountsByUserId", description = "Verilən userId üçün account-ları qaytarır")
    @GetMapping("/user/{userId}")
    @ResponseStatus(NO_CONTENT)
    public List<AccountResponse> listByUserId(@PathVariable Long userId) {
        log.info("REST: get accounts for userId={}", userId);
        return accountService.listByUserId(userId);
    }

    @Operation(summary = "updateAccountOwner", description = "Account-un sahibini dəyişir")
    @PutMapping("/{id}/owner")
    @ResponseStatus(NO_CONTENT)
    public void updateOwner(@PathVariable Long id,
                            @Valid @RequestBody UpdateAccountRequest request) {
        log.info("REST: update account owner id={} -> newUserId={}", id, request.getUserId());
        accountService.updateOwner(id, request);
    }

    @Operation(summary = "deleteAccount", description = "Account-u silir (soft delete)")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.info("REST: delete account {}", id);
        accountService.delete(id);
    }
}
