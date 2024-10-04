package fbc.catalogservice.service;

import fbc.catalogservice.jpa.CatalogEntity;

/**
 * ===========================================================
 *
 * @author : jglee
 * @packageName : fbc.catalogservice.service
 * @fileName : CatalogService.java
 * @date : 24. 10. 2.
 * @description : CatalogService
 * ===========================================================
 */
public interface CatalogService {
    Iterable<CatalogEntity> getAllCatalogs();
}
