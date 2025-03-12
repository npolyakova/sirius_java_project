package ru.hpclab.hl.module1.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.hpclab.hl.module1.model.Guest;
import ru.hpclab.hl.module1.repository.GuestRepository;
import ru.hpclab.hl.module1.service.GuestService;
import ru.hpclab.hl.module1.service.StatisticsService;

import java.util.Random;

@Configuration
public class ServicesConfig {

    @Bean
    GuestService guestService(GuestRepository guestRepository) {
        GuestService guestService = new GuestService(guestRepository);
        for (int i = 0; i < 5; i++) {
            guestRepository.save(new Guest(new Random().nextLong(), "Guest", "0000000000"));
        }
        return guestService;
    }

    @Bean
    @ConditionalOnProperty(prefix = "statistics", name = "service", havingValue = "console2000")
    StatisticsService statisticsService2000(GuestService guestService){
        return new StatisticsService(2000, guestService);
    }

    @Bean
    @ConditionalOnProperty(prefix = "statistics", name = "service", havingValue = "console1000")
    StatisticsService statisticsService1000(GuestService guestService){
        return new StatisticsService(1000, guestService);
    }
}
