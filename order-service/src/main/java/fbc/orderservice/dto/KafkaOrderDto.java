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
 * ===========================================================
 */
@Data
@AllArgsConstructor
public class KafkaOrderDto implements Serializable {
    private Schema schema;
    private Payload payload;
}
