package fbc.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * ===========================================================
 *
 * @author : jglee
 * @packageName : fbc.orderservice.dto
 * @fileName : KafkaOrderDto.java
 * @date : 24. 10. 2.
 * @description : KafkaOrderDto
 * Kafka source connector 용 Dto
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
 *         "payload":{"id":2,"user_id":"admin","pwd":"0","name":"Administrator","created_at":1732598179000}}
 * ===========================================================
 */
@Data
@AllArgsConstructor
public class KafkaOrderDto implements Serializable {
    private Schema schema;
    private Payload payload;
}
