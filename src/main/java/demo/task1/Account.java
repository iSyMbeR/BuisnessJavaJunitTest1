package demo.task1;

import lombok.*;

import java.math.BigDecimal;
/**
 *This class is a model of account
 *
 * @author Kamil
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Account {
    private Long id;
    private String name;
    private String address;
    private BigDecimal amount;
}
