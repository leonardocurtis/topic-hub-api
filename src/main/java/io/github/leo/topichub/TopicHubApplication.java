package io.github.leo.topichub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class TopicHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(TopicHubApplication.class, args);
    }
}
