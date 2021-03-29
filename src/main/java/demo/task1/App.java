package demo.task1;


import demo.task1.bank.Bank;
import demo.task1.bank.BankImpl;
import demo.task1.dao.AccountDao;
import demo.task1.dao.AccountDaoJpaImpl;
import demo.task1.model.Account;
import demo.task1.model.OperationType;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.util.List;

@Log4j2
public class App {
    /**
     * @param args Metoda uruchamia aplikacje
     */
    public static void main(String[] args) {
        Bank bank = new BankImpl();
        AccountDao accountDao = new AccountDaoJpaImpl();
        bank.createAccount("Kamilek", "asdkosad@o2.pl");
        Long id = bank.createAccount("Kamiler", "gdziesTam");
        Long id2 = bank.createAccount("Kamilos", "gdziesTam");
        System.out.println(id + " id1" + id2 + " ID 2");
        List<Account> accountList = accountDao.findAll();
        System.out.println(accountList.toString());
    }

    public static double max(double a, double b) {
        return Math.max(a, b);
    }
}
