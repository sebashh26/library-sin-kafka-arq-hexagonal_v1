package com.mitocode.library.infraestructure.in.messaging.config;

import java.io.IOException;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties.AckMode;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitocode.library.infraestructure.in.messaging.dto.request.CreateOrderBookEventRequest;

import lombok.RequiredArgsConstructor;

@EnableKafka
@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

	private final KafkaProperties kafkaProperties;

    @Bean
    ConsumerFactory<String, CreateOrderBookEventRequest> consumerFactoryOrderBook() throws IOException {
        ObjectMapper om = new ObjectMapper();
        
        return new DefaultKafkaConsumerFactory<>(
            kafkaProperties.buildConsumerProperties(),
            new StringDeserializer(),
            new JsonDeserializer<>(CreateOrderBookEventRequest.class, om, false)
            );
    }

    @Bean("ordersBookListener")
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, CreateOrderBookEventRequest>> kafkaListenerContainerFactoryOrderBook() throws IOException {
        ConcurrentKafkaListenerContainerFactory<String, CreateOrderBookEventRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryOrderBook());
        factory.getContainerProperties().setAckMode(AckMode.MANUAL_IMMEDIATE);
        factory.setConcurrency(1); // Configura el número de hilos para procesar mensajes en paralelo, ajusta según tus necesidades
        //lo siguiente es para los reintentos, en este caso no se hace ningun reintento, 
        //si quieres hacer reintentos puedes configurar el FixedBackOff con el tiempo de espera 
        //entre reintentos y el numero de reintentos, tambien puedes configurar un deadletter topic 
        //para los mensajes que no se puedan procesar despues de los reintentos
        //Reintenta 3 veces con backoff exponencial
        //FixedBackOff backOff = new FixedBackOff(2000L, 3); // delay 2s, 3 intentos
        factory.setCommonErrorHandler(new DefaultErrorHandler(new FixedBackOff(0L, 0L))); // to deadletter topic, 0 retries | or without deadletter this can to controle kafkalistener
        return factory;
    }
    
    @Bean
    ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties());
    }

    @Bean
    KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
