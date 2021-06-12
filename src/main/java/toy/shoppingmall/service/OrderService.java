package toy.shoppingmall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.shoppingmall.domain.*;
import toy.shoppingmall.domain.item.Item;
import toy.shoppingmall.repository.ItemRepository;
import toy.shoppingmall.repository.MemberRepository;
import toy.shoppingmall.repository.OrderRepository;
import toy.shoppingmall.repository.OrderSearch;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, List<OrderItemDto> orderItemDtos) {

        int totalPrice = 0; //전체 주문 가격

        //회원 조회
        Member member = memberRepository.findById(memberId).orElseGet(null);

        //배송 정보 생성
        Delivery delivery = new Delivery(member.getAddress(), DeliveryStatus.READY);

        //주문상품 생성
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDto orderItemDto : orderItemDtos) {
            Long itemId = orderItemDto.getItemId();
            int count = orderItemDto.getCount();

            Item item = itemRepository.findById(itemId).orElseGet(null);
            OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
            totalPrice += orderItem.getTotalPrice();

            orderItems.add(orderItem);
        }

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItems, totalPrice);

        //주문 저장
        orderRepository.save(order);
        return order.getId();
    }

    /**
     * 주문 조회
     */
    List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch);
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseGet(null);
        order.cancel();
    }
}
