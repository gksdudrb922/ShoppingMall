package toy.shoppingmall.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import toy.shoppingmall.domain.Order;
import toy.shoppingmall.domain.OrderStatus;

import javax.persistence.EntityManager;
import java.util.List;

import static org.springframework.util.StringUtils.*;
import static toy.shoppingmall.domain.QMember.*;
import static toy.shoppingmall.domain.QOrder.*;

public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public OrderRepositoryCustomImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Order> findAll(OrderSearch orderSearch) {
        return queryFactory
                .selectFrom(order)
                .join(order.member, member)
                .where(
                        nameLike(orderSearch.getMemberName()),
                        statusEq(orderSearch.getOrderStatus())
                )
                .limit(1000)
                .fetch();
    }

    private BooleanExpression nameLike(String memberName) {
        if (!hasText(memberName)) {
            return null;
        }
        return member.name.like(memberName);
    }

    private BooleanExpression statusEq(OrderStatus statusCond) {
        if (statusCond == null) {
            return null;
        }
        return order.status.eq(statusCond);
    }
}
