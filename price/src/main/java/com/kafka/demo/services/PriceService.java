package com.kafka.demo.services;

import com.kafka.demo.dtos.Price;
import com.kafka.demo.dtos.Quote;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class PriceService {

    @KafkaListener(topics = "quote", groupId = "quoteGroup", concurrency = "500")
    @SendTo("price")
    public Price handleQuote(Quote quote) throws InterruptedException {
        log.info("Quote in price : {}", quote);
        // Process the Quote and create a Price
        Price price = new Price();
        price.setId(quote.getId());

        price.setPriceList(generateRandomIntegers());

        log.info("Price : {}", price);
        return price;
    }


    public List<Integer> generateRandomIntegers() throws InterruptedException {
        List<Integer> randomNumbers = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < random.nextInt(10); i++) {
            int randomNumber = random.nextInt(2000 - 1001) + 1000;
            randomNumbers.add(randomNumber);
        }
        Thread.sleep(5000);
        return randomNumbers;
    }
}
