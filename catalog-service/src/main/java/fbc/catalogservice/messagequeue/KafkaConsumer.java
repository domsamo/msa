package fbc.catalogservice.messagequeue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * ===========================================================
 *
 * @author : jglee
 * @packageName : fbc.catalogservice.messagequeue
 * @fileName : KafkaConsumer.java
 * @date : 24. 10. 2.
 * @description : KafkaConsumer
 * ===========================================================
 */
@Service
@Slf4j
public class KafkaConsumer {
//    CatalogRepository repository;
//
//    @Autowired
//    public KafkaConsumer(CatalogRepository repository) {
//        this.repository = repository;
//    }
//
//    @KafkaListener(topics = "example-catalog-topic")
//    public void updateQty(String kafkaMessage) {
//        log.info("Kafka Message: ->" + kafkaMessage);
//
//        Map<Object, Object> map = new HashMap<>();
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {});
//        } catch (JsonProcessingException ex) {
//            ex.printStackTrace();
//        }
//
//        CatalogEntity entity = repository.findByProductId((String)map.get("productId"));
//        if (entity != null) {
//            entity.setStock(entity.getStock() - (Integer)map.get("qty"));
//            repository.save(entity);
//        }
//    }
}
