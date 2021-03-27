package demo.task1.dao;

import demo.task1.model.Account;
import demo.task1.model.AccountOperation;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountOperationDao {
    void deposit(AccountOperation accountOperation);

    void withdraw(AccountOperation accountOperation);

    void transfer(AccountOperation accountOperation);

    BigDecimal getBalance(AccountOperation accountOperation);
}
