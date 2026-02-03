package org.example.moneyflowspring;

import org.example.moneyflowspring.known_merchants.KnownMerchantsRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MoneyFlowSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoneyFlowSpringApplication.class, args);


    }

}
