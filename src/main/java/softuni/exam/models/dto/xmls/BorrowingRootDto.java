package softuni.exam.models.dto.xmls;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "borrowing_records")
@XmlAccessorType(XmlAccessType.FIELD)
public class BorrowingRootDto {
    @XmlElement(name = "borrowing_record")
    private List<BorrowingDto> borrowingDtoList;


    public BorrowingRootDto() {
    }

    public List<BorrowingDto> getBorrowingDtoList() {
        return borrowingDtoList;
    }

    public void setBorrowingDtoList(List<BorrowingDto> borrowingDtoList) {
        this.borrowingDtoList = borrowingDtoList;
    }
}
