package softuni.exam.models.dto.jsons;

import com.google.gson.annotations.Expose;


import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class BookSeedDto implements Serializable {
    @Expose
    @Size(min = 3, max = 40)
    private String author;
    @Expose
    private boolean available;
    @Expose
    @Size(min = 5)
    private String description;
    @Expose
    private String genre;
    @Expose
    @Size(min = 3, max = 40)
    private String title;
    @Expose
    @Positive
    private double rating;




    public BookSeedDto() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
