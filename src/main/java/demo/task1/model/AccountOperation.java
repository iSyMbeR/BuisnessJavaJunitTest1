package demo.task1.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Builder
@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "AccountOperation.findAccountsWithOperationsBetween",
                query = "select a from Account a, AccountOperation ao where (ao.created between ?1 and ?2) and ao.source.id = a.id"),
        @NamedQuery(name = "AccountOperation.findAccountsWithoutOperations",
                query = "select a from Account a where a.id not in " +
                        "(select x.source.id from AccountOperation x)"),

        @NamedQuery(name = "AccountOperation.findAccountsWithMaxOperations",
                query = "select a, count(a.id) from Account a, AccountOperation ao where a.id =ao.source.id " +
                        "group by a.id" +
                        " order by count(a.id) desc"),

        @NamedQuery(name = "AccountOperation.findMostPopularTypeOfOperation",
                query = "select ao.type, count(ao.type) from AccountOperation ao" +
                        " group by ao.type" +
                        " order by count(ao.type) desc"),
})

public class AccountOperation extends AbstractModel {
    @ManyToOne
    private Account source;
    @ManyToOne
    private Account destination;
    private BigDecimal amount;
    @Enumerated
    private OperationType type;
    private boolean operationStatus = false;
}
