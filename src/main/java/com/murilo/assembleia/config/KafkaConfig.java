package com.murilo.assembleia.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.murilo.assembleia.dto.ResultadoVotacaoDTO;

@Configuration
public class KafkaConfig {
	
	private static final String PACKAGE = "com.murilo.assembleia";
	private static final String KAFKA_URL = "localhost:9092";
	
	@Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(
        		AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,
        		KAFKA_URL);
        return new KafkaAdmin(configs);
    }
	
	@Bean
    public ConsumerFactory<String, ResultadoVotacaoDTO> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                KAFKA_URL);
        configProps.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                PACKAGE);
        configProps.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        configProps.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ResultadoVotacaoDTO> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ResultadoVotacaoDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        
        factory.setConsumerFactory(consumerFactory());
        
        return factory;
    }
    
    @Bean
    public ProducerFactory<String, ResultadoVotacaoDTO> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                KAFKA_URL);
        configProps.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                PACKAGE);
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        configProps.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, ResultadoVotacaoDTO> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
