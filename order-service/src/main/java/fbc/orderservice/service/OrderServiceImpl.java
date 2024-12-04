package fbc.orderservice.service;

import fbc.orderservice.dto.OrderDto;
import fbc.orderservice.jpa.OrderEntity;
import fbc.orderservice.jpa.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * ===========================================================
 *
 * @author : jglee
 * @packageName : fbc.orderservice.service
 * @fileName : OrderServiceImpl.java
 * @date : 24. 10. 2.
 * @description : OrderServiceImpl
 * ===========================================================
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDto.getQty() * orderDto.getUnitPrice());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        OrderEntity orderEntity = mapper.map(orderDto, OrderEntity.class);

        orderRepository.save(orderEntity);

        log.info("orderEntity.isPersisted() : {}", orderEntity.isPersisted());

        Optional<OrderEntity> orderEntity2 = Optional.ofNullable(orderRepository.findByOrderId(orderDto.getOrderId()));
        if(orderEntity2.isPresent()) {
            log.info("orderEntity2.isPersisted() : isPresent");
        }else{
            log.info("orderEntity2.isPersisted() : not isPresent");
        }

        OrderDto returnValue = mapper.map(orderEntity, OrderDto.class);

        return returnValue;
    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        OrderDto orderDto = new ModelMapper().map(orderEntity, OrderDto.class);

        return orderDto;
    }

    @Override
    public Iterable<OrderEntity> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }
}
