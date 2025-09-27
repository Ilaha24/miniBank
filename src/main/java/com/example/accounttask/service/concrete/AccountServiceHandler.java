package com.example.accounttask.service.concrete;

import com.example.accounttask.dao.entity.AccountEntity;
import com.example.accounttask.dao.entity.TransactionLogEntity;
import com.example.accounttask.dao.repository.AccountRepository;
import com.example.accounttask.dto.request.DepositWithdrawRequest;
import com.example.accounttask.dto.request.TransferRequest;
import com.example.accounttask.dto.request.create.CreateAccountRequest;
import com.example.accounttask.dto.request.update.UpdateAccountRequest;
import com.example.accounttask.dto.response.AccountResponse;
import com.example.accounttask.enums.Status;
import com.example.accounttask.exception.BadRequestException;
import com.example.accounttask.exception.NotFoundException;
import com.example.accounttask.mapper.AccountMapper;
import com.example.accounttask.service.abstacts.AccountService;
import com.example.accounttask.service.abstacts.TransactionLogService;
import com.example.accounttask.service.abstacts.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.accounttask.enums.Status.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceHandler implements AccountService {



    UserService userService;

    TransactionLogService  transactionLogService;

    AccountRepository accountRepository;


    @Override
    @Transactional
    public AccountResponse create(CreateAccountRequest request) {
        log.info("Create account for userId= {}", request.getUserId());

        userService.getById(request.getUserId());

        AccountEntity entity = AccountMapper.ACCOUNT_MAPPER.toEntity(request);

        AccountEntity saved =  accountRepository.save(entity);

        log.info("Account created: id={} userId={}", saved.getId(), saved.getUserId());

        return AccountMapper.ACCOUNT_MAPPER.toResponse(saved);
    }

    @Override
    @Transactional
    public void deposit(Long id, DepositWithdrawRequest request) {

        log.info("Deposit started: accountId={}, amount={}, desc={}",
               id, request.getAmount(), request.getDescription());

        var active = List.of(ACTIVE, IN_PROGRESS);


        AccountEntity account = accountRepository.findById(id, active)
                .orElseThrow(() -> new NotFoundException("Account not found" + id));

        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Amount must be greater than 0");
        }

        BigDecimal newBalance = account.getBalance().add(request.getAmount());
        account.setBalance(newBalance);
        accountRepository.save(account);

        transactionLogService.logDeposit(
                account.getId(),
                request.getAmount(),
                account.getBalance(),
                request.getDescription()
        );

        log.info("Deposit finished: accountId={}, amount={}, newBalance={}",
                account.getId(), request.getAmount(), account.getBalance());

    }

    @Override
    @Transactional
    public void withdraw(Long id, DepositWithdrawRequest request) {

        log.info("Withdraw started: accountId={}, amount={}", id, request.getAmount());

        var active = List.of(ACTIVE, IN_PROGRESS);

        AccountEntity account = accountRepository.findById(id, active)
                .orElseThrow(() -> new NotFoundException("Account not found" + id));

        if(request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Amount must be greater than 0");
        }

        if(account.getBalance().compareTo(request.getAmount()) < 0) {
            throw new BadRequestException("Amount must be less than 0");
        }

        BigDecimal newBalance = account.getBalance().subtract(request.getAmount());

        account.setBalance(newBalance);

        accountRepository.save(account);

        transactionLogService.logWithdraw(
                account.getId(),
                request.getAmount(),
                account.getBalance(),
                request.getDescription()
        );

        log.info("Withdraw finished: accountId={}, amount={}, newBalance={}",
                account.getId(), request.getAmount(), newBalance);
    }

    @Override
    @Transactional
    public void transfer(TransferRequest request) {

        log.info("Transfer started: from ={}, to={}, amount={}, desc={}",
                request.getFromAccountId(), request.getToAccountId(),
                request.getAmount(), request.getDescription());

        if(request.getFromAccountId() == null || request.getToAccountId() == null) {
            throw new BadRequestException("Both fromAccountId and toAccountId are required");
        }

        if(request.getFromAccountId().equals(request.getToAccountId())) {
            throw new BadRequestException("Transfer between same accounts is not allowed");
        }

        if(request.getAmount() ==null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Amount must be greater than 0");
        }

        var active = List.of(ACTIVE, IN_PROGRESS);

        AccountEntity from = accountRepository.findById(request.getFromAccountId(), active)
                .orElseThrow(() -> new NotFoundException("Account not found" + request.getFromAccountId()));

        AccountEntity to = accountRepository.findById(request.getToAccountId(), active)
                .orElseThrow(() -> new NotFoundException("Account not found" + request.getToAccountId()));

        if(from.getBalance().compareTo(request.getAmount()) < 0) {
            throw new BadRequestException("Insufficient balance");
        }

        from.setBalance(from.getBalance().subtract(request.getAmount()));
        to.setBalance(to.getBalance().add(request.getAmount()));

        accountRepository.save(from);
        accountRepository.save(to);

        transactionLogService.logTransfer(
                from.getId(),
                to.getId(),
                request.getAmount(),
                from.getBalance(),
                to.getBalance(),
                request.getDescription()
        );

        log.info("Transfer finished: from={} to={} amount={} fromBal={} toBal={}",
                from.getId(), to.getId(), request.getAmount(), from.getBalance(), to.getBalance());

    }

    @Override
    public AccountResponse getById(Long id) {

        log.info("Fetching account by id: accountId={}", id);

        var active = List.of(ACTIVE, IN_PROGRESS);

        AccountEntity entity = accountRepository.findById(id, active)
                .orElseThrow(() -> new NotFoundException("Account not found" + id));

        AccountResponse response = AccountMapper.ACCOUNT_MAPPER.toResponse(entity);

        log.info("Fetched account by id: accountId={} userId={} balance={}",
                response.getId(), response.getUserId(), response.getBalance());
        return response;
    }

    @Override
    public List<AccountResponse> getAll() {

        log.info("Fetching all accounts");

        var active = List.of(ACTIVE, IN_PROGRESS);

        List<AccountEntity> entities = accountRepository.findAll(active);

        List<AccountResponse> responses = entities.stream()
                .map(AccountMapper.ACCOUNT_MAPPER::toResponse)
                .collect(Collectors.toList());

        log.info("Fetched {} accounts: ", responses.size());

        return responses;
    }

    @Override
    public List<AccountResponse> listByUserId(Long userId) {

        log.info("Fetching all accounts by userId: {}", userId);

        var active = List.of(ACTIVE, IN_PROGRESS);

        List<AccountEntity> entities = accountRepository.findAllByUserId(userId, active);

        List<AccountResponse> responses = entities.stream()
                .map(AccountMapper.ACCOUNT_MAPPER::toResponse)
                .toList();

        log.info("Fetched {} accounts for userId={}", responses.size(), userId);

        return responses;
    }

    @Override
    public void updateOwner(Long id, UpdateAccountRequest request) {
        log.info("UpdateOwner started: accountId={}, newUserId={}",
                id, request.getUserId());

        if (request.getUserId() == null) {
            throw new BadRequestException("new userId is required");
        }

        var active = List.of(ACTIVE, IN_PROGRESS);

        AccountEntity account = accountRepository.findById(id, active)
                .orElseThrow(() -> new NotFoundException("Account not found: " + id));

        if (request.getUserId().equals(account.getUserId())) {
            throw new BadRequestException("Account already belongs to userId=" + request.getUserId());
        }

        userService.getById(request.getUserId());

        account.setUserId(request.getUserId());

        accountRepository.save(account);

        log.info("Owner updated: accountId={} -> newUserId={}", id, request.getUserId());


    }

    @Override
    public void delete(Long id) {

        log.info("Soft delete started: accountId={}", id);

        var active = List.of(ACTIVE, IN_PROGRESS);
        AccountEntity account = accountRepository.findById(id, active)
                .orElseThrow(() -> new NotFoundException("Account not found: " + id));

        account.setStatus(DELETED);
        accountRepository.save(account);

        log.info("Soft delete finished: accountId={} (status=DELETED)", id);

    }
}
