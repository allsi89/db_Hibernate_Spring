package cardealer.service;

import cardealer.domain.dtos.binding.CustomerDto;
import cardealer.domain.dtos.views.orderedcustomers.ExportOrderedCustomersDto;
import cardealer.domain.entities.Customer;

import java.util.List;

public interface CustomerService {

    void seedAll(List<CustomerDto> customerDtos);

    List<Customer> getAllCustomers();

    ExportOrderedCustomersDto getOrderedCustomers();


}
