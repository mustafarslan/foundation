package com.foundation.core.service;

import com.foundation.core.dto.GreetingResponseDto;
import com.foundation.core.entity.Greet;
import com.foundation.core.repository.GreetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Profile("dev")
public class GreetService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ModelMapper modelMapper;
    private final GreetRepository greetRepository;

    public GreetingResponseDto greet() {
        Greet greet = greetRepository.findById(1L);
        sendMessage("hello World");
        return modelMapper.map(greet, GreetingResponseDto.class);
    }

    public void sendMessage(String message) {
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("foundation-greet-topic", message);
        future.whenComplete((result, ex) -> {
            if(ex == null) {
                log.error("Sent message=[{}] with offset=[{}]", message, result.getRecordMetadata().offset());
            } else {
                log.error("Unable to send message=[{}] due to : {}", message, ex.getMessage());
            }
        });
    }

    @KafkaListener(topics="foundation-greet-topic", groupId="greet")
    public void listenGroupGreet(String message){
        log.info("from topic={}, group={}, message:{}", "foundation-greet-topic", "greet", message);
    }
}
