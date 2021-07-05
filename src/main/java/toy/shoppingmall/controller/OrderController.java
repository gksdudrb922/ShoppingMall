package toy.shoppingmall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import toy.shoppingmall.domain.Member;
import toy.shoppingmall.domain.Order;
import toy.shoppingmall.domain.OrderItem;
import toy.shoppingmall.domain.item.Item;
import toy.shoppingmall.repository.OrderRepository;
import toy.shoppingmall.repository.OrderSearch;
import toy.shoppingmall.service.ItemService;
import toy.shoppingmall.service.MemberService;
import toy.shoppingmall.service.OrderItemDto;
import toy.shoppingmall.service.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final MemberService memberService;
    private final ItemService itemService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @GetMapping("/order")
    public String createForm(Model model) {
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String order(Long memberId, OrderItemDto orderItemDto) {
        List<OrderItemDto> orderItemDtos = orderItemDto.getOrderItemList()
                .stream()
                .map(o -> new OrderItemDto(o.getItemId(), o.getCount()))
                .collect(Collectors.toList());
        orderService.order(memberId, orderItemDtos);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {

        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);

        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {

        orderService.cancelOrder(orderId);

        return "redirect:/orders";
    }
}
