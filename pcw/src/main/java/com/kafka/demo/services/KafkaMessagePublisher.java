package com.kafka.demo.services;

import com.kafka.demo.dtos.QuoteEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaMessagePublisher {
    private final KafkaTemplate<String, QuoteEvent> kafkaTemplate;

    public void publishToQuote(QuoteEvent quoteEvent) {
        log.info("Pushing to quote : " + quoteEvent.toString());
        kafkaTemplate.send("quote", quoteEvent);
    }

}

