package mostwanted.service;

import mostwanted.domain.dtos.racedtos.EntryDto;
import mostwanted.domain.dtos.racedtos.RaceDto;
import mostwanted.domain.dtos.racedtos.RaceImportRootDto;
import mostwanted.domain.entities.District;
import mostwanted.domain.entities.Race;
import mostwanted.domain.entities.RaceEntry;
import mostwanted.repository.DistrictRepository;
import mostwanted.repository.RaceEntryRepository;
import mostwanted.repository.RaceRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import mostwanted.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static mostwanted.common.Constants.INCORRECT_DATA_MESSAGE;
import static mostwanted.common.Constants.RACES_XML_FILE_PATH;
import static mostwanted.common.Constants.SUCCESSFUL_IMPORT_MESSAGE;

@Service
public class RaceServiceImpl implements RaceService {
    private final RaceRepository raceRepository;
    private final DistrictRepository districtRepository;
    private final RaceEntryRepository raceEntryRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    @Autowired
    public RaceServiceImpl(RaceRepository raceRepository, DistrictRepository districtRepository, RaceEntryRepository raceEntryRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.raceRepository = raceRepository;
        this.districtRepository = districtRepository;
        this.raceEntryRepository = raceEntryRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public Boolean racesAreImported() {
        return this.raceRepository.count() != 0;
    }

    @Override
    public String readRacesXmlFile() throws IOException {
        return this.fileUtil.readFile(RACES_XML_FILE_PATH);
    }

    @Override
    public String importRaces() throws JAXBException, FileNotFoundException {
        StringBuilder importResult = new StringBuilder();
        RaceImportRootDto raceImportRootDto = this.xmlParser
                .parseXml(RaceImportRootDto.class, RACES_XML_FILE_PATH);
        int index = 0;
        for (RaceDto raceDto : raceImportRootDto.getRaces()) {
            Race raceEntity = this.modelMapper.map(raceDto, Race.class);
            District districtEntity = this.districtRepository
                    .findByName(raceDto.getDistrictName())
                    .orElse(null);

            if (districtEntity == null){
                importResult
                        .append(INCORRECT_DATA_MESSAGE)
                        .append(System.lineSeparator());
                continue;
            }

            raceEntity.setDistrict(districtEntity);

            List<RaceEntry> raceEntries = new ArrayList<>();
            for (EntryDto entry : raceDto.getEntries().getEntries()) {
                this.raceEntryRepository
                        .findById(entry.getId()).ifPresent(raceEntries::add);
            }

            raceEntity.setRaceEntries(raceEntries);
            this.raceRepository.saveAndFlush(raceEntity);

            index++;
            importResult
                    .append(String.format(
                            SUCCESSFUL_IMPORT_MESSAGE,
                            raceEntity.getClass().getSimpleName(),
                            index)
                    )
                    .append(System.lineSeparator());
        }

        return importResult.toString().trim();
    }
}
