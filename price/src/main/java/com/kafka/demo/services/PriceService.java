package com.kafka.demo.services;

import com.kafka.demo.dtos.Price;
import com.kafka.demo.dtos.PriceEvent;
import com.kafka.demo.dtos.QuoteEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PriceService {
    private final KafkaMessagePublisher kafkaMessagePublisher;

    public void sendPrices(QuoteEvent quoteEvent) {
        List<Integer> prices = generateRandomIntegers();
        PriceEvent priceEvent = PriceEvent.builder()
                .price(Price.builder()
                        .priceList(prices).
                        build())
                .correlationalId(quoteEvent.getCorrelationalId())
                .build();

        kafkaMessagePublisher.publishToPrice(priceEvent);
    }

    public List<Integer> generateRandomIntegers() {
        List<Integer> randomNumbers = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < random.nextInt(10); i++) {
            int randomNumber = random.nextInt(2000 - 1001) + 1000;
            randomNumbers.add(randomNumber);
        }
        return randomNumbers;
    }
}
