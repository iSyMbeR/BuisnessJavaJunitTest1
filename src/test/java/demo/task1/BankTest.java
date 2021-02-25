package demo.task1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        Long id = bank.createAccount("Kamil","gdziesTam");
        assert id != null;
    }

    @Test
    void createAccountTest2() {
        Long id = bank.createAccount("Kamil","gdziesTam");
        Long id2 = bank.createAccount("Kamil","gdziesTam");
        assert id != null && id.equals(id2);
    }
}
