package toy.shoppingmall.repository.order.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static toy.shoppingmall.domain.QDelivery.*;
import static toy.shoppingmall.domain.QMember.*;
import static toy.shoppingmall.domain.QOrder.*;
import static toy.shoppingmall.domain.QOrderItem.*;
import static toy.shoppingmall.domain.item.QItem.*;

@Repository
public class OrderQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public OrderQueryRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }


    public List<OrderQueryDto> findOrderQueryDtos() {
        List<OrderQueryDto> result = findOrders();

        result.forEach(o -> {
            List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId());
            o.setOrderItems(orderItems);
        });

        return result;
    }

    public List<OrderQueryDto> findAllByDto_optimization() {

        List<OrderQueryDto> result = findOrders();

        Map<Long, List<OrderItemQueryDto>> orderItemMap = findOrderItemMap(toOrderIds(result));

        result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));

        return result;
    }

    private List<OrderQueryDto> findOrders() {
        return queryFactory
                .select(new QOrderQueryDto(order.id, member.name, order.orderDate, order.status, delivery.address))
                .from(order)
                .join(order.member, member)
                .join(order.delivery, delivery)
                .fetch();
    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return queryFactory
                .select(new QOrderItemQueryDto(orderItem.order.id, item.name, orderItem.orderPrice, orderItem.count))
                .from(orderItem)
                .join(orderItem.item, item)
                .where(orderItem.order.id.eq(orderId))
                .fetch();
    }

    private List<Long> toOrderIds(List<OrderQueryDto> result) {
        return result.stream()
                .map(o -> o.getOrderId())
                .collect(Collectors.toList());
    }

    private Map<Long, List<OrderItemQueryDto>> findOrderItemMap(List<Long> orderIds) {
        List<OrderItemQueryDto> orderItems = queryFactory
                .select(new QOrderItemQueryDto(orderItem.order.id, item.name, orderItem.orderPrice, orderItem.count))
                .from(orderItem)
                .join(orderItem.item, item)
                .where(orderItem.order.id.in(orderIds))
                .fetch();

        return orderItems.stream()
                .collect(Collectors.groupingBy(OrderItemQueryDto::getOrderId));
    }

    public List<OrderFlatDto> findAllByDto_flat() {
        return queryFactory
                .select(new QOrderFlatDto(order.id, member.name, order.orderDate, order.status, delivery.address, item.name, orderItem.orderPrice, orderItem.count))
                .from(order)
                .join(order.member, member)
                .join(order.delivery, delivery)
                .join(order.orderItems, orderItem)
                .join(orderItem.item, item)
                .fetch();
    }
}
