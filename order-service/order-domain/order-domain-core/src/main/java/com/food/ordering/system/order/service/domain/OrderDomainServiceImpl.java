package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService {

    private static final String UTC = "UTC";

    @Override
    public OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant) {
        validateRestaurant(restaurant);
        setOrderProductInformation(order, restaurant);
        order.validateOrder();
        order.initializeOrder();
        System.out.println("Order with id: {} is initiated");
        return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public OrderPaidEvent payOrder(Order order) {
        order.pay();
        System.out.println("Order with id: {} is paid");
        return new OrderPaidEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public void approveOrder(Order order) {
        order.approve();
        // 주문 처리의 마지막 단계이기 때문에 이벤트 반환하지 않음
        // TrackingId가 있는 Get 엔드포인트를 사용하여 데이터를 가져옴
        System.out.println("Order with id: {} is approved");
    }

    @Override
    public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages) {
        order.initCancel(failureMessages);
        System.out.println("Order payment is cancelling for order id: {}");
        return new OrderCancelledEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public void cancelOrder(Order order, List<String> failureMessages) {
        order.cancel();
        // 주문 처리의 (또 다른) 마지막 단계이기 때문에 이벤트 반환하지 않음
        System.out.println("Order with id: {} is cancelled");
    }

    private void validateRestaurant(Restaurant restaurant) {
        if (!restaurant.isActive()) {
            throw new OrderDomainException("Restaurant with id " + restaurant.getId().getValue()
                + " is currently not active!");
        }
    }

    /**
     * 이중for문을 해시맵 같은 타입으로 최적화하기
     */
    private void setOrderProductInformation(Order order, Restaurant restaurant) {
        order.getItems().forEach(oi -> restaurant.getProducts().forEach(rp -> {
            Product currentProduct = oi.getProduct();
            if (currentProduct.equals(rp)) {
                currentProduct.updateWithConfirmNameAndPrice(rp.getName(), rp.getPrice());
            }
        }));
    }
}
