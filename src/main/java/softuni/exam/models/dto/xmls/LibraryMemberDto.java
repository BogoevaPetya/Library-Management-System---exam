package softuni.exam.models.dto.xmls;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class LibraryMemberDto {
    @XmlElement
    private long id;




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
