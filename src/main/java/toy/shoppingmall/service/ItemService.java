package toy.shoppingmall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.shoppingmall.controller.ItemForm;
import toy.shoppingmall.domain.item.Album;
import toy.shoppingmall.domain.item.Book;
import toy.shoppingmall.domain.item.Item;
import toy.shoppingmall.domain.item.Movie;
import toy.shoppingmall.repository.ItemRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public Long saveItem(Item item) {
        itemRepository.save(item);
        return item.getId();
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findById(itemId).orElseGet(null);
    }

    @Transactional
    public void updateItem(ItemForm form) {
        if (form.getTag().equals("A")) {
            Album item = (Album) itemRepository.findById(form.getId()).orElseGet(null);
            item.updateItem(form);
        } else if (form.getTag().equals("B")) {
            Book item = (Book) itemRepository.findById(form.getId()).orElseGet(null);
            item.updateItem(form);
        } else if (form.getTag().equals("M")) {
            Movie item = (Movie) itemRepository.findById(form.getId()).orElseGet(null);
            item.updateItem(form);
        }
    }
}
