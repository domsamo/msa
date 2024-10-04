package fbc.orderservice.jpa;

import org.springframework.data.repository.CrudRepository;

/**
 * ===========================================================
 *
 * @author : jglee
 * @packageName : fbc.orderservice.jpa
 * @fileName : OrderRepository.java
 * @date : 24. 10. 2.
 * @description : OrderRepository
 * ===========================================================
 */
public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
    OrderEntity findByOrderId(String orderId);
    Iterable<OrderEntity> findByUserId(String userId);
}
