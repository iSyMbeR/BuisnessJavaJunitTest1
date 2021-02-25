package demo.task1;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BankImpl implements Bank {
    HashMap<Long, Account> accountHashMap;
    private Account account;

    public BankImpl() {
        accountHashMap = new HashMap<>();
    }

    @Override
    public Long createAccount(String name, String address) {
        Account account = Account.builder()
                .id(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE)
                .name(name)
                .address(address)
                .amount(new BigDecimal(0))
                .build();

        //jezeli istenieje uzytkownik z tkaim samym imieniem i adresem
        for (Map.Entry<Long, Account> entry : accountHashMap.entrySet()) {
            if (entry.getValue().getName().equals(name) && entry.getValue().getAddress().equals(address)) {
                return entry.getKey();
            }
        }
        //jezeli nie ma takiego id to dodaje konto do hashmapy
        if (!accountHashMap.containsKey(account.getId())) {
            accountHashMap.put(account.getId(), account);
        }
        return account.getId();
    }

    @Override
    public Long findAccount(String name, String address) {
        for (Map.Entry<Long, Account> entry : accountHashMap.entrySet()) {
            if (entry.getValue().getName().equals(name) && entry.getValue().getAddress().equals(address)) {
                System.out.println("Znaleziono konto " + name + " " + address);
                return entry.getKey();
            }
        }
        return null;
    }

    public Account findAccountById(Long id) {
        for (Map.Entry<Long, Account> entry : accountHashMap.entrySet()) {
            if (entry.getKey().equals(id))
                return entry.getValue();
        }
        return null;
    }

    @Override
    public void deposit(Long id, BigDecimal amount) {
        account = findAccountById(id);
        if (account != null) {
            account.setAmount(account.getAmount().add(amount));
            accountHashMap.replace(account.getId(), account);
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
