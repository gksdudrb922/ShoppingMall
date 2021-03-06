package toy.shoppingmall.domain.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.shoppingmall.controller.ItemForm;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue("M")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Movie extends Item {
    private String director;
    private String actor;

    public Movie(String name, int price, int stockQuantity, String director, String actor) {
        super(name, price, stockQuantity);
        this.director = director;
        this.actor = actor;
    }

    public void updateItem(ItemForm form) {
        name = form.getName();
        price = form.getPrice();
        stockQuantity = form.getStockQuantity();
        director = form.getDirector();
        actor = form.getActor();
    }
}
