package fbc.orderservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * ===========================================================
 *
 * @author : jglee
 * @packageName : fbc.orderservice.dto
 * @fileName : ResponseOrder.java
 * @date : 24. 10. 2.
 * @description : ResponseOrder
 * ===========================================================
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseOrder {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private Date createdAt;

    private String orderId;
}
