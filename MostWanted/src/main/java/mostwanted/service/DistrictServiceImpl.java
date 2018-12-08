package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.domain.dtos.DistrictImportDto;
import mostwanted.domain.entities.District;
import mostwanted.domain.entities.Town;
import mostwanted.repository.DistrictRepository;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static mostwanted.common.Constants.*;

@Service
public class DistrictServiceImpl implements DistrictService {
    private final DistrictRepository districtRepository;
    private final TownRepository townRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    @Autowired
    public DistrictServiceImpl(DistrictRepository districtRepository, TownRepository townRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.districtRepository = districtRepository;
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public Boolean districtsAreImported() {
        return this.districtRepository.count() != 0;
    }

    @Override
    public String readDistrictsJsonFile() throws IOException {
        return this.fileUtil.readFile(DISTRICTS_JSON_FILE_PATH);
    }

    @Override
    public String importDistricts(String districtsFileContent) {
        StringBuilder importResult = new StringBuilder();
        DistrictImportDto[] districtImportDtos = this.gson
                .fromJson(districtsFileContent, DistrictImportDto[].class);

        for (DistrictImportDto districtImportDto : districtImportDtos) {
            if (!this.validationUtil.isValid(districtImportDto)) {
                importResult
                        .append(INCORRECT_DATA_MESSAGE)
                        .append(System.lineSeparator());
                continue;
            }

            District districtEntity = this.districtRepository
                    .findByName(districtImportDto.getName())
                    .orElse(null);

            if (districtEntity != null) {
                importResult
                        .append(DUPLICATE_DATA_MESSAGE)
                        .append(System.lineSeparator());
                continue;
            }

            districtEntity = this.modelMapper.map(districtImportDto, District.class);

            Town townEntity = this.townRepository.findByName(districtImportDto.getTownName())
                    .orElse(null);

            if (townEntity == null) {
                importResult
                        .append(INCORRECT_DATA_MESSAGE)
                        .append(System.lineSeparator());
                continue;
            }

            districtEntity.setTown(townEntity);
            this.districtRepository.saveAndFlush(districtEntity);

            importResult
                    .append(String.format(
                            SUCCESSFUL_IMPORT_MESSAGE,
                            districtEntity.getClass().getSimpleName(),
                            districtEntity.getName())
                    )
                    .append(System.lineSeparator());

        }
        return importResult.toString().trim();
    }
}
