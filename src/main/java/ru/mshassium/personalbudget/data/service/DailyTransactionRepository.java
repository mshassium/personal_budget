package ru.mshassium.personalbudget.data.service;

import org.springframework.data.jpa.repository.Query;
import ru.mshassium.personalbudget.data.entity.DailyTransaction;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface DailyTransactionRepository extends JpaRepository<DailyTransaction, Integer> {


    @Query(value = "select * from daily_transactions dt where date >= ?1 and date <= ?2", nativeQuery = true)
    List<DailyTransaction> getAllForPeriod(LocalDateTime from, LocalDateTime to);

}
