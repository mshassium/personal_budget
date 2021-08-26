package ru.mshassium.personalbudget.data.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.mshassium.personalbudget.data.AbstractEntity;
import java.time.LocalDateTime;

@Entity
@Table(name = "daily_transactions")
public class DailyTransaction extends AbstractEntity {

    private LocalDateTime date;
    private Integer amount;
    private String description;
    private String type;
    private Integer spendingPeriod;

    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Integer getSpendingPeriod() {
        return spendingPeriod;
    }
    public void setSpendingPeriod(Integer spendingPeriod) {
        this.spendingPeriod = spendingPeriod;
    }

}
