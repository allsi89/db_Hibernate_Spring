package cardealer.service;

import cardealer.domain.dtos.binding.CustomerDto;
import cardealer.domain.dtos.views.orderedcustomers.CustomerByBirthdayDto;
import cardealer.domain.dtos.views.orderedcustomers.ExportOrderedCustomersDto;
import cardealer.domain.entities.Customer;
import cardealer.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(ModelMapper modelMapper, CustomerRepository customerRepository) {
        this.modelMapper = modelMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public void seedAll(List<CustomerDto> customerDtos) {
        Customer[] customers = this.modelMapper.map(customerDtos, Customer[].class);
        this.customerRepository.saveAll(Arrays.asList(customers));
    }

    @Override
    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    @Override
    public ExportOrderedCustomersDto getOrderedCustomers() {

        List<CustomerByBirthdayDto> customers =
                this.customerRepository.getAllSortedByBirthday()
                        .stream()
                        .map(c -> modelMapper.map(c, CustomerByBirthdayDto.class))
                        .collect(Collectors.toList());

        ExportOrderedCustomersDto export = new ExportOrderedCustomersDto();
        export.setCustomers(customers);
        return export;
    }


}
