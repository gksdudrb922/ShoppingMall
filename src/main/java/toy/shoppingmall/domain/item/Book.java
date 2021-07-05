package toy.shoppingmall.domain.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.shoppingmall.controller.ItemForm;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue("B")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends Item {
    private String author;
    private String isbn;

    public Book(String name, int price, int stockQuantity, String author, String isbn) {
        super(name, price, stockQuantity);
        this.author = author;
        this.isbn = isbn;
    }

    public void updateItem(ItemForm form) {
        name = form.getName();
        price = form.getPrice();
        stockQuantity = form.getStockQuantity();
        author=form.getAuthor();
        isbn = form.getIsbn();
    }
}
