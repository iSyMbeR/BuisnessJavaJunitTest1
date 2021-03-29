package demo.task1;


import demo.task1.bank.Bank;
import demo.task1.bank.BankImpl;
import demo.task1.dao.AccountDao;
import demo.task1.dao.AccountDaoJpaImpl;
import demo.task1.dao.AccountOperationDao;
import demo.task1.dao.AccountOperationDaoJpaImpl;
import demo.task1.model.Account;
import demo.task1.model.OperationType;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAmount;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Log4j2
public class App {
    /**
     * @param args Metoda uruchamia aplikacje
     */
    public static void main(String[] args) {
        Bank bank = new BankImpl();
        AccountDao accountDao = new AccountDaoJpaImpl();
        AccountOperationDao accountOperationDao = new AccountOperationDaoJpaImpl();
//
//        Long id = bank.createAccount("Kamilek", "asdkosad@o2.pl");
//        bank.createAccount("Kamilek2", "asdkosad@o2.pl");
//        bank.createAccount("Kamilek3", "fejkowymail@o2.pl");
//        bank.createAccount("Kamilek3", "prawdziwymail@o2.pl");
//        bank.createAccount("stachu", "prawdziwymail@o2.pl");
        Optional<Account> account = accountDao.findById(2L);
        account.get().addOperation(OperationType.WITHDRAW, BigDecimal.valueOf(500));
        //System.out.println(accountDao.findByLogin("Kamilek"));

        //a) konta z danym właścicielem (nazwa i adres)
        //System.out.println(accountDao.findByLoginAndAddress("Kamilek","asdkosad@o2.pl"));

        //b) konta w których nazwa właściciela zaczyna się od zadanego fragmentu (operator LIKE)
        //System.out.println(accountDao.findLoginLike("stac"));

        //// c) konta z saldem w określonym przedziale
        // System.out.println(accountDao.findByAmountBetween(BigDecimal.valueOf(0),BigDecimal.valueOf(1000000)));

//        // d) konta z największą ilością środków
//        System.out.println(accountDao.findMaxAmount());


        ////e) operacje na danym koncie w zadanym przedziale dat
//        System.out.println(accountOperationDao.findAccountsWithOperationsBetween(account.get().getCreated(), new Date()));
//
//        System.out.println(accountOperationDao.findAccountsWithoutOperations());

        //System.out.println(accountOperationDao.findAccountsWithMaxOperations());
        //g) konta z najwiekszą liczbą operacji

        System.out.println(accountOperationDao.findMostPopularTypeOfOperation());

        //System.out.println(accountDao.findByAddress("asdskosad@o2.pl"));
        //System.out.println(accountDao.findByAmountBetween(BigDecimal.valueOf(0), BigDecimal.valueOf(50000)));
        //System.out.println(accountDao.findByLogin("Kamilek"));
    }

    public static double max(double a, double b) {
        return Math.max(a, b);
    }
}
