package demo.task1;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        System.out.println("start");

        Bank bank = new BankImpl();
        bank.createAccount("Kamil", "Nowak");
        System.out.println(bank.findAccount("Kamil", "Nowak"));


    }

    public static double max(double a, double b) {
        return Math.max(a, b);
    }
}
