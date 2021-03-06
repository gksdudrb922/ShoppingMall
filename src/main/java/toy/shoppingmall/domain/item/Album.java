package toy.shoppingmall.domain.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.shoppingmall.controller.ItemForm;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue("A")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Album extends Item {
    private String artist;
    private String etc;

    public Album(String name, int price, int stockQuantity, String artist, String etc) {
        super(name, price, stockQuantity);
        this.artist = artist;
        this.etc = etc;
    }

    public void updateItem(ItemForm form) {
        name = form.getName();
        price = form.getPrice();
        stockQuantity = form.getStockQuantity();
        artist = form.getArtist();
        etc = form.getEtc();
    }
}
