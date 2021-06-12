package toy.shoppingmall.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy.shoppingmall.domain.*;
import toy.shoppingmall.domain.item.Album;
import toy.shoppingmall.domain.item.Book;
import toy.shoppingmall.exception.NotEnoughStockException;
import toy.shoppingmall.repository.OrderRepository;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() {
        //given
        Member member = new Member("kim", new Address("서울", "도산대로", "12345"));
        Book book = new Book("jpaBook", 10000, 10, "han", "12345");
        Album album = new Album("jpaAlbum", 20000, 20, "yeong", "cool");

        em.persist(member);
        em.persist(book);
        em.persist(album);

        List<OrderItemDto> orderItemDtos = new ArrayList<>();
        orderItemDtos.add(new OrderItemDto(book.getId(), 2));
        orderItemDtos.add(new OrderItemDto(album.getId(), 3));

        //when
        Long orderId = orderService.order(member.getId(), orderItemDtos);

        //then
        Order getOrder = orderRepository.findById(orderId).orElseGet(null);
        assertThat(OrderStatus.ORDER).isEqualTo(getOrder.getStatus());
        assertThat(2).isEqualTo(getOrder.getOrderItems().size());
        assertThat(10000 * 2 + 20000 * 3).isEqualTo(getOrder.getTotalPrice());
        assertThat(8).isEqualTo(book.getStockQuantity());
     }

     @Test
     public void 상품주문_재고수량초과() {
         //given
         Member member = new Member("kim", new Address("서울", "도산대로", "12345"));
         Book book = new Book("jpaBook", 10000, 10, "han", "12345");
         Album album = new Album("jpaAlbum", 20000, 20, "yeong", "cool");

         em.persist(member);
         em.persist(book);
         em.persist(album);

         //when
         List<OrderItemDto> orderItemDtos = new ArrayList<>();
         orderItemDtos.add(new OrderItemDto(book.getId(), 12));
         orderItemDtos.add(new OrderItemDto(album.getId(), 3));

         //then
         assertThrows(NotEnoughStockException.class, () -> orderService.order(member.getId(), orderItemDtos));
      }

      @Test
      public void 주문취소() {
          //given
          Member member = new Member("kim", new Address("서울", "도산대로", "12345"));
          Book book = new Book("jpaBook", 10000, 10, "han", "12345");
          Album album = new Album("jpaAlbum", 20000, 20, "yeong", "cool");

          em.persist(member);
          em.persist(book);
          em.persist(album);

          List<OrderItemDto> orderItemDtos = new ArrayList<>();
          orderItemDtos.add(new OrderItemDto(book.getId(), 2));
          orderItemDtos.add(new OrderItemDto(album.getId(), 3));

          Long orderId = orderService.order(member.getId(), orderItemDtos);

          //when
          orderService.cancelOrder(orderId);

          //then
          Order getOrder = orderRepository.findById(orderId).orElseGet(null);
          assertThat(OrderStatus.CANCEL).isEqualTo(getOrder.getStatus());
          assertThat(book.getStockQuantity()).isEqualTo(10);
       }

}