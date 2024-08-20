package com.kafka.demo.services;

import com.kafka.demo.dtos.PriceEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaMessageConsumer {
    private final PcwService pcwService;

    @KafkaListener(topics = "price", groupId = "priceGroup")
    public void consumePrices(PriceEvent priceEvent) {
        log.info("Pulling from price : " + priceEvent.toString());
        pcwService.sendPricesToUser(priceEvent);
    }
}
