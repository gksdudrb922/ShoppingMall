package toy.shoppingmall.domain;

import lombok.Getter;
import toy.shoppingmall.domain.item.Item;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private int count;

    //==연관관계 메서드==//
    public void setOrder(Order order) {
        this.order = order;
    }
}
