package ru.mshassium.personalbudget.data.generator;

import com.vaadin.flow.spring.annotation.SpringComponent;

import ru.mshassium.personalbudget.data.service.DailyTransactionRepository;
import ru.mshassium.personalbudget.data.entity.DailyTransaction;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(DailyTransactionRepository dailyTransactionRepository) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (dailyTransactionRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Generating demo data");

            logger.info("... generating 100 Daily Transaction entities...");
            ExampleDataGenerator<DailyTransaction> dailyTransactionRepositoryGenerator = new ExampleDataGenerator<>(
                    DailyTransaction.class, LocalDateTime.of(2021, 8, 26, 0, 0, 0));
            dailyTransactionRepositoryGenerator.setData(DailyTransaction::setId, DataType.ID);
            dailyTransactionRepositoryGenerator.setData(DailyTransaction::setId, DataType.NUMBER_UP_TO_100);
            dailyTransactionRepositoryGenerator.setData(DailyTransaction::setDate, DataType.DATETIME_LAST_10_YEARS);
            dailyTransactionRepositoryGenerator.setData(DailyTransaction::setAmount, DataType.NUMBER_UP_TO_100);
            dailyTransactionRepositoryGenerator.setData(DailyTransaction::setDescription, DataType.WORD);
            dailyTransactionRepositoryGenerator.setData(DailyTransaction::setType, DataType.WORD);
            dailyTransactionRepositoryGenerator.setData(DailyTransaction::setSpendingPeriod, DataType.NUMBER_UP_TO_100);
            dailyTransactionRepository.saveAll(dailyTransactionRepositoryGenerator.create(100, seed));

            logger.info("Generated demo data");
        };
    }

}