package cardealer.service;

import cardealer.domain.dtos.PartDto;
import cardealer.domain.entities.Part;

import java.util.List;

public interface PartService {
    void seedAll(PartDto[] partDtos);

    List<Part> getAllParts();
}
