package com.example.accounttask.controller;

import com.example.accounttask.dto.response.TransactionLogResponse;
import com.example.accounttask.service.abstacts.TransactionLogService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/transactions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
public class TransactionLogController {

    TransactionLogService transactionLogService;

    @Operation(
            summary = "listTransactionLogsByAccount",
            description = "Verilmiş accountId üçün bütün transaction log-ları qaytarır",
            operationId = "listTransactionLogsByAccount"
    )
    @GetMapping("/account/{accountId}")
    public List<TransactionLogResponse> listByAccount(@PathVariable Long accountId) {
        log.info("REST: get transaction logs for accountId={}", accountId);
        return transactionLogService.listByAccount(accountId);
    }
}
