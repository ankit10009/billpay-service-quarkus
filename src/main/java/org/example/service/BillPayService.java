package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.example.model.Account;
import org.example.proxy.AccountServiceClient;
import org.example.proxy.PayeeServiceClient;
import org.example.model.Payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class BillPayService {

@RestClient
PayeeServiceClient payeeService;

@RestClient
AccountServiceClient accountService;

@Transactional
public Payment makePayment(Long payeeId, Long accountId, BigDecimal amount) {
    // Validate payee
    payeeService.getPayeeById(payeeId);

    // Validate account and balance
    Account account = accountService.getAccountById(accountId);
    if (account.balance.compareTo(amount) < 0) {
        throw new RuntimeException("Insufficient balance");
    }

    // Deduct balance
    accountService.updateAccountBalance(accountId, account.balance.subtract(amount));

    // Record payment
    Payment payment = new Payment();
    payment.payeeId = payeeId;
    payment.accountId = accountId;
    payment.amount = amount;
    payment.paymentDate = LocalDateTime.now();
    payment.persist();

    return payment;
}

public List<Payment> getPaymentHistory() {
    return Payment.listAll();
}
}

