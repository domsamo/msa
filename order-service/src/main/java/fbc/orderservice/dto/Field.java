package fbc.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ===========================================================
 *
 * @author : jglee
 * @packageName : fbc.orderservice.dto
 * @fileName : Field.java
 * @date : 24. 10. 2.
 * @description : Kafka producer JDBC 송신용 Field
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
public class Field {
    private String type;
    private boolean optional;
    private String field;
}
