package com.bank;

import com.bank.dto.*;
import com.bank.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class BankServiceIntegrationTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Test
    void testCustomerAccountAndTransactionWorkflow() {
        // 1. Create Customer 1
        CustomerDTO customer1 = new CustomerDTO(null, "Alice Smith", "alice@example.com", "9876543210");
        CustomerDTO createdCustomer1 = customerService.createCustomer(customer1);
        Assertions.assertNotNull(createdCustomer1.getCustomerId());

        // 2. Create Customer 2
        CustomerDTO customer2 = new CustomerDTO(null, "Bob Jones", "bob@example.com", "9123456789");
        CustomerDTO createdCustomer2 = customerService.createCustomer(customer2);
        Assertions.assertNotNull(createdCustomer2.getCustomerId());

        // 3. Create Account 1 for Alice
        AccountDTO account1 = new AccountDTO(null, "ACC1001", "SAVINGS", 1000.0, createdCustomer1.getCustomerId());
        AccountDTO createdAccount1 = accountService.createAccount(account1);
        Assertions.assertEquals(1000.0, createdAccount1.getBalance());

        // 4. Create Account 2 for Bob
        AccountDTO account2 = new AccountDTO(null, "ACC1002", "CHECKING", 500.0, createdCustomer2.getCustomerId());
        AccountDTO createdAccount2 = accountService.createAccount(account2);
        Assertions.assertEquals(500.0, createdAccount2.getBalance());

        // 5. Deposit to Account 1
        AccountDTO deposited = accountService.deposit(new DepositRequestDTO("ACC1001", 500.0));
        Assertions.assertEquals(1500.0, deposited.getBalance());

        // 6. Withdraw from Account 2
        AccountDTO withdrawn = accountService.withdraw(new WithdrawRequestDTO("ACC1002", 200.0));
        Assertions.assertEquals(300.0, withdrawn.getBalance());

        // 7. Transfer from ACC1001 to ACC1002
        List<TransactionDTO> transferTxs = transactionService.transferFunds(new TransferRequestDTO("ACC1001", "ACC1002", 400.0));
        Assertions.assertEquals(2, transferTxs.size());

        // 8. Verify Balances after Transfer
        AccountDTO acc1Updated = accountService.getAccountByNumber("ACC1001");
        AccountDTO acc2Updated = accountService.getAccountByNumber("ACC1002");

        Assertions.assertEquals(1100.0, acc1Updated.getBalance());
        Assertions.assertEquals(700.0, acc2Updated.getBalance());

        // 9. Verify Transactions
        List<TransactionDTO> acc1Txs = transactionService.getTransactionsByAccountId(acc1Updated.getAccountId());
        Assertions.assertTrue(acc1Txs.size() >= 2);
    }
}
