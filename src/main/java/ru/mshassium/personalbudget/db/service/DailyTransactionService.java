package ru.mshassium.personalbudget.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;
import ru.mshassium.personalbudget.db.entity.DailyTransactions;
import ru.mshassium.personalbudget.db.repository.DailyTransactionRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DailyTransactionService extends CrudService<DailyTransactions, Integer> {

    private DailyTransactionRepository repository;

    public DailyTransactionService(@Autowired DailyTransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    protected DailyTransactionRepository getRepository() {
        return repository;
    }

    public List<DailyTransactions> getAllForPeriod(LocalDateTime from, LocalDateTime to){
        return repository.getAllForPeriod(from, to);
    }

}
