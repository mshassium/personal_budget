package ru.mshassium.personalbudget.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.mshassium.personalbudget.db.entity.DailyTransactions;

import java.time.LocalDateTime;
import java.util.List;

public interface DailyTransactionRepository extends JpaRepository<DailyTransactions, Integer> {


    @Query(value = "select * from daily_transactions dt where date >= ?1 and date <= ?2", nativeQuery = true)
    List<DailyTransactions> getAllForPeriod(LocalDateTime from, LocalDateTime to);

}
