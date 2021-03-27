package demo.task1.model;

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
@EqualsAndHashCode
@ToString
public class Account {
    private Long id;
    private String login;
    private String address;
    private BigDecimal amount;
}
