package ru.mshassium.personalbudget.db.entity;


import lombok.*;
import ru.mshassium.personalbudget.db.model.WalletType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "wallet")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "full_clear_amount", nullable = false)
    private BigDecimal fullClearAmount;

    @Column(name = "left_amount", nullable = false)
    private BigDecimal leftAmount;

    @Column(name = "perfect_spending_amount", nullable = false)
    private BigDecimal perfectSpendingAmount;

    @Column(name = "period_from", nullable = false)
    private LocalDateTime periodFrom;

    @Column(name = "period_to", nullable = false)
    private LocalDateTime periodTo;

    @Column(name = "days_count", nullable = false)
    private Integer daysCount;

    @Column(name = "type", nullable = false)
    private WalletType type;

    @OneToMany(mappedBy = "wallet")
    private Set<DailyTransactions> dailyTransactions;


}
