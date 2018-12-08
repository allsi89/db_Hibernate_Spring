package mostwanted.domain.dtos.racedtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "races")
@XmlAccessorType(XmlAccessType.FIELD)
public class RaceImportRootDto {
    @XmlElement(name = "race")
    private RaceDto[] races;

    public RaceDto[] getRaces() {
        return races;
    }

    public void setRaces(RaceDto[] races) {
        this.races = races;
    }
}
