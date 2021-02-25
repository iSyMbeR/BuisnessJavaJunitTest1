package demo.task1;

import java.math.BigDecimal;

public class App {

    public static void main(String[] args) {
        System.out.println("start");

        Bank bank = new BankImpl();

        bank.createAccount("KamilNowak", "Ziemia");
        bank.createAccount("ElonMusk", "Mars");

        Long idKamil = bank.findAccount("KamilNowak", "Ziemia");
        Long idElon = bank.findAccount("ElonMusk", "Mars");


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
