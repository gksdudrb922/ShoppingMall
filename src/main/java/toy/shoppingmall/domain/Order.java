package toy.shoppingmall.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(cascade = ALL, fetch = LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private int totalPrice;

    //==생성 메서드==//
    public static Order createOrder(Member member, Delivery delivery, List<OrderItem> orderItems, int totalPrice) {
        Order order = new Order(member, delivery, LocalDateTime.now(), OrderStatus.ORDER, totalPrice);
        for (OrderItem orderItem : orderItems) {
            order.getOrderItems().add(orderItem);
            orderItem.setOrder(order);
        }
        return order;
    }

    private Order(Member member, Delivery delivery, LocalDateTime orderDate, OrderStatus status, int totalPrice) {
        this.member = member;
        this.delivery = delivery;
        this.orderDate = orderDate;
        this.status = status;
        this.totalPrice = totalPrice;
    }

    //==비즈니스 메서드==//
    /**
     * 주문 취소
     */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
        status = OrderStatus.CANCEL;
    }
}
