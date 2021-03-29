package demo.task1.bank;

import demo.task1.dao.AccountDao;
import demo.task1.dao.AccountDaoJpaImpl;
import demo.task1.dao.AccountOperationDao;
import demo.task1.dao.AccountOperationDaoJpaImpl;
import demo.task1.model.Account;
import demo.task1.model.AccountOperation;
import demo.task1.model.OperationType;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static demo.task1.Communicat.ACCOUNT_NOT_FOUND;

public class BankImpl implements Bank {

    HashMap<Long, Account> accountHashMap;
    private Account account;
    AccountDao accountDao;
    AccountOperationDao accountOperationDao;

    public BankImpl() {
        accountHashMap = new HashMap<>();
        accountDao = new AccountDaoJpaImpl();
        accountOperationDao = new AccountOperationDaoJpaImpl();
    }

    @Override
    public Long createAccount(String login, String address) {
        Account account = Account.builder()
                .name(login)
                .address(address)
                .amount(new BigDecimal(10000))
                .build();
        try {
            accountDao.save(account);
        } catch (Exception sqlException) {
            System.out.println(account.getName() + " znajduje się już w bazie");
        }
        return account.getId();
    }

    @Override
    public Long findAccount(String login, String address) {
        AtomicReference<Long> idFoundAccount = new AtomicReference<>(0L);
        List<Account> accountList = accountDao.findAll();
        accountList.forEach(account1 -> {
            if (account1.getName().equals(login) && account1.getAddress().equals(address)) {
                idFoundAccount.set(account1.getId());
            }
        });
        if (!idFoundAccount.get().equals(0L)) {
            System.out.println("Znaleziono uzytkownika o loginie " + login + " oraz adresie " + address);
            return idFoundAccount.get();
        }
        return null;
    }

    public Account findAccountById(Long id) {
        Optional<Account> acc = accountDao.findById(id);
        return acc.orElse(null);
    }

    @Override
    public void deposit(Long id, BigDecimal amount) {
        Account acc = findAccountById(id);
        if (acc != null) {
            AccountOperation accountOperation = AccountOperation.builder()
                    .source(acc)
                    .destination(acc)
                    .type(OperationType.DEPOSIT)
                    .amount(amount)
                    .build();
            acc.addOperation(accountOperation);
            acc.setAmount(acc.getAmount().add(amount));
            accountDao.update(acc);
            accountOperationDao.save(accountOperation);
            System.out.println(accountOperation.toString());
        } else {
            System.out.println(ACCOUNT_NOT_FOUND);
            throw new AccountIdException();
        }
    }

    @Override
    public BigDecimal getBalance(Long id) {
        account = findAccountById(id);
        if (account == null) {
            System.out.println(ACCOUNT_NOT_FOUND);
            throw new AccountIdException();
        } else {
            return account.getAmount();
        }
    }

    @Override
    public void withdraw(Long id, BigDecimal amount) {
        account = null;
        account = findAccountById(id);
        if (account != null) {
            AccountOperation accountOperation = AccountOperation.builder()
                    .source(account)
                    .destination(account)
                    .type(OperationType.WITHDRAW)
                    .amount(amount)
                    .build();
            account.setAmount(account.getAmount().subtract(amount));
            account.addOperation(accountOperation);
            accountDao.update(account);
            accountOperationDao.save(accountOperation);
            if (account == null) {
                System.out.println(ACCOUNT_NOT_FOUND);
                throw new AccountIdException();
            } else if (account.getAmount().subtract(amount).compareTo(BigDecimal.ZERO) < 0) {
                throw new InsufficientFundsException();
            } else {
                account.setAmount(account.getAmount().subtract(amount));
            }
        }
    }

    @Override
    public void transfer(Long idSource, Long idDestination, BigDecimal amount) {
        account = findAccountById(idSource);
        Account account2 = findAccountById(idDestination);
        if (account == null || account2 == null) {
            throw new AccountIdException();
        } else if (account.getAmount().subtract(amount).compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientFundsException();
        } else {
            account.setAmount(account.getAmount().subtract(amount));
            account2.setAmount(account2.getAmount().add(amount));
            accountDao.update(account);
            accountDao.update(account2);
        }
    }
}
