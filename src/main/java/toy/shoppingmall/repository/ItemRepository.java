package toy.shoppingmall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.shoppingmall.domain.item.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
