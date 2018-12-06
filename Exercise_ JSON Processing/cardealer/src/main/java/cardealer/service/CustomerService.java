package cardealer.service;

import cardealer.domain.dtos.CustomerByBirthdayDto;
import cardealer.domain.dtos.CustomerDto;
import cardealer.domain.dtos.CustomerPurchasesViewDto;
import cardealer.domain.entities.Customer;

import java.util.List;

public interface CustomerService {

    void seedAll(CustomerDto[] customerDtos);

    List<Customer> getAllCustomers();



}
