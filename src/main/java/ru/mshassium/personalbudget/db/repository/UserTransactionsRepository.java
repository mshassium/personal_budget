package ru.mshassium.personalbudget.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mshassium.personalbudget.db.entity.UserTransactions;

public interface UserTransactionsRepository extends JpaRepository<UserTransactions, Integer> {
}
