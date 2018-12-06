package cardealer.service;

import cardealer.domain.dtos.binding.CarDto;
import cardealer.domain.dtos.views.toyotacars.CarViewDetailedDto;
import cardealer.domain.dtos.views.toyotacars.ExportToyotaCarsDto;
import cardealer.domain.entities.Car;
import cardealer.domain.entities.Part;
import cardealer.repository.CarRepository;
import cardealer.repository.PartRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    private static final String TOYOTA_MAKE = "Toyota";
    private final CarRepository carRepository;
    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final PartService partService;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, PartRepository partRepository, ModelMapper modelMapper, PartService partService) {
        this.carRepository = carRepository;
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.partService = partService;
    }

    @Override
    public void seedAll(List<CarDto> carDtos) {

        List<Car> cars = carDtos
                .stream()
                .map(carDto -> this.modelMapper.map(carDto, Car.class))
                .collect(Collectors.toList());
        List<Part> allParts = this.partService.getAllParts();
        Random random = new Random();
        for (Car car : cars) {
            List<Part> parts = this.getRandomParts(allParts);

            car.setParts(parts);
            this.carRepository.saveAndFlush(car);
        }
    }

    private List<Part> getRandomParts(List<Part> allParts) {
        Random random = new Random();
        List<Part> parts = new ArrayList<>();
        int count = random.nextInt((int) (allParts.size()/13));
        for (int i = 0; i < count ; i++) {
            parts.add(allParts.get(random.nextInt((int) (this.partRepository.count() - 1)) + 1));
        }
        return parts;
    }

    @Override
    public List<Car> getAllCars() {
        return this.carRepository.findAll();
    }

    @Override
    public ExportToyotaCarsDto findAllToyotaCars() {
        List<CarViewDetailedDto> toyotaCars =
                this.carRepository
                        .findAllByMakeOrderByModelAscTravelledDistanceDesc(TOYOTA_MAKE)
                        .stream()
                        .map(c -> this.modelMapper.map(c, CarViewDetailedDto.class))
                        .collect(Collectors.toList());
        ExportToyotaCarsDto export = new ExportToyotaCarsDto<>();
        export.setCars(toyotaCars);
        return export;
    }


}
