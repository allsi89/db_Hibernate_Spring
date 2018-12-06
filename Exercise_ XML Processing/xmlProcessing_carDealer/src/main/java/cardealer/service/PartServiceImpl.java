package cardealer.service;

import cardealer.domain.dtos.binding.PartDto;
import cardealer.domain.entities.Part;
import cardealer.domain.entities.Supplier;
import cardealer.repository.PartRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class PartServiceImpl implements PartService {
    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final SupplierService supplierService;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, ModelMapper modelMapper, SupplierService supplierService) {
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.supplierService = supplierService;
    }

    @Override
    public void seedAll(List<PartDto> partDtos) {

        Part[] parts = this.modelMapper.map(partDtos, Part[].class);

        Random random = new Random();
        List<Supplier> allSuppliers = this.supplierService.getAllSuppliers();
        for (Part part : parts) {
            part.setSupplier(allSuppliers.get(random.nextInt(allSuppliers.size())));
            this.partRepository.saveAndFlush(part);
        }
    }

    @Override
    public List<Part> getAllParts() {
        return this.partRepository.findAll();
    }

}
