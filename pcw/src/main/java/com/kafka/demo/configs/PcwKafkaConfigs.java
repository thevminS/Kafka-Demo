package com.kafka.demo.configs;

import com.kafka.demo.constants.ConfigConstants;
import com.kafka.demo.dtos.Price;
import com.kafka.demo.dtos.Quote;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class PcwKafkaConfigs {

    @Bean
    public NewTopic createTopic() {
        return TopicBuilder.name(ConfigConstants.REQUEST_TOPIC_NAME)
                .partitions(ConfigConstants.REQUEST_TOPIC_PARTITION_COUNT)
                .build();
    }

    @Bean
    public ReplyingKafkaTemplate<String, Quote, Price> replyKafkaTemplate(
            ProducerFactory<String, Quote> producerFactory,
            KafkaMessageListenerContainer<String, Price> container) {
        ReplyingKafkaTemplate<String, Quote, Price> template = new ReplyingKafkaTemplate<>(producerFactory, container);
        template.setDefaultReplyTimeout(Duration.ofSeconds(10));
        template.setSharedReplyTopic(true);
        return template;
    }

    @Bean
    public KafkaMessageListenerContainer<String, Price> replyContainer(ConsumerFactory<String, Price> consumerFactory) {
        ContainerProperties containerProperties = new ContainerProperties(ConfigConstants.REPLY_TOPIC_NAME);
        return new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
    }

    @Bean
    public ProducerFactory<String, Quote> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, ConfigConstants.KAFKA_PORT);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, ConfigConstants.ROUND_ROBIN_PARTITIONER_CLASS);
        return props;
    }
}
