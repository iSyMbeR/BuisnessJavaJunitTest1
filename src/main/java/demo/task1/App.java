package demo.task1;


import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;

@Log4j2
public class App {
    /**
     * @param args nic ciekawego
     *             Metoda uruchamia aplikacje
     */
    public static void main(String[] args) {
        Bank bank = new BankImpl();
        log.info("utworzenie instancji banku klasy" + bank.getClass());

        Long idKamil = 0L;
        Long idElon = 0L;
        try {
            bank.createAccount("KamilNowak", "Ziemia");
            bank.createAccount("ElonMusk", "Mars");
            idKamil = bank.findAccount("KamilNowak", "Ziemia");
            idElon = bank.findAccount("ElonMusk", "Mars");
            log.debug("Created account with ids : " + idKamil + " and " + idElon);
        } catch (Throwable e) {
            log.error("Indexing error", e);
        }


        bank.getBalance(idKamil);
        System.out.println("Przed dodaniem kasiory " + bank.getBalance(idKamil));
        bank.deposit(idKamil, new BigDecimal(30));
        System.out.println("po dodaniu kasiory " + bank.getBalance(idKamil));
        bank.getBalance(idKamil);
        System.out.println();

        System.out.println("stan konta elona przed przelaniem kasiory " + bank.getBalance(idElon));
        System.out.println("stan konta Kamila przed przelaniem kasiory " + bank.getBalance(idKamil));
        System.out.println();
        bank.transfer(idKamil, idElon, new BigDecimal(20));
        System.out.println("Stan konta chlopakow po przelaniu kasiory do kumpla za browara: " +
                "\nKamil: " + bank.getBalance(idKamil) +
                "\nElon: " + bank.getBalance(idElon));
        bank.withdraw(idKamil, new BigDecimal(10));
        System.out.println("Po wyplaceniu kamila 10zl " + bank.getBalance(idKamil));
    }

    public static double max(double a, double b) {
        return Math.max(a, b);
    }
}
