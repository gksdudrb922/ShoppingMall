package toy.shoppingmall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.shoppingmall.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
}
