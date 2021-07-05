package toy.shoppingmall;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import toy.shoppingmall.domain.*;
import toy.shoppingmall.domain.item.Book;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * 총 주문 2개
 * userA
 * JPA1 BOOK
 * JPA2 BOOK
 * userB
 * SPRING1 BOOK
 * SPRING2 BOOK
 */
@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Member member = createMember("userA", "서울", "1", "1111");
            em.persist(member);

            Book book1 = createBook("JPA1 BOOK", 10000, 100, "han", "12345");
            em.persist(book1);

            Book book2 = createBook("JPA2 BOOK", 20000, 100, "yeong", "12346");
            em.persist(book2);

            List<OrderItem> orderItems = new ArrayList<>();
            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);
            orderItems.add(orderItem1);
            orderItems.add(orderItem2);

            Delivery delivery = createDelivery(member);

            int totalPrice = 0;
            for (OrderItem orderItem : orderItems) {
                totalPrice += orderItem.getTotalPrice();
            }

            Order order = Order.createOrder(member, delivery, orderItems, totalPrice);
            em.persist(order);
        }

        public void dbInit2() {
            Member member = createMember("userB", "진주", "2", "2222");
            em.persist(member);

            Book book1 = createBook("SPRING1 BOOK", 20000, 200, "han", "12345");
            em.persist(book1);

            Book book2 = createBook("SPRING2 BOOK", 40000, 300, "yeong", "12346");
            em.persist(book2);

            List<OrderItem> orderItems = new ArrayList<>();
            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);
            orderItems.add(orderItem1);
            orderItems.add(orderItem2);

            Delivery delivery = createDelivery(member);

            int totalPrice = 0;
            for (OrderItem orderItem : orderItems) {
                totalPrice += orderItem.getTotalPrice();
            }

            Order order = Order.createOrder(member, delivery, orderItems, totalPrice);
            em.persist(order);
        }

        private Member createMember(String name, String city, String street, String zipcode) {
            return new Member(name, new Address(city, street, zipcode));
        }

        private Book createBook(String name, int price, int stockQuantity, String author, String isbn) {
            return new Book(name, price, stockQuantity, author, isbn);
        }

        private Delivery createDelivery(Member member) {
            return new Delivery(member.getAddress(), DeliveryStatus.READY);
        }
    }
}
