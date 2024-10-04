package fbc.orderservice.service;

import fbc.orderservice.dto.OrderDto;
import fbc.orderservice.jpa.OrderEntity;

/**
 * ===========================================================
 *
 * @author : jglee
 * @packageName : fbc.orderservice.service
 * @fileName : OrderService.java
 * @date : 24. 10. 2.
 * @description : OrderService
 * ===========================================================
 */
public interface OrderService {
    OrderDto createOrder(OrderDto orderDetails);
    OrderDto getOrderByOrderId(String orderId);
    Iterable<OrderEntity> getOrdersByUserId(String userId);
}
