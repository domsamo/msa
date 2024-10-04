package fbc.orderservice.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * ===========================================================
 *
 * @author : jglee
 * @packageName : fbc.orderservice.dto
 * @fileName : OrderDto.java
 * @date : 24. 10. 2.
 * @description : OrderDto
 * ===========================================================
 */
@Data
public class OrderDto implements Serializable {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;

    private String orderId;
    private String userId;
}
