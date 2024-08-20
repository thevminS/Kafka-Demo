package com.kafka.demo.confige;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class PriceKafkaConfig {

    @Bean
    public NewTopic createTopic(){
        return TopicBuilder.name("price").build();
    }
}
