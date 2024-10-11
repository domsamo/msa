package fbc.userservice.client;

import fbc.userservice.error.FeignErrorDecoder2;
import fbc.userservice.vo.ResponseCatalog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name="catalog-service", configuration = FeignErrorDecoder2.class)
public interface CatalogServiceClient {

    @GetMapping("/catalog-service/getCatalogs_wrong")
    List<ResponseCatalog> getCatalogs();

}
