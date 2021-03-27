package demo.task1.model;

import demo.task1.OperationType;
import demo.task1.model.Account;
import lombok.*;

import java.math.BigDecimal;

@Builder
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountOperation {
    private Long id;
    private Account source;
    private Account destination;
    private BigDecimal amount;
    private OperationType type;
    private boolean operationStatus = false;
}
