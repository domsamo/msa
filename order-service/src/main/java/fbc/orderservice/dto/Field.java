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
 * @description : Field
 * ===========================================================
 */
@Data
@AllArgsConstructor
public class Field {
    private String type;
    private boolean optional;
    private String field;
}
