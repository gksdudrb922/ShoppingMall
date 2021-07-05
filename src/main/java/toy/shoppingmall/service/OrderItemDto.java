package toy.shoppingmall.service;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class OrderItemDto {

    private Long itemId; //상품 아이디
    private Integer count; // 주문 수량
    private List<OrderItemDto> orderItemList;

    public OrderItemDto(Long itemId, Integer count) {
        this.itemId = itemId;
        this.count = count;
    }
}
