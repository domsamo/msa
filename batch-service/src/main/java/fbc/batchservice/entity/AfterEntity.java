package fbc.batchservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * ===========================================================
 *
 * @author : jglee
 * @packageName : fbc.batchservice.entity
 * @fileName : AfterEntity
 * @date : 24. 12. 4.
 * @description :
 * ===========================================================
 */
@Entity
@Getter
@Setter
public class AfterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
}
