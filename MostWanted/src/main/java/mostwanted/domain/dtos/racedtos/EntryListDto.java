package mostwanted.domain.dtos.racedtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "entries")
@XmlAccessorType(XmlAccessType.FIELD)
public class EntryListDto {
    @XmlElement(name = "entry")
    private EntryDto[] entries;

    public EntryDto[] getEntries() {
        return entries;
    }

    public void setEntries(EntryDto[] entries) {
        this.entries = entries;
    }
}
