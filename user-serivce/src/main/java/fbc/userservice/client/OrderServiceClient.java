package fbc.userservice.client;

import fbc.userservice.error.FeignErrorDecoder;
import fbc.userservice.vo.ResponseOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="order-service", configuration = FeignErrorDecoder.class)
public interface OrderServiceClient {

    @GetMapping("/order-service/{userId}/orders")
//    @GetMapping("/order-service/{userId}/orders_wrong")
//    ResponseEntity<List<ResponseOrder>> getOrders(@PathVariable String userId);
    List<ResponseOrder> getOrders(@PathVariable String userId);
}
