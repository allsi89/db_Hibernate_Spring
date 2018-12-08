package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.domain.dtos.CarImportDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.Racer;
import mostwanted.repository.CarRepository;
import mostwanted.repository.RacerRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static mostwanted.common.Constants.*;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final RacerRepository racerRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, RacerRepository racerRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.carRepository = carRepository;
        this.racerRepository = racerRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public Boolean carsAreImported() {
        return this.carRepository.count() != 0;
    }

    @Override
    public String readCarsJsonFile() throws IOException {
        return this.fileUtil.readFile(CARS_JSON_FILE_PATH);
    }

    @Override
    public String importCars(String carsFileContent) {
        StringBuilder importResult = new StringBuilder();
        CarImportDto[] carImportDtos = this.gson
                .fromJson(carsFileContent, CarImportDto[].class);
        for (CarImportDto carImportDto : carImportDtos) {
            if (!this.validationUtil.isValid(carImportDto)) {
                importResult
                        .append(INCORRECT_DATA_MESSAGE)
                        .append(System.lineSeparator());
                continue;
            }
            Car carEntity = this.modelMapper.map(carImportDto, Car.class);

            Racer racer = this.racerRepository.findByName(carImportDto.getRacerName())
                    .orElse(null);

            if (racer != null){
                carEntity.setRacer(racer);
                racer.getCars().add(carEntity);
            }
            this.carRepository.saveAndFlush(carEntity);

            importResult
                    .append(String.format(
                            SUCCESSFUL_IMPORT_MESSAGE,
                            carEntity.getClass().getSimpleName(),
                            String.format("%s %s @ %d",
                                    carEntity.getBrand(),
                                    carEntity.getModel(),
                                    carEntity.getYearOfProduction()
                            ))
                    )
                    .append(System.lineSeparator());
        }
        return importResult.toString().trim();
    }
}
