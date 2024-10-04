package fbc.orderservice.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fbc.orderservice.dto.OrderDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * ===========================================================
 *
 * @author : jglee
 * @packageName : fbc.orderservice.messagequeue
 * @fileName : KafkaProducer.java
 * @date : 24. 10. 2.
 * @description : KafkaProducer
 * ===========================================================
 */
@Service
@Slf4j
public class KafkaProducer {
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public OrderDto send(String topic, OrderDto orderDto) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";
        try {
            jsonInString = mapper.writeValueAsString(orderDto);
        } catch(JsonProcessingException ex) {
            ex.printStackTrace();
        }

        kafkaTemplate.send(topic, jsonInString);
        log.info("Kafka Producer sent data from the Order microservice: " + orderDto);

        return orderDto;
    }
}
