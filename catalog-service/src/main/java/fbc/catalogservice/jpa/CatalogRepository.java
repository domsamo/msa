package fbc.catalogservice.jpa;

import org.springframework.data.repository.CrudRepository;

/**
 * ===========================================================
 *
 * @author : jglee
 * @packageName : fbc.catalogservice.jpa
 * @fileName : CatalogRepository.java
 * @date : 24. 10. 2.
 * @description : CatalogRepository
 * ===========================================================
 */
public interface CatalogRepository extends CrudRepository<CatalogEntity, Long> {
    CatalogEntity findByProductId(String productId);
}
