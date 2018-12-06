package cardealer.service;

import cardealer.domain.dtos.SupplierDto;
import cardealer.domain.entities.Supplier;
import cardealer.repository.SupplierRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final ModelMapper modelMapper;
    private final SupplierRepository supplierRepository;


    @Autowired
    public SupplierServiceImpl(ModelMapper modelMapper, SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedAll(SupplierDto[] supplierDtos) {
        Supplier[] suppliers = this.modelMapper.map(supplierDtos, Supplier[].class);
        this.supplierRepository.saveAll(Arrays.asList(suppliers));
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        return this.supplierRepository.findAll();
    }


}
