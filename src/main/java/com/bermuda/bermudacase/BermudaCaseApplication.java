package com.bermuda.bermudacase;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.bermuda.bermudacase.business.concreteBusiness.RabbitMQListener;

@SpringBootApplication
@EnableMongoRepositories
public class BermudaCaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BermudaCaseApplication.class, args);
    }

    @Bean
    public Queue guidQueue() {
        return new Queue("guidQueue", false);
    }

    @Bean
    public RabbitMQListener listener() {
        return new RabbitMQListener();
    }

}
