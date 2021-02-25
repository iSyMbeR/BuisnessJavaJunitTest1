package demo.task1;

import lombok.*;

import java.math.BigDecimal;

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
