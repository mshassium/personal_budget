package ru.mshassium.personalbudget.db.entity;


import lombok.*;
import ru.mshassium.personalbudget.db.model.UserTransactionsType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "all_transactions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserTransactions {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "type", nullable = false)
    private UserTransactionsType type;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "daily_transactions_id", nullable = false)
    private DailyTransactions dailyTransactions;

}
