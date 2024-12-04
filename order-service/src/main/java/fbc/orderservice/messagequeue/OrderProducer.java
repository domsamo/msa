package fbc.orderservice.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fbc.orderservice.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

//import org.springframework.kafka.core.KafkaTemplate;

/**
 * ===========================================================
 *
 * @author : jglee
 * @packageName : fbc.orderservice.messagequeue
 * @fileName : OrderProducer.java
 * @date : 24. 10. 2.
 * @description : OrderProducer
 *         {"schema":{
 *             "type":"struct",
 *             "fields":[
 *                 {"type":"int32","optional":false,"field":"id"},
 *                 {"type":"string","optional":true,"field":"user_id"},
 *                 {"type":"string","optional":true,"field":"pwd"},
 *                 {"type":"string","optional":true,"field":"name"},
 *                 {"type":"int64","optional":false,"name":"org.apache.kafka.connect.data.Timestamp","version":1,"field":"created_at"}
 *             ],
 *             "optional":false,
 *             "name":"users"},
 *         "payload":{"id":2,"user_id":"admin","pwd":"0","name":"Administrator","created_at":1732598179000}
 *         }
 *
 * Sink Connector 등록 (Kafka -> Mysql로 Order정보 등록)
 *  {
 *      "name" : "my-order-sink-connect",
 *      "config" : {
 *          "connector.class" : "io.confluent.connect.jdbc.JdbcSinkConnector",
 *          "connection.url":"jdbc:mysql://localhost:3306/msadb",
 *          "connection.user":"root",
 *          "connection.password":"1q2w3e4r!",
 *          "auto.create": "true",
 *          "auto.evolve": "true",
 *          "delete.enabled" : "false",
 *          "tasks.max" : "1",
 *          "topics" : "orders"
 *      }
 * }
 * ===========================================================
 */
@Service
@Slf4j
public class OrderProducer {
    private KafkaTemplate<String, String> kafkaTemplate;

    List<Field> fields = Arrays.asList(new Field("string", true, "order_id"),
            new Field("string", true, "user_id"),
            new Field("string", true, "product_id"),
            new Field("int32", true, "qty"),
            new Field("int32", true, "unit_price"),
            new Field("int32", true, "total_price"));
    Schema schema = Schema.builder()
            .type("struct")
            .fields(fields)
            .optional(false)
            .name("orders")
            .build();

    @Autowired
    public OrderProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * 실제 사용자가 입력하는 데이터(payload) 생성
     * @param topic
     * @param orderDto
     * @return
     */
    public OrderDto send(String topic, OrderDto orderDto) {
        Payload payload = Payload.builder()
                .order_id(orderDto.getOrderId())
                .user_id(orderDto.getUserId())
                .product_id(orderDto.getProductId())
                .qty(orderDto.getQty())
                .unit_price(orderDto.getUnitPrice())
                .total_price(orderDto.getTotalPrice())
                .build();

        KafkaOrderDto kafkaOrderDto = new KafkaOrderDto(schema, payload);

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";
        try {
            jsonInString = mapper.writeValueAsString(kafkaOrderDto);
            log.info("jsonInString:{}", jsonInString);
        } catch(JsonProcessingException ex) {
            ex.printStackTrace();
        }

        kafkaTemplate.send(topic, jsonInString);
        log.info("Order Producer sent data from the Order microservice: " + kafkaOrderDto);

        return orderDto;
    }
}
