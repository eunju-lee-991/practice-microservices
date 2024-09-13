package com.food.ordering.system.order.service.domain.ports.input.service;

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse;
import jakarta.validation.Valid;

public interface OrderApplicationService {

// 입력 포트는 도메인 계층에서 구현되고 도메인의 클라이언트가 사용하는 인터페이스
    // 출력 포트는 데이터 액세스와 같이 인프라 계층에서 구현되는 인터페이스
    // 또는 메시징 모듈을 사용하고 해당 인프라 계층에 도달하기 위해 도메인 계층에서 사용
  // 이벤트 리스너는 특별한 유형의 애플리케이션 서비스이며, 도메인 이벤트에 의해 트리거 됨

    // a method's preconditions (as represented by parameter constraints) must not be strengthened in sub types
    CreateOrderResponse createOrder(@Valid CreateOrderCommand createOrderCommand);

    TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);
}
