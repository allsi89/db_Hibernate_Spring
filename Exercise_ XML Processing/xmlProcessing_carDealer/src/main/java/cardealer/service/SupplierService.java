package cardealer.service;

import cardealer.domain.dtos.binding.SupplierDto;
import cardealer.domain.dtos.views.localsuppliers.ExportLocalSuppliersDto;
import cardealer.domain.entities.Supplier;

import java.util.List;

public interface SupplierService {
    void seedAll(List<SupplierDto> supplierDtos);

    List<Supplier> getAllSuppliers();

    ExportLocalSuppliersDto getAllLocals();
}
