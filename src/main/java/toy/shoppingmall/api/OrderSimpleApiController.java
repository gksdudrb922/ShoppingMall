package toy.shoppingmall.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import toy.shoppingmall.domain.Address;
import toy.shoppingmall.domain.Order;
import toy.shoppingmall.domain.OrderStatus;
import toy.shoppingmall.repository.OrderRepository;
import toy.shoppingmall.repository.order.simplequery.OrderSimpleQueryDto;
import toy.shoppingmall.repository.order.simplequery.OrderSimpleQueryRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @GetMapping("api/v1/simple-orders")
    public Result ordersV1() {
        List<Order> all = orderRepository.findAll();
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
        }
        return new Result(all);
    }

    @GetMapping("api/v2/simple-orders")
    public Result ordersV2() {
        List<Order> orders = orderRepository.findAll();
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
        return new Result(result);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @GetMapping("api/v3/simple-orders")
    public Page<SimpleOrderDto> ordersV3(Pageable pageable) {
        Page<Order> orders = orderRepository.findAllWithMemberDelivery(pageable);
        Page<SimpleOrderDto> result = orders.map(SimpleOrderDto::new);
        return result;
    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }

    @GetMapping("/api/v4/simple-orders")
    public Page<OrderSimpleQueryDto> ordersV4(Pageable pageable) {
        return orderSimpleQueryRepository.findOrderDtos(pageable);
    }
}
