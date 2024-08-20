package com.kafka.demo.services;

import com.kafka.demo.dtos.Price;
import com.kafka.demo.dtos.PriceEvent;
import com.kafka.demo.dtos.Quote;
import com.kafka.demo.dtos.QuoteEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
@Slf4j
public class PcwService {

    private final KafkaMessagePublisher kafkaMessagePublisher;
    private final ConcurrentMap<String, CompletableFuture<Price>> responseFutures = new ConcurrentHashMap<>();

    public CompletableFuture<Price> sendToPrice(Quote quote) {
        String correlationId = UUID.randomUUID().toString();
        CompletableFuture<Price> responseFuture = new CompletableFuture<>();
        // Set a timeout for the CompletableFuture
        responseFuture.orTimeout(30, TimeUnit.SECONDS)
                .exceptionally(ex -> {
                    log.error("Request with correlation ID {} timed out", correlationId, ex);
                    return null;
                });
        responseFutures.put(correlationId, responseFuture);
        QuoteEvent quoteEvent = QuoteEvent.builder()
                .quote(quote)
                .correlationalId(correlationId)
                .build();
        try {
            kafkaMessagePublisher.publishToQuote(quoteEvent);
        } catch (Exception e) {
            log.error("Failed to publish quote event", e);
            responseFutures.remove(correlationId);
            responseFuture.completeExceptionally(e);
        }
        return responseFuture;
    }

    public void sendPricesToUser(PriceEvent priceEvent) {
        log.info("Processing price event for correlation ID: {}", priceEvent.getCorrelationalId());
        CompletableFuture<Price> responseFuture = responseFutures.remove(priceEvent.getCorrelationalId());

        if (responseFuture != null) {
            log.info("Completing future with price: {}", priceEvent.getPrice());
            log.info("Response future: {}", responseFuture);
            responseFuture.complete(priceEvent.getPrice());
            log.info("Response future after: {}", responseFuture);
        } else {
            log.warn("No future found for correlation ID: {}", priceEvent.getCorrelationalId());
        }
    }
}
