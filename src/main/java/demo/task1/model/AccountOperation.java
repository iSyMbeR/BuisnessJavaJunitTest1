package demo.task1.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Builder
@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AccountOperation extends AbstractModel{
    @ManyToOne
    private Account source;
    @ManyToOne
    private Account destination;
    private BigDecimal amount;
    private OperationType type;
    private boolean operationStatus = false;
}
