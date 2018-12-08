package mostwanted.domain.dtos.raceentrydtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "race-entries")
@XmlAccessorType(XmlAccessType.FIELD)
public class RaceEntryImportRootDto {
    @XmlElement(name = "race-entry")
    private RaceEntryDto[] raceEntryDtos;

    public RaceEntryDto[] getRaceEntryDtos() {
        return raceEntryDtos;
    }

    public void setRaceEntryDtos(RaceEntryDto[] raceEntryDtos) {
        this.raceEntryDtos = raceEntryDtos;
    }
}
