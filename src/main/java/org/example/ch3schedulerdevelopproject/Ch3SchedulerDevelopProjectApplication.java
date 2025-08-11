package org.example.ch3schedulerdevelopproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Ch3SchedulerDevelopProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ch3SchedulerDevelopProjectApplication.class, args);
    }

}
