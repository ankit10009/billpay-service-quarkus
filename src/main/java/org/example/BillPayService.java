package org.example;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class BillPayService {

//@RegisterRestClient(baseUri = "https://stage.code.quarkus.io/api")

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

