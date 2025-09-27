package com.example.accounttask.service.concrete;

import com.example.accounttask.dao.entity.TransactionLogEntity;
import com.example.accounttask.dao.repository.TransactionLogRepository;
import com.example.accounttask.dto.response.TransactionLogResponse;
import com.example.accounttask.mapper.TransactionLogMapper;
import com.example.accounttask.service.abstacts.TransactionLogService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.example.accounttask.enums.Operation.*;
import static com.example.accounttask.enums.Status.*;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionLogHandler implements TransactionLogService {

    TransactionLogRepository transactionLogRepository;


    @Override

        public void logDeposit(Long toAccountId,
                BigDecimal amount,
                BigDecimal toBalanceAfter,
                String description) {

            TransactionLogEntity e = TransactionLogEntity.builder()
                    .status(ACTIVE)
                    .operationType(DEPOSIT)
                    .amount(amount)
                    .fromAccountId(null)
                    .toAccountId(toAccountId)
                    .balanceAfterFromAccount(null)
                    .balanceAfterToAccount(toBalanceAfter)
                    .description(description)
                    .build();

            transactionLogRepository.save(e);
            log.debug("Logged DEPOSIT: to={}, amount={}, toBalanceAfter={}, logId={}",
                    toAccountId, amount, toBalanceAfter, e.getId());
    }

    @Override
    public void logWithdraw(Long fromAccountId, BigDecimal amount, BigDecimal fromBalanceAfter, String description) {
        TransactionLogEntity e = TransactionLogEntity.builder()
                .status(ACTIVE)
                .operationType(WITHDRAW)
                .amount(amount)
                .fromAccountId(fromAccountId)
                .toAccountId(null)
                .balanceAfterFromAccount(fromBalanceAfter)
                .balanceAfterToAccount(null)
                .description(description)
                .build();

        transactionLogRepository.save(e);
        log.debug("Logged WITHDRAW: from={}, amount={}, fromAfter={}, logId={}",
                fromAccountId, amount, fromBalanceAfter, e.getId());
    }

    @Override
    public void logTransfer(Long fromAccountId, Long toAccountId, BigDecimal amount, BigDecimal fromBalanceAfter, BigDecimal toBalanceAfter, String description) {
        TransactionLogEntity e = TransactionLogEntity.builder()
                .status(ACTIVE)
                .operationType(TRANSFER)
                .amount(amount)
                .fromAccountId(fromAccountId)
                .toAccountId(toAccountId)
                .balanceAfterFromAccount(fromBalanceAfter)
                .balanceAfterToAccount(toBalanceAfter)
                .description(description)
                .build();

        transactionLogRepository.save(e);
        log.debug("Logged TRANSFER: from={} to={} amount={} fromAfter={} toAfter={} logId={}",
                fromAccountId, toAccountId, amount, fromBalanceAfter, toBalanceAfter, e.getId());
    }

    @Override
    public List<TransactionLogResponse> listByAccount(Long id) {
        var activeStatuses = List.of(ACTIVE, IN_PROGRESS);
        return transactionLogRepository.findAllByAccountId(id, activeStatuses)
                .stream()
                .map(TransactionLogMapper.TRANSACTION_LOG_MAPPER::toResponse)
                .toList();
    }
}
