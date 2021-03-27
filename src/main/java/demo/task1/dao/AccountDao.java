package demo.task1.dao;

import demo.task1.model.Account;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface AccountDao {
    void save(Account account);
    void update(Account account);
    void delete(Account account);
    Optional<Account> findById(Long id);
    List<Account> findAll();
}
