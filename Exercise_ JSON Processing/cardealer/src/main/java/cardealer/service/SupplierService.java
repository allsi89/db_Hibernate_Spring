package cardealer.service;

import cardealer.domain.dtos.SupplierDto;
import cardealer.domain.dtos.SupplierViewDto;
import cardealer.domain.entities.Supplier;

import java.util.List;

public interface SupplierService {
    void seedAll(SupplierDto[] supplierDtos);

    List<Supplier> getAllSuppliers();

}
