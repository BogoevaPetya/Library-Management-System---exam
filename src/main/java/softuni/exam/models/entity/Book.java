package softuni.exam.models.entity;

import softuni.exam.models.entity.common.BaseEntity;
import softuni.exam.models.entity.common.Genre;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "books")
public class Book extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(nullable = false)
    private boolean available;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Genre genre;

    @Column(nullable = false)
    private double rating;

    @OneToMany(mappedBy = "book")
    private List<BorrowingRecord> borrowingRecords;






    public Book() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<BorrowingRecord> getBorrowingRecords() {
        return borrowingRecords;
    }

    public void setBorrowingRecords(List<BorrowingRecord> borrowingRecords) {
        this.borrowingRecords = borrowingRecords;
    }
}
