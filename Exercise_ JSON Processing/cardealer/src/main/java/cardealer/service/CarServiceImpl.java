package cardealer.service;

import cardealer.domain.dtos.*;
import cardealer.domain.entities.Car;
import cardealer.domain.entities.Part;
import cardealer.repository.CarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final PartService partService;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, PartService partService) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.partService = partService;
    }

    @Override
    public void seedAll(CarDto[] carDtos) {

        Car[] cars = this.modelMapper.map(carDtos, Car[].class);
        List<Part> allParts = this.partService.getAllParts();
        Random random = new Random();
        for (Car car : cars) {
            while(car.getParts().size() >= 20) {
                car.getParts().add(allParts.get(random.nextInt(allParts.size())));
                if (random.nextInt(5) == 0 && car.getParts().size() >= 10) {
                    break;
                }
            }
        }
        this.carRepository.saveAll(Arrays.asList(cars));
    }

    @Override
    public List<Car> getAllCars() {
        return this.carRepository.findAll();
    }


}
