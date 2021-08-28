package ru.mshassium.personalbudget.views.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="mailto:khamitov-rail@yandex-team.ru">Rail Khamitov</a>
 * @date 28.08.2021
 */

@Getter
@Setter
@AllArgsConstructor
@Builder
public class DailyTransactionViewModel {
    private final Long id;
    private final LocalDateTime date;
    private final Integer estimatedSpending;
    private final Integer estimatedBalance;
    private final Integer amountPerDay;
    private final Integer spendPerDay;
    private final Integer balancePerDay;
    private final Integer balancePerMonth;
    private final Integer adjustment;
    private final String description;
}
