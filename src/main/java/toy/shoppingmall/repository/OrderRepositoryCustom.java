package toy.shoppingmall.repository;

import toy.shoppingmall.domain.Order;

import java.util.List;

public interface OrderRepositoryCustom {
    List<Order> findAll(OrderSearch orderSearch);
}
