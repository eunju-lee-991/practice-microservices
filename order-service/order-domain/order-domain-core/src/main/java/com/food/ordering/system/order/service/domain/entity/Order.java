package com.food.ordering.system.order.service.domain.entity;

import com.food.ordering.system.domain.entity.AggregateRoot;
import com.food.ordering.system.domain.valueobject.*;
import com.food.ordering.system.order.service.domain.valueobject.StreetAddress;
import com.food.ordering.system.order.service.domain.valueobject.TrackingId;
import lombok.Getter;

import java.util.List;

@Getter
public class Order extends AggregateRoot<OrderId> {
    private final CustomerId customerId;
    private final RestaurantId restaurantId;
    private final StreetAddress deliveryAddress;
    private final Money money;
    private final List<OrderItem> items;
    private TrackingId trackingId;
    private OrderStatus orderStatus;
    private List<String> failureMessages;

    public static Builder builder() {
        return new Builder();
    }

    // private 생성자
    private Order(Builder builder) {
        super.setId(builder.orderId); // AggregateRoot의 생성자에 OrderId를 전달
        this.customerId = builder.customerId;
        this.restaurantId = builder.restaurantId;
        this.deliveryAddress = builder.deliveryAddress;
        this.money = builder.money;
        this.items = builder.items;
        this.trackingId = builder.trackingId;
        this.orderStatus = builder.orderStatus;
        this.failureMessages = builder.failureMessages;
    }

    // Builder 클래스
    public static class Builder {
        private OrderId orderId;
        private CustomerId customerId;
        private RestaurantId restaurantId;
        private StreetAddress deliveryAddress;
        private Money money;
        private List<OrderItem> items;
        private TrackingId trackingId;
        private OrderStatus orderStatus;
        private List<String> failureMessages;

        // 각 필드에 대한 메서드
        public Builder orderId(OrderId id) {
            this.orderId = id;
            return this;
        }

        public Builder customerId(CustomerId customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder restaurantId(RestaurantId restaurantId) {
            this.restaurantId = restaurantId;
            return this;
        }

        public Builder deliveryAddress(StreetAddress deliveryAddress) {
            this.deliveryAddress = deliveryAddress;
            return this;
        }

        public Builder money(Money money) {
            this.money = money;
            return this;
        }

        public Builder items(List<OrderItem> items) {
            this.items = items;
            return this;
        }

        public Builder trackingId(TrackingId trackingId) {
            this.trackingId = trackingId;
            return this;
        }

        public Builder orderStatus(OrderStatus orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }

        public Builder failureMessages(List<String> failureMessages) {
            this.failureMessages = failureMessages;
            return this;
        }

        // 빌드 메서드
        public Order build() {
            return new Order(this);
        }
    }
}
