package cardealer.service;

import cardealer.domain.dtos.CarDto;
import cardealer.domain.dtos.CarPartsViewDto;
import cardealer.domain.dtos.CarViewDetailedDto;
import cardealer.domain.dtos.CarViewDto;
import cardealer.domain.entities.Car;

import java.util.List;

public interface CarService {
    void seedAll(CarDto[] carDtos);

    List<Car> getAllCars();

}
