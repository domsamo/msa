package fbc.catalogservice.dto;

import lombok.Data;
/**
 * ===========================================================
 *
 * @author : jglee
 * @packageName : fbc.catalogservice.dto
 * @fileName : CatalogDto.java
 * @date : 24. 10. 2.
 * @description : CatalogDto
 * ===========================================================
 */
@Data
public class CatalogDto { // implements Serializable {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;

    private String orderId;
    private String userId;
}
