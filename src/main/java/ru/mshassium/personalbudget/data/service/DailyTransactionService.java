package ru.mshassium.personalbudget.data.service;

import ru.mshassium.personalbudget.data.entity.DailyTransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;
import java.time.LocalDateTime;

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

}
