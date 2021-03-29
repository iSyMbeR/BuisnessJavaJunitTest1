package demo.task1.model;

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

    public void addOperation(AccountOperation accountOperation){
            accountOperations.add(accountOperation);
    }

    public void addOperation(OperationType operationType, BigDecimal amount){
        if (operationType.equals(OperationType.DEPOSIT)) {
            accountOperations.add(AccountOperation.builder()
                    .type(OperationType.DEPOSIT)
                    .source(this)
                    .destination(this)
                    .amount(this.getAmount().add(amount))
                    .operationStatus(true)
                    .build());
        } else if (operationType.equals(OperationType.WITHDRAW)){
            if (!(this.getAmount().subtract(amount).signum() < 0)){
                accountOperations.add(AccountOperation.builder()
                        .type(OperationType.WITHDRAW)
                        .source(this)
                        .destination(this)
                        .amount(this.getAmount().subtract(amount))
                        .operationStatus(true)
                        .build());
            }
        } else
            throw new IllegalArgumentException();
    }
}
