package ru.practicum.ewm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"ru.practicum"})
public class ExploreWithMeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExploreWithMeApplication.class, args);
    }

}
