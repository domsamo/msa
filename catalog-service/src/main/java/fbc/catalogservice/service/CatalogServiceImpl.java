package fbc.catalogservice.service;

import fbc.catalogservice.jpa.CatalogEntity;
import fbc.catalogservice.jpa.CatalogRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ===========================================================
 *
 * @author : jglee
 * @packageName : fbc.catalogservice.service
 * @fileName : CatalogServiceImpl.java
 * @date : 24. 10. 2.
 * @description : CatalogServiceImpl
 * ===========================================================
 */
@Data
@Slf4j
@Service
public class CatalogServiceImpl implements CatalogService{
    CatalogRepository catalogRepository;

    @Autowired
    public CatalogServiceImpl(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    @Override
    public Iterable<CatalogEntity> getAllCatalogs() {
        return catalogRepository.findAll();
    }
}
