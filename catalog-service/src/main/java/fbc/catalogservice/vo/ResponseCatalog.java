package fbc.catalogservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * ===========================================================
 *
 * @author : jglee
 * @packageName : fbc.catalogservice.vo
 * @fileName : ResponseCatalog.java
 * @date : 24. 10. 2.
 * @description : ResponseCatalog
 * ===========================================================
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseCatalog {
    private String productId;
    private String productName;
    private Integer unitPrice;
    private Integer stock;
    private Date createdAt;
}
