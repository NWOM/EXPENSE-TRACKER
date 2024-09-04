package authService.eventProducer;

import authService.model.UserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class UserInfoProducer
{
   private final KafkaTemplate<String, UserInfoDTO> kafkaTemplate;
   @Value("${spring.kafka.topic-json.name}")
   private  String TOPIC_NAME;
   @Autowired
   UserInfoProducer(KafkaTemplate<String,UserInfoDTO> kafkaTemplate){
      this.kafkaTemplate=kafkaTemplate;
   }
   public void sendEventToKafka(UserInfoEvent userInfoEvent){
      Message<UserInfoEvent> message= MessageBuilder.withPayload(userInfoEvent).
              setHeader(KafkaHeaders.TOPIC,TOPIC_NAME).build();
      kafkaTemplate.send(message);
   }

}
//THIS CLASS IS RESPONSIBLE FOR SENDING EVENTS TO KAFKA TOPIC
//IT SENDS USER-RELATED EVENTS ('UserInfoEvent') TO A SPECIFIED KAFKA TOPIC. THIS ALLOWS
//OTHER SERVICES TO CONSUME AND PROCESS THIS EVENT ASYNCHRONOUSLY
