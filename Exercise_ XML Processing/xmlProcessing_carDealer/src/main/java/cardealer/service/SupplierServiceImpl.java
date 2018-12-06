package cardealer.service;

import cardealer.domain.dtos.binding.SupplierDto;
import cardealer.domain.dtos.views.localsuppliers.ExportLocalSuppliersDto;
import cardealer.domain.dtos.views.localsuppliers.SupplierViewDto;
import cardealer.domain.entities.Supplier;
import cardealer.repository.SupplierRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    public void seedAll(List<SupplierDto> supplierDtos) {
        Supplier[] suppliers = this.modelMapper.map(supplierDtos, Supplier[].class);
        this.supplierRepository.saveAll(Arrays.asList(suppliers));
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        return this.supplierRepository.findAll();
    }


    private Integer getCountOfPars(int id) {
        return null;
    }

    @Override
    public ExportLocalSuppliersDto getAllLocals() {
        List<Supplier> suppliers = this.supplierRepository.findAll();
        List<SupplierViewDto> locals = suppliers.stream()
                .filter(s-> !s.getImporter())
                .map(s->this.modelMapper.map(s, SupplierViewDto.class))
                .collect(Collectors.toList());
        locals.forEach(l->{
            l.setPartsCount(this.supplierRepository.getCountOfParts(l.getId()));
        });
        ExportLocalSuppliersDto export = new ExportLocalSuppliersDto();
        export.setLocals(locals);
        return export;
    }


}
