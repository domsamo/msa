package fbc.orderservice.vo;

import lombok.Data;

/**
 * ===========================================================
 *
 * @author : jglee
 * @packageName : fbc.orderservice.dto
 * @fileName : RequestOrder.java
 * @date : 24. 10. 2.
 * @description : RequestOrder
 * ===========================================================
 */
@Data
public class RequestOrder {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
}
