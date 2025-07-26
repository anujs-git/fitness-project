package com.fitness.aiservice.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Bean
    public Queue activityQueue(){
      return new Queue("activity.queue", true);
    }
    @Bean
    public DirectExchange activityExchange(){
        return new DirectExchange("fitness.exchange");
    }
    @Bean
    public Binding activityBinding(Queue activityQueue, DirectExchange activityExchange){
        return  BindingBuilder.bind(activityQueue).to(activityExchange).with("activity.tracking");
    }
    @Bean
    public MessageConverter jsonMessageConverter(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // optional, more readable
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
