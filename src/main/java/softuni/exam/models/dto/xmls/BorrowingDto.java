package softuni.exam.models.dto.xmls;

import softuni.exam.util.LocalDateAdapter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlRootElement(name = "borrowing_record")
@XmlAccessorType(XmlAccessType.FIELD)
public class BorrowingDto {
    @XmlElement(name = "borrow_date")
    @NotNull
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate borrowDate;

    @XmlElement(name = "return_date")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @NotNull
    private LocalDate returnDate;

    @XmlElement
    private BookDTO book;

    @XmlElement
    private LibraryMemberDto member;

    @XmlElement
    @Size(min = 3, max = 100)
    private String remarks;


    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public BookDTO getBook() {
        return book;
    }

    public void setBook(BookDTO book) {
        this.book = book;
    }

    public LibraryMemberDto getMember() {
        return member;
    }

    public void setMember(LibraryMemberDto member) {
        this.member = member;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
