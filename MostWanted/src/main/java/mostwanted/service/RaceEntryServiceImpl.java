package mostwanted.service;

import mostwanted.domain.dtos.raceentrydtos.RaceEntryDto;
import mostwanted.domain.dtos.raceentrydtos.RaceEntryImportRootDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.RaceEntry;
import mostwanted.domain.entities.Racer;
import mostwanted.repository.CarRepository;
import mostwanted.repository.RaceEntryRepository;
import mostwanted.repository.RacerRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import mostwanted.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

import static mostwanted.common.Constants.RACE_ENTRIES_XML_FILE_PATH;
import static mostwanted.common.Constants.SUCCESSFUL_IMPORT_MESSAGE;

@Service
public class RaceEntryServiceImpl implements RaceEntryService {
    private final RaceEntryRepository raceEntryRepository;
    private final CarRepository carRepository;
    private final RacerRepository racerRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    @Autowired
    public RaceEntryServiceImpl(RaceEntryRepository raceEntryRepository, CarRepository carRepository, RacerRepository racerRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.raceEntryRepository = raceEntryRepository;
        this.carRepository = carRepository;
        this.racerRepository = racerRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public Boolean raceEntriesAreImported() {
        return this.raceEntryRepository.count() != 0;
    }

    @Override
    public String readRaceEntriesXmlFile() throws IOException {
        return this.fileUtil.readFile(RACE_ENTRIES_XML_FILE_PATH);
    }

    @Override
    public String importRaceEntries() throws JAXBException, FileNotFoundException {
        StringBuilder importResult = new StringBuilder();
        RaceEntryImportRootDto raceEntryImportRootDto = this.xmlParser
                .parseXml(RaceEntryImportRootDto.class, RACE_ENTRIES_XML_FILE_PATH);
        int index = 0;
        for (RaceEntryDto raceEntryDto : raceEntryImportRootDto.getRaceEntryDtos()) {
            RaceEntry raceEntryEntity = this.modelMapper.map(raceEntryDto, RaceEntry.class);
            Car carEntity = this.carRepository.findById(raceEntryDto.getCarId())
                    .orElse(null);
            raceEntryEntity.setCar(carEntity);
            Racer racerEntity = this.racerRepository.findByName(raceEntryDto.getRacer())
                    .orElse(null);
            raceEntryEntity.setRacer(racerEntity);
            this.raceEntryRepository.saveAndFlush(raceEntryEntity);
            index++;
            importResult
                    .append(String.format(
                            SUCCESSFUL_IMPORT_MESSAGE,
                            raceEntryEntity.getClass().getSimpleName(),
                            index)
                    )
                    .append(System.lineSeparator());
        }
        return importResult.toString().trim();
    }
}
