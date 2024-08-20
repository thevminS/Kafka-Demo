package com.kafka.demo.services;

import com.kafka.demo.dtos.PriceEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaMessagePublisher {
    private final KafkaTemplate<String, PriceEvent> kafkaTemplate;

    public void publishToPrice(PriceEvent priceEvent) {
        log.info("Pushing to price : " + priceEvent.toString());
        kafkaTemplate.send("price", priceEvent);
    }
}
