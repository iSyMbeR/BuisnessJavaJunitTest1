package demo.task1;


import demo.task1.dao.AccountDao;
import demo.task1.dao.AccountOperationDao;
import demo.task1.dao.jdbc.AccountDaoJdbcImpl;
import demo.task1.dao.jdbc.AccountOperationDaoJdbcImpl;
import demo.task1.model.Account;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@Log4j2
public class App {
    /**
     * @param args nic ciekawego
     *             Metoda uruchamia aplikacje
     */
    public static void main(String[] args){
        Bank bank = new BankImpl();
        AccountDao accountDao = new AccountDaoJdbcImpl();
        AccountOperationDao accountOperationDao = new AccountOperationDaoJdbcImpl();
        List<Account> accountList = accountDao.findAll();
        bank.createAccount("Kamil","Szopen@o2.pl");
        bank.findAccount("Kamil", "Szopen@o2.pl");
        if(!accountList.isEmpty()){
            System.out.println("Accounts: ");
            accountList.forEach(System.out::println);
        } else {
            Account account = Account.builder()
                    .login("kamil")
                    .address("kamil@kamilovymail.pl")
                    .amount(BigDecimal.valueOf(50000))
                    .build();
            accountDao.save(account);
            System.out.println("Saved: " + account);
        }
        bank.deposit(1L,BigDecimal.valueOf(12432));

    }

    public static double max(double a, double b) {
        return Math.max(a, b);
    }
}
