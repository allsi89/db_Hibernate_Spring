package cardealer.service;

import cardealer.domain.dtos.binding.CarDto;
import cardealer.domain.dtos.views.toyotacars.ExportToyotaCarsDto;
import cardealer.domain.entities.Car;

import java.util.List;

public interface CarService {
    void seedAll(List<CarDto> carDtos);

    List<Car> getAllCars();

    ExportToyotaCarsDto findAllToyotaCars();



}
