package demo.task1;

import java.math.BigDecimal;

public interface Bank {
    /**
     * Tworzy nowe lub zwraca id istniejącego konta.
     *
     * @param name imie i nazwisko własciciela
     * @param address adres własciciela
     * @return id utworzonego lub istniejacego konta.
     */
    Long createAccount(String name, String address);

    /**
     * Znajduje identyfikator konta.
     *
     * @param name imię i nazwisko właściciela
     * @param address adres właściciela
     * @return id konta lub null, gdy brak konta o podanych parametrach
     */
    Long findAccount(String name, String address);

    /**
     * Dodaje srodki do konta.
     *
     * @param id indeks konta na ktory ma byc przelana kasa
     * @param amount srodki
     */
    void deposit(Long id, BigDecimal amount);

    /**
     * Zwraca ilosc srodkow na koncie.
     *
     * @param id indeks konta z ktorego pobierany jest stan konta
     * @return srodki
     */
    BigDecimal getBalance(Long id);

    /**
     * Pobiera srodki z konta.
     *
     * @param id indeks konta z ktorego sa pobierane pieniadze
     * @param amount srodki
     */
    void withdraw(Long id, BigDecimal amount);

    /**
     * Przelewa srodki miedzy kontami.
     *
     * @param idSource id konta, z ktorego przesylamy pieniadze
     * @param idDestination id konta, do ktorego sa przesylane pieniadze
     * @param amount srodki
     */
    void transfer(Long idSource, Long idDestination, BigDecimal amount);

    class InsufficientFundsException extends RuntimeException {
    };

    class AccountIdException extends RuntimeException {
    };
}
