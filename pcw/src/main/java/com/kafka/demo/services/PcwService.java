package com.kafka.demo.services;

import com.kafka.demo.dtos.Price;
import com.kafka.demo.dtos.Quote;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Service
@Slf4j
public class PcwService {

    private final ReplyingKafkaTemplate<String, Quote, Price> kafkaTemplate;

    public Price sendToPrice(Quote quote) throws ExecutionException, InterruptedException {
        log.info("Quote : {}", quote);
        String requestTopic = "quote";
        ProducerRecord<String, Quote> record = new ProducerRecord<>(requestTopic, quote);
        String requestReplyTopic = "price";
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, requestReplyTopic.getBytes()));

        RequestReplyFuture<String, Quote, Price> replyFuture = kafkaTemplate.sendAndReceive(record);

        CompletableFuture<?> sendResult = replyFuture.whenComplete((result, exception) -> {
            if (exception == null) {
                System.out.println("Topic created successfully");
            } else {
                System.out.println("Failed to create topic: " + exception.getMessage());
            } });
        log.info("Results : {}" ,sendResult);
//        sendResult.getProducerRecord().headers().forEach(header -> log.info(header.key() + ":" + Arrays.toString(header.value())));

        ConsumerRecord<String, Price> consumerRecord = replyFuture.get();
        log.info("Quote Price : {}", consumerRecord.value());
        return consumerRecord.value();
    }

}
