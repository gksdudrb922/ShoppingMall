package toy.shoppingmall.controller;

import lombok.Data;
import toy.shoppingmall.domain.item.Album;
import toy.shoppingmall.domain.item.Book;
import toy.shoppingmall.domain.item.Item;
import toy.shoppingmall.domain.item.Movie;

@Data
public class ItemForm {
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    private String artist;
    private String etc;

    private String author;
    private String isbn;

    private String director;
    private String actor;

    private String tag;

    public void updateItemForm(Item item, String tag) {
        setId(item.getId());
        setName(item.getName());
        setPrice(item.getPrice());
        setStockQuantity(item.getStockQuantity());
        if (tag.equals("A")) {
            Album album = (Album) item;
            setArtist(album.getArtist());
            setEtc(album.getEtc());
        } else if (tag.equals("B")) {
            Book book = (Book) item;
            setAuthor(book.getAuthor());
            setIsbn(book.getIsbn());
        } else if (tag.equals("M")) {
            Movie movie = (Movie) item;
            setDirector(movie.getDirector());
            setActor(movie.getActor());
        }
        setTag(tag);
    }
}
