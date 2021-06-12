package toy.shoppingmall.service;

import lombok.Data;

@Data
public class OrderItemDto {

    private Long itemId; //상품 아이디
    private int count; // 주문 수량

    public OrderItemDto(Long itemId, int count) {
        this.itemId = itemId;
        this.count = count;
    }
}
