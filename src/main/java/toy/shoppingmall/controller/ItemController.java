package toy.shoppingmall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import toy.shoppingmall.domain.item.Album;
import toy.shoppingmall.domain.item.Book;
import toy.shoppingmall.domain.item.Item;
import toy.shoppingmall.domain.item.Movie;
import toy.shoppingmall.service.ItemService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new ItemForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(ItemForm form) {
        if (form.getTag().equals("A")) {
            Album item = new Album(form.getName(), form.getPrice(), form.getStockQuantity(), form.getArtist(), form.getEtc());
            itemService.saveItem(item);
        } else if (form.getTag().equals("B")) {
            Book item = new Book(form.getName(), form.getPrice(), form.getStockQuantity(), form.getAuthor(), form.getIsbn());
            itemService.saveItem(item);
        } else if (form.getTag().equals("M")) {
            Movie item = new Movie(form.getName(), form.getPrice(), form.getStockQuantity(), form.getDirector(), form.getActor());
            itemService.saveItem(item);
        }
        return "redirect:/items";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Item item = itemService.findOne(itemId);
        ItemForm form = new ItemForm();
        String tag = "";

        if (item instanceof Album) {
            tag="A";
        } else if (item instanceof Book) {
            tag = "B";
        } else if (item instanceof Movie) {
            tag="M";
        }
        form.updateItemForm(item, tag);

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String updateItem(ItemForm form) {
        itemService.updateItem(form);
        return "redirect:/items";
    }
}
