package miu.edu.auction;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
public class AuctionApplication {
    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Chicago"));
    }

    public static void main(String[] args) {
        SpringApplication.run(AuctionApplication.class, args);
    }
}
