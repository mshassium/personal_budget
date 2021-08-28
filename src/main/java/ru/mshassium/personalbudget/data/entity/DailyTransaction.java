package ru.mshassium.personalbudget.data.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.atmosphere.config.service.Get;
import ru.mshassium.personalbudget.data.AbstractEntity;
import java.time.LocalDateTime;

@Entity
@Table(name = "daily_transactions")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyTransaction extends AbstractEntity {

    private LocalDateTime date;
    private Integer amount;
    private String description;

}
