package fbc.orderservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * ===========================================================
 *
 * @author : jglee
 * @packageName : fbc.orderservice.dto
 * @fileName : Schema.java
 * @date : 24. 10. 2.
 * @description : Schema
 * ===========================================================
 */
@Data
@Builder
public class Schema {
    private String type;
    private List<Field> fields;
    private boolean optional;
    private String name;
}
