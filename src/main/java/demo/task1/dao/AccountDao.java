package demo.task1.dao;

import demo.task1.model.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountDao extends GenericDao<Account, Long> {

    Optional<Account> findByLogin(String login);
    List<Account> findLoginLike(String login);
    Optional<Account> findByLoginAndAddress(String login, String address);
    List<Account> findByAddress(String address);
    List<Account> findByAmount(BigDecimal bigDecimalValue);
    List<Account> findByAmountBetween(BigDecimal min, BigDecimal max);
    List<Account> findMaxAmount();
    List<Account> findMinAmount();
}
