package ru.mshassium.personalbudget.db.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "daily_transactions")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyTransactions {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "left_daily_amount", nullable = false)
    private BigDecimal leftDailyAmount;

    @Column(name = "perfect_left_amount", nullable = false)
    private BigDecimal perfectLeftAmount;

    @Column(name = "adjustment")
    private BigDecimal adjustment;

    @OneToMany(mappedBy = "dailyTransactions")
    private Set<UserTransactions> userTransactions;

}
