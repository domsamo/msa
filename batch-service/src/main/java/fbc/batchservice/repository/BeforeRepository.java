package fbc.batchservice.repository;

import fbc.batchservice.entity.BeforeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ===========================================================
 *
 * @author : jglee
 * @packageName : fbc.batchservice.repository
 * @fileName : BeforeRepository
 * @date : 24. 12. 4.
 * @description :
 * ===========================================================
 */
public interface BeforeRepository extends JpaRepository<BeforeEntity, Long> {
}
