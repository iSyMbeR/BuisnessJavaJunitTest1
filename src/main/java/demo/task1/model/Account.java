package demo.task1.model;

import demo.task1.dao.AccountDao;
import demo.task1.dao.AccountDaoJpaImpl;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is a model of account
 *
 * @author Kamil
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@NamedQueries({
        @NamedQuery(name = "Account.findByLogin",
                query = "select a from Account a where a.name=?1"),
        @NamedQuery(name = "Account.findByAddress",
                query = "select a from Account a where a.address=?1"),
        @NamedQuery(name = "Account.findByAmount",
                query = "select a from Account a where a.amount=?1"),
        @NamedQuery(name = "Account.findByAmountBetween",
                query = "select a from Account a where a.amount BETWEEN ?1 AND ?2"),
        @NamedQuery(name = "Account.findByLoginAndAddress",
                query = "select a from Account a where a.name = ?1 AND a.address = ?2"),
        @NamedQuery(name = "Account.findLoginLike",
                query = "select a from Account a where a.name LIKE ?1"),
        @NamedQuery(name = "Account.findMaxAmount",
                query = "select a from Account a where a.amount = (select max(b.amount) from Account b)"),
        @NamedQuery(name = "Account.findMinAmount",
                query = "select a from Account a where a.amount = (select min(b.amount) from Account b)")
})

public class Account extends AbstractModel {
    @Column(unique = true)
    private String name;
    private String address;
    private BigDecimal amount;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AccountOperation> accountOperations = new LinkedList<>();

    public Account(Long id, String login, String address, BigDecimal amount) {
        this.amount = amount;
        this.address = address;
        this.name = login;
    }

    public void addOperation(AccountOperation accountOperation) {
        accountOperations.add(accountOperation);
    }

    public void addOperation(OperationType operationType, BigDecimal amount) {
        AccountDao accountDao = new AccountDaoJpaImpl();
        if (operationType.equals(OperationType.DEPOSIT)) {
            accountOperations.add(AccountOperation.builder()
                    .type(OperationType.DEPOSIT)
                    .source(this)
                    .destination(this)
                    .amount(this.getAmount().add(amount))
                    .operationStatus(true)
                    .build());
            this.setAmount(this.getAmount().add(amount));
            accountDao.update(this);
        } else if (operationType.equals(OperationType.WITHDRAW)) {
            if (!(this.getAmount().subtract(amount).signum() < 0)) {
                accountOperations.add(AccountOperation.builder()
                        .type(OperationType.WITHDRAW)
                        .source(this)
                        .destination(this)
                        .amount(this.getAmount().subtract(amount))
                        .operationStatus(true)
                        .build());
                this.setAmount(this.getAmount().subtract(amount));
                accountDao.update(this);
            }
        } else
            throw new IllegalArgumentException();
    }
}
