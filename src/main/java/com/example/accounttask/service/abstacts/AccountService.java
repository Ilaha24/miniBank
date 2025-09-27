package com.example.accounttask.service.abstacts;

import com.example.accounttask.dto.request.DepositWithdrawRequest;
import com.example.accounttask.dto.request.TransferRequest;
import com.example.accounttask.dto.request.create.CreateAccountRequest;
import com.example.accounttask.dto.request.update.UpdateAccountRequest;
import com.example.accounttask.dto.response.AccountResponse;

import java.util.List;

public interface AccountService {

    AccountResponse create(CreateAccountRequest request);

    void deposit(Long id, DepositWithdrawRequest request);

    void withdraw(Long id, DepositWithdrawRequest request);

    void transfer(TransferRequest request);

    AccountResponse getById(Long id);

    List<AccountResponse> getAll();

    List<AccountResponse> listByUserId(Long userId);

    void updateOwner(Long id, UpdateAccountRequest request);

    void delete(Long id);
}
