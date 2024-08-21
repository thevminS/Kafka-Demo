package com.kafka.demo.constants;

public class ConfigConstants {
    public static String KAFKA_PORT = "192.168.1.35:9092";
    public static String ROUND_ROBIN_PARTITIONER_CLASS = "org.apache.kafka.clients.producer.RoundRobinPartitioner";
    public static String REQUEST_TOPIC_NAME = "quote";
    public static String REPLY_TOPIC_NAME = "price";
    public static int REQUEST_TOPIC_PARTITION_COUNT = 500;

}
