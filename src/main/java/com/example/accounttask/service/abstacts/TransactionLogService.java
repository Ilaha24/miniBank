package com.example.accounttask.service.abstacts;

import com.example.accounttask.dto.response.TransactionLogResponse;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionLogService {

    void logDeposit(Long toAccountId,
                    BigDecimal amount,
                    BigDecimal toBalanceAfter,
                    String description);

    void logWithdraw(Long fromAccountId,
                     BigDecimal amount,
                     BigDecimal fromBalanceAfter,
                     String description);

    void logTransfer(Long fromAccountId,
                     Long toAccountId,
                     BigDecimal amount,
                     BigDecimal fromBalanceAfter,
                     BigDecimal toBalanceAfter,
                     String description);

    List<TransactionLogResponse> listByAccount(Long id);
}
