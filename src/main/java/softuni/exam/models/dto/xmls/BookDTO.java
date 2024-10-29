package softuni.exam.models.dto.xmls;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class BookDTO {
    @XmlElement
    private String title;






    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
