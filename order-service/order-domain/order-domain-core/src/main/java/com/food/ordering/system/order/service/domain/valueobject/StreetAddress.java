package com.food.ordering.system.order.service.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode
@RequiredArgsConstructor
public class StreetAddress {
    private final UUID id;
    private final String street;
    private final String postalCode;
    private final String city;
}
