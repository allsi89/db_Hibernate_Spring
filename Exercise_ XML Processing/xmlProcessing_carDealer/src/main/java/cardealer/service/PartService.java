package cardealer.service;

import cardealer.domain.dtos.binding.PartDto;
import cardealer.domain.entities.Part;

import java.util.List;

public interface PartService {
    void seedAll(List<PartDto> partDtos);

    List<Part> getAllParts();


}
