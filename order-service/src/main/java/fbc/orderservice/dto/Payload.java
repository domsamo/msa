package fbc.orderservice.dto;

import lombok.Builder;
import lombok.Data;

/**
 * ===========================================================
 *
 * @author : jglee
 * @packageName : fbc.orderservice.dto
 * @fileName : Payload.java
 * @date : 24. 10. 2.
 * @description : Payload
 * ===========================================================
 */
@Data
@Builder
public class Payload {
    private String order_id;
    private String user_id;
    private String product_id;
    private int qty;
    private int unit_price;
    private int total_price;
}
