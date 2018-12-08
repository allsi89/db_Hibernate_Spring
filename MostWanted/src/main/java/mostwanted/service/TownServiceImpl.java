package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.domain.dtos.TownImportDto;
import mostwanted.domain.entities.Town;
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
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public Boolean townsAreImported() {
        return this.townRepository.count() != 0;
    }

    @Override
    public String readTownsJsonFile() throws IOException {
        return this.fileUtil.readFile(TOWNS_JSON_FILE_PATH);
    }

    @Override
    public String importTowns(String townsFileContent) {
        StringBuilder importResult = new StringBuilder();
        TownImportDto[] townImportDtos = this.gson
                .fromJson(townsFileContent, TownImportDto[].class);
        for (TownImportDto townImportDto : townImportDtos) {
            if (!this.validationUtil.isValid(townImportDto)) {
                importResult
                        .append(INCORRECT_DATA_MESSAGE)
                        .append(System.lineSeparator());
                continue;
            }
            Town townEntity = this.townRepository
                    .findByName(townImportDto.getName()).orElse(null);

            if (townEntity != null) {
                importResult
                        .append(DUPLICATE_DATA_MESSAGE)
                        .append(System.lineSeparator());
                continue;
            }

            townEntity = this.modelMapper.map(townImportDto, Town.class);
            this.townRepository.saveAndFlush(townEntity);

            importResult
                    .append(String.format(
                            SUCCESSFUL_IMPORT_MESSAGE,
                            townEntity.getClass().getSimpleName(),
                            townEntity.getName())
                    )
                    .append(System.lineSeparator());
        }
        return importResult.toString().trim();
    }

    @Override
    public String exportRacingTowns() {
        List<Town> racingTowns = this.townRepository.extractRacingTowns();
        StringBuilder exportResult = new StringBuilder();
        racingTowns.forEach(t->{
            exportResult
                    .append(String.format("Name: %s", t.getName()))
                    .append(System.lineSeparator())
                    .append(String.format("Racers: %d", t.getRacers().size()))
                    .append(System.lineSeparator())
                    .append(System.lineSeparator());
        });
        return exportResult.toString().trim();
    }
}
