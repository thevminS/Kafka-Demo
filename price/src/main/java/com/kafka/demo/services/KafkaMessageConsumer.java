package com.kafka.demo.services;

import com.kafka.demo.dtos.QuoteEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaMessageConsumer {
    private final PriceService priceService;

    @KafkaListener(topics = "quote", groupId = "quoteGroup")
    public void consumeQuotes(QuoteEvent quoteEvent) {
        log.info("Pulling from quote : " + quoteEvent.toString());
        priceService.sendPrices(quoteEvent);
    }
}
