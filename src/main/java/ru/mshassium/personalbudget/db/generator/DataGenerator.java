package ru.mshassium.personalbudget.db.generator;

import com.vaadin.exampledata.ExampleDataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import ru.mshassium.personalbudget.db.entity.DailyTransactions;
import ru.mshassium.personalbudget.db.repository.DailyTransactionRepository;

import java.time.LocalDateTime;

//@SpringComponent
public class DataGenerator {

//    @Bean
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
            ExampleDataGenerator<DailyTransactions> dailyTransactionRepositoryGenerator = new ExampleDataGenerator<>(
                    DailyTransactions.class, LocalDateTime.of(2021, 8, 26, 0, 0, 0));
//            dailyTransactionRepositoryGenerator.setData(DailyTransactions::setId, DataType.ID);
//            dailyTransactionRepositoryGenerator.setData(DailyTransactions::setId, DataType.NUMBER_UP_TO_100);
//            dailyTransactionRepositoryGenerator.setData(DailyTransactions::setDate, DataType.DATETIME_LAST_10_YEARS);
//            dailyTransactionRepositoryGenerator.setData(DailyTransactions::setAmount, DataType.NUMBER_UP_TO_100);
//            dailyTransactionRepositoryGenerator.setData(DailyTransactions::setDescription, DataType.WORD);
            dailyTransactionRepository.saveAll(dailyTransactionRepositoryGenerator.create(100, seed));

            logger.info("Generated demo data");
        };
    }

}
