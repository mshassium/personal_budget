package ru.mshassium.personalbudget.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mshassium.personalbudget.db.entity.Wallet;

public interface WalletTransactionsRepository extends JpaRepository<Wallet, Integer> {
}
