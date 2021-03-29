package demo.task1;

import demo.task1.dao.AccountDao;
import demo.task1.dao.AccountDaoJpaImpl;
import demo.task1.model.Account;
import demo.task1.model.OperationType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

public class AccountDaoTest {

    private AccountDao accountDao;

    @BeforeEach
    public void setup() {
        accountDao = new AccountDaoJpaImpl();
    }

    @AfterEach
    public void clear() {
        //  usuwa uzytkownikow po kazdym tescie
        for (Account u: accountDao.findAll())
            accountDao.delete(u);
    }

    @Test
    public void testSave() {
        Account u = new Account(null,"abc","abc@abc.pl", BigDecimal.valueOf(5000));
        accountDao.save(u);
        assert u.getId() != null;
    }

    @Test
    public void testSaveWithGroups() {
        Account account = new Account(null,"abc","abc@abc.pl", BigDecimal.valueOf(5000));
        account.addOperation(OperationType.DEPOSIT,BigDecimal.valueOf(5000));
        account.addOperation(OperationType.WITHDRAW,BigDecimal.valueOf(1000));
        accountDao.save(account);

        Optional<Account> o = accountDao.findById(account.getId());
        assert o.isPresent();
        assert o.get().getAccountOperations().size() == 2;
    }

    @Test
    public void testUpdateWithGroups() {
        Account account = new Account(null,"abc","abc@abc.pl", BigDecimal.valueOf(5000));
        account.addOperation(OperationType.DEPOSIT,BigDecimal.valueOf(5000));
        account.addOperation(OperationType.WITHDRAW,BigDecimal.valueOf(1000));
        accountDao.save(account);

        account = accountDao.findById(account.getId()).get();
        assert account.getAccountOperations().size() == 2;

        account.getAccountOperations().remove(0);
        accountDao.update(account);

        account = accountDao.findById(account.getId()).get();
        assert account.getAccountOperations().size() == 1;
    }

    @Test
    public void testSaveUniqueLogin() {
        Account account = new Account(null,"abc","abc@abc.pl", BigDecimal.valueOf(5000));
        accountDao.save(account);

        Assertions.assertThrows(javax.persistence.RollbackException.class, ()-> {
            Account account2 = new Account(null,"abc","abc@abc.pl", BigDecimal.valueOf(5000));
            accountDao.save(account2);
        });

    }

    @Test
    public void testUpdate() {
        Account account = new Account(null,"abc","abc@abc.pl", BigDecimal.valueOf(5000));
        accountDao.save(account);
        assert account.getId() != null;
        account.setName("bbb");
        account.setAddress("bbb@bbb.pl");
        account.setAmount(BigDecimal.valueOf(5000));
        accountDao.update(account);

        Account acc2 = accountDao.findById(account.getId()).get();
        assert acc2.equals(account);
    }

    @Test
    public void testDelete() {
        Account u = new Account(null,"abc","abc@abc.pl", BigDecimal.valueOf(5000));
        accountDao.save(u);
        accountDao.delete(u);
        Account u2 = accountDao.findById(u.getId()).orElse(null);
        assert u2 == null;
    }

    @Test
    public void testSelect() {
        accountDao.save( new Account(null,"abc","abc@abc.pl", BigDecimal.valueOf(5000)));
        accountDao.save( new Account(null,"abc2","abc@abc.pl", BigDecimal.valueOf(5000)));
        assert accountDao.findAll().size() == 2;
    }
}
