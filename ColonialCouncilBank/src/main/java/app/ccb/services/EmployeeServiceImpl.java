package app.ccb.services;

import app.ccb.domain.dtos.EmployeeImportRootDto;
import app.ccb.domain.entities.Branch;
import app.ccb.domain.entities.Employee;
import app.ccb.repositories.BranchRepository;
import app.ccb.repositories.EmployeeRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static app.ccb.constants.Constant.EMPLOYEES_JSON_FILE_PATH;
import static app.ccb.constants.Constant.INCORRECT_DATA_MESSAGE;
import static app.ccb.constants.Constant.SUCCESSFULLY_IMPORTED_MESSAGE;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final BranchRepository branchRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, BranchRepository branchRepository, FileUtil fileUtil, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson) {
        this.employeeRepository = employeeRepository;
        this.branchRepository = branchRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count() != 0;
    }

    @Override
    public String readEmployeesJsonFile() throws IOException {
        return this.fileUtil.readFile(EMPLOYEES_JSON_FILE_PATH);
    }

    @Override
    public String importEmployees(String employees) {
        StringBuilder importResult = new StringBuilder();

        EmployeeImportRootDto[] employeeImportRootDtos =
                this.gson.fromJson(employees, EmployeeImportRootDto[].class);

        for (EmployeeImportRootDto employeeImportRootDto : employeeImportRootDtos) {
            if (!this.validationUtil.isValid(employeeImportRootDto)) {
                importResult.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            Branch branchEntity = this.branchRepository
                    .findByName(employeeImportRootDto.getBranchName())
                    .orElse(null);

            if (branchEntity == null) {
                importResult.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            Employee employeeEntity = this.modelMapper.map(employeeImportRootDto, Employee.class);
            employeeEntity.setFirstName(employeeImportRootDto.getFullName().split("\\s+")[0]);
            employeeEntity.setLastName(employeeImportRootDto.getFullName().split("\\s+")[1]);
            employeeEntity.setStartedOn(LocalDate.parse(employeeImportRootDto.getStartedOn()));
            employeeEntity.setBranch(branchEntity);
            this.employeeRepository.saveAndFlush(employeeEntity);

            importResult
                    .append(
                            String.format(SUCCESSFULLY_IMPORTED_MESSAGE,
                                    employeeEntity.getClass().getSimpleName(),
                                    employeeEntity.getFirstName() + " " + employeeEntity.getLastName()))
                    .append(System.lineSeparator());
        }

        return importResult.toString().trim();
    }

    @Override
    public String exportTopEmployees() {
        List<Employee> employees = this.employeeRepository.extractTopEmployees();
        StringBuilder exportResult = new StringBuilder();
        employees.forEach(e -> {
            exportResult
                    .append(String.format("Full Name: %s %s", e.getFirstName(), e.getLastName()))
                    .append(System.lineSeparator())
                    .append(String.format("Salary: %.2f", e.getSalary()))
                    .append(System.lineSeparator())
                    .append(String.format("Started On: %s", e.getStartedOn()))
                    .append(System.lineSeparator())
                    .append("Clients:")
                    .append(System.lineSeparator());
            e.getClients().forEach(c -> {
                exportResult
                        .append(String.format("\t%s", c.getFullName()))
                        .append(System.lineSeparator());
            });
        });
        return exportResult.toString().trim();
    }
}
