package ru.mshassium.personalbudget.data.service;

import ru.mshassium.personalbudget.data.entity.DailyTransaction;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;

public interface DailyTransactionRepository extends JpaRepository<DailyTransaction, Integer> {

}