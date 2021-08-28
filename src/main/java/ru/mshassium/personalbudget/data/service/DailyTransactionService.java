package ru.mshassium.personalbudget.data.service;

import org.springframework.data.jpa.repository.Query;
import ru.mshassium.personalbudget.data.entity.DailyTransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DailyTransactionService extends CrudService<DailyTransaction, Integer> {

    private DailyTransactionRepository repository;

    public DailyTransactionService(@Autowired DailyTransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    protected DailyTransactionRepository getRepository() {
        return repository;
    }

    public List<DailyTransaction> getAllForPeriod(LocalDateTime from, LocalDateTime to){
        return repository.getAllForPeriod(from, to);
    }

}
