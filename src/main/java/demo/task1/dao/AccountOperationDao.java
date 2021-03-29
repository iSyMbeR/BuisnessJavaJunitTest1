package demo.task1.dao;

import demo.task1.model.Account;
import demo.task1.model.AccountOperation;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface AccountOperationDao extends GenericDao<AccountOperation, Long> {
    List<AccountOperation> findAccountsWithOperationsBetween(Date from, Date to);
    List<AccountOperation> findAccountsWithoutOperations();
    List<Object> findAccountsWithMaxOperations();
    List<Object> findMostPopularTypeOfOperation();
}
