package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.domain.dtos.RacerImportDto;
import mostwanted.domain.entities.Race;
import mostwanted.domain.entities.Racer;
import mostwanted.domain.entities.Town;
import mostwanted.repository.RacerRepository;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static mostwanted.common.Constants.*;

@Service
public class RacerServiceImpl implements RacerService {
    private final RacerRepository racerRepository;
    private final TownRepository townRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    @Autowired
    public RacerServiceImpl(RacerRepository racerRepository, TownRepository townRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.racerRepository = racerRepository;
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public Boolean racersAreImported() {
        return this.racerRepository.count() != 0;
    }

    @Override
    public String readRacersJsonFile() throws IOException {
        return this.fileUtil.readFile(RACERS_JSON_FILE_PATH);
    }

    @Override
    public String importRacers(String racersFileContent) {
        StringBuilder importResult = new StringBuilder();
        RacerImportDto[] racerImportDtos = this.gson.fromJson(racersFileContent, RacerImportDto[].class);
        for (RacerImportDto racerImportDto : racerImportDtos) {
            if (!this.validationUtil.isValid(racerImportDto)){
                importResult
                        .append(INCORRECT_DATA_MESSAGE)
                        .append(System.lineSeparator());
                continue;
            }

            Racer racerEntity = this.racerRepository
                    .findByName(racerImportDto.getName())
                    .orElse(null);

            if (racerEntity != null){
                importResult
                        .append(DUPLICATE_DATA_MESSAGE)
                        .append(System.lineSeparator());
                continue;
            }

            racerEntity = this.modelMapper.map(racerImportDto, Racer.class);
            Town townEntity = this.townRepository.findByName(racerImportDto.getHomeTown())
                    .orElse(null);
            racerEntity.setHomeTown(townEntity);
            this.racerRepository.saveAndFlush(racerEntity);

            importResult
                    .append(String.format(
                            SUCCESSFUL_IMPORT_MESSAGE,
                            racerEntity.getClass().getSimpleName(),
                            racerEntity.getName())
                    )
                    .append(System.lineSeparator());
        }
        return importResult.toString().trim();
    }

    @Override
    public String exportRacingCars() {
        List<Racer> racers = this.racerRepository.extractRacersWithRacingCars();
        StringBuilder exportResult = new StringBuilder();
        racers.forEach(r->{
            exportResult
                    .append(String.format("Name: %s", r.getName()))
                    .append(System.lineSeparator())
                    .append("Cars:")
                    .append(System.lineSeparator());
            r.getCars().forEach(c->{
                exportResult.append(String.format("\t%s %s %d",
                        c.getBrand(), c.getModel(), c.getYearOfProduction()
                        ))
                        .append(System.lineSeparator());
            });
        });
        return exportResult.toString().trim();
    }
}
