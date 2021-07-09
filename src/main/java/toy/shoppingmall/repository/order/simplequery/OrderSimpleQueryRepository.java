package toy.shoppingmall.repository.order.simplequery;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import toy.shoppingmall.domain.Order;

import javax.persistence.EntityManager;
import java.util.List;

import static toy.shoppingmall.domain.QDelivery.delivery;
import static toy.shoppingmall.domain.QMember.member;
import static toy.shoppingmall.domain.QOrder.order;

@Repository
public class OrderSimpleQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public OrderSimpleQueryRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Page<OrderSimpleQueryDto> findOrderDtos(Pageable pageable) {
        List<OrderSimpleQueryDto> content = queryFactory
                .select(new QOrderSimpleQueryDto(
                        order.id,
                        member.name,
                        order.orderDate,
                        order.status,
                        delivery.address))
                .from(order)
                .join(order.member, member)
                .join(order.delivery, delivery)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Order> countQuery = queryFactory
                .selectFrom(order);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }
}
