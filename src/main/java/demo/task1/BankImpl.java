package demo.task1;

import demo.task1.dao.AccountDao;
import demo.task1.dao.AccountOperationDao;
import demo.task1.dao.jdbc.AccountDaoJdbcImpl;
import demo.task1.dao.jdbc.AccountOperationDaoJdbcImpl;
import demo.task1.model.Account;
import demo.task1.model.AccountOperation;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class BankImpl implements Bank {
    HashMap<Long, Account> accountHashMap;
    private Account account;
    AccountDao accountDao;
    AccountOperationDao accountOperationDao;
    public BankImpl() {
        accountHashMap = new HashMap<>();
        accountDao = new AccountDaoJdbcImpl();
        accountOperationDao = new AccountOperationDaoJdbcImpl();
    }

    @Override
    public Long createAccount(String login, String address) {
        Account account = Account.builder()
                .login(login)
                .address(address)
                .amount(new BigDecimal(10000))
                .build();
       try{
           accountDao.save(account);
       } catch (Exception sqlException){
           System.out.println(account.getLogin() + " znajduje się już w bazie");
       }
        return account.getId();
    }

    @Override
    public Long findAccount(String login, String address) {
        AtomicReference<Long> idFoundAccount = new AtomicReference<>(0L);
        List<Account> accountList = accountDao.findAll();
        accountList.forEach(account1 -> {
            if(account1.getLogin().equals(login) && account1.getAddress().equals(address)){
                idFoundAccount.set(account1.getId());
            }
        });
        if(!idFoundAccount.get().equals(0L)){
            System.out.println("Znaleziono uzytkownika o loginie " + login +" oraz adresie " + address);
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
        if (acc != null){
            AccountOperation accountOperation = AccountOperation.builder()
                    .source(acc)
                    .destination(acc)
                    .type(OperationType.DEPOSIT)
                    .amount(amount)
                    .build();
            accountOperationDao.deposit(accountOperation);
            System.out.println(accountOperation.toString());
        } else {
            System.out.println("Nie znaleziono takiego konta");
            throw new AccountIdException();
        }
    }

    @Override
    public BigDecimal getBalance(Long id) {
        account = findAccountById(id);
        if (account == null) {
            System.out.println("Nie znaleziono takiego konta");
            throw new AccountIdException();
        } else {
            return account.getAmount();
        }
    }

    @Override
    public void withdraw(Long id, BigDecimal amount) {
        account = findAccountById(id);
        if (account == null) {
            System.out.println("Nie znaleziono takiego konta");
            throw new AccountIdException();
        } else if (account.getAmount().subtract(amount).compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientFundsException();
        } else {
            account.setAmount(account.getAmount().subtract(amount));
            accountHashMap.replace(account.getId(), account);
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
            accountHashMap.replace(account.getId(), account);
            accountHashMap.replace(account2.getId(), account2);
        }
    }
}
