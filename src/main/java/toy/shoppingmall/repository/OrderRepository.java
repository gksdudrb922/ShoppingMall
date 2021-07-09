package toy.shoppingmall.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import toy.shoppingmall.domain.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {

    @EntityGraph(attributePaths = {"member", "delivery"})
    @Query("select o from Order o")
    List<Order> findAllWithMemberDelivery();

    @EntityGraph(attributePaths = {"member", "delivery"})
    @Query("select o from Order o")
    Page<Order> findAllWithMemberDelivery(Pageable pageable);

    @EntityGraph(attributePaths = {"member", "delivery", "orderItems", "orderItems.item"})
    @Query("select o from Order o")
    List<Order> findAllWithItem();
}
