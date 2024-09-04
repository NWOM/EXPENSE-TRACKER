package com.sagnik.userService.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sagnik.userService.entities.UserInfoDto;
import com.sagnik.userService.repository.UserRepository;
import com.sagnik.userService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceConsumer {
    @Autowired
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "${spring.kafka.topic-json.name}",groupId = "${spring.kafka.consumer.group-id}")
    public void listen(UserInfoDto eventData){
        try{
            //Todo: Make it Transactional , to handle idempotency and validate email,phoneNumber etc
          userService.createOrUpdateUser(eventData);

        }catch(Exception ex){
            ex.printStackTrace();
           System.out.println("AuthServiceConsumer: Exception is thrown while consuming Kafka Event");
        }
    }
}
