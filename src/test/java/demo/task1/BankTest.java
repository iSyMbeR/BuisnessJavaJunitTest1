package demo.task1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class BankTest {

    private Bank bank;

    @BeforeEach
    void setup() {
        bank = new BankImpl();
    }

    @AfterEach
    void clean() {
        bank = null;
    }

    @Test
    void createAccountTest1() {
        Long id = bank.createAccount("Kamil", "gdziesTam");
        assert id != null;
    }

    @Test
    void createAccountTest2() {
        Long id = bank.createAccount("Kamil", "gdziesTam");
        Long id2 = bank.createAccount("Kamil", "gdziesTam");
        assert id != null && id.equals(id2);
    }

    @Test
    void createAccountTest3() {
        Long id = bank.createAccount("Kamil", "Ziemia");
        Long id2 = bank.createAccount("ElonMusk", "Mars");
        assert id != null && !id.equals(id2);
    }

    @Test
    void findAccountTest1() {
        Long id = bank.createAccount("Kamil", "Ziemia");
        Long findAccountId = bank.findAccount("Kamil", "Ziemia");
        assert id.equals(findAccountId);
    }

    @Test
    void findAccountTest2() {
        Long id = bank.createAccount("Kamil", "Ziemia");
        Long id2 = bank.createAccount("ElonMusk", "Mars");
        Long findAccountId = bank.findAccount("Kamil", "Ziemia");
        Long findAccountId2 = bank.findAccount("ElonMusk", "Mars");
        assert id.equals(findAccountId) && id2.equals(findAccountId2);
    }

    @Test
    void getBalanceTest1() {
        Long id = bank.createAccount("Kamil", "Ziemia");
        BigDecimal checkBalance = bank.getBalance(id);
        assert checkBalance.equals(new BigDecimal(0));
    }

    @Test
    void getBalanceTest2() {
        Long id = bank.createAccount("ElonMusk", "Mars");
        bank.deposit(id, new BigDecimal(500));
        BigDecimal checkBalance = bank.getBalance(id);
        assert !checkBalance.equals(new BigDecimal(0)) && checkBalance.equals(new BigDecimal(500));
    }

    @Test
    void depositAccount1() {
        Long id = bank.createAccount("Kamil", "Ziemia");
        Long id2 = bank.createAccount("ElonMusk", "Mars");
        bank.deposit(id, new BigDecimal(500));
        bank.deposit(id2, new BigDecimal(900));
        assert !bank.getBalance(id).equals(bank.getBalance(id2)) && bank.getBalance(id).equals(new BigDecimal(500)) && bank.getBalance(id2).equals(new BigDecimal(900));
    }

    @Test
    void depositAccount2() {
        Long id = bank.createAccount("Kamil", "Ziemia");
        bank.deposit(id, new BigDecimal(40));
        bank.withdraw(id, new BigDecimal(30));
        bank.deposit(id, new BigDecimal(40));
        assert bank.getBalance(id).equals(new BigDecimal(50));
    }

    @Test
    void transferAccount1() {
        Long id = bank.createAccount("Kamil", "Ziemia");
        Long id2 = bank.createAccount("ElonMusk", "Mars");
        Long id3 = bank.createAccount("Dziekan", "PbWi");

        bank.deposit(id, new BigDecimal(40));
        bank.deposit(id2, new BigDecimal(40));
        bank.deposit(id3, new BigDecimal(40));

        bank.transfer(id, id3, new BigDecimal(40)); // warunek
        bank.transfer(id2, id3, new BigDecimal(40)); // warunek

        assert bank.getBalance(id).equals(new BigDecimal(0)) &&
                bank.getBalance(id2).equals(new BigDecimal(0)) &&
                bank.getBalance(id3).equals(new BigDecimal(120));
    }

    @Test
    void transferAccount2() {
        Long id = bank.createAccount("Kamil", "Ziemia");
        Long id2 = bank.createAccount("ElonMusk", "Mars");
        Long id3 = bank.createAccount("Dziekan", "PbWi");

        bank.deposit(id, new BigDecimal(40));
        bank.deposit(id2, new BigDecimal(40));
        bank.deposit(id3, new BigDecimal(40));

        bank.transfer(id, id3, new BigDecimal(40)); // warunek
        bank.transfer(id2, id3, new BigDecimal(40)); // warunek
        bank.transfer(id3, id, new BigDecimal(100)); // i cyk stypedium

        assert bank.getBalance(id).equals(new BigDecimal(100)) &&
                bank.getBalance(id2).equals(new BigDecimal(0)) &&
                bank.getBalance(id3).equals(new BigDecimal(20));
    }


}
