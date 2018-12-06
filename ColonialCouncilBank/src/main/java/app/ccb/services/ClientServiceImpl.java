package app.ccb.services;

import app.ccb.domain.dtos.ClientImportRootDto;
import app.ccb.domain.entities.Client;
import app.ccb.domain.entities.Employee;
import app.ccb.repositories.ClientRepository;
import app.ccb.repositories.EmployeeRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static app.ccb.constants.Constant.CLIENTS_JSON_FILE_PATH;
import static app.ccb.constants.Constant.INCORRECT_DATA_MESSAGE;
import static app.ccb.constants.Constant.SUCCESSFULLY_IMPORTED_MESSAGE;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, EmployeeRepository employeeRepository, FileUtil fileUtil, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson) {
        this.clientRepository = clientRepository;
        this.employeeRepository = employeeRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public Boolean clientsAreImported() {
        return this.clientRepository.count() != 0;
    }

    @Override
    public String readClientsJsonFile() throws IOException {
        return this.fileUtil.readFile(CLIENTS_JSON_FILE_PATH);
    }

    @Override
    public String importClients(String clients) {
        StringBuilder importResult = new StringBuilder();

        ClientImportRootDto[] clientImportRootDtos =
                this.gson.fromJson(clients, ClientImportRootDto[].class);

        for (ClientImportRootDto clientImportRootDto : clientImportRootDtos) {
            if (!this.validationUtil.isValid(clientImportRootDto)){
                importResult.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            Employee employeeEntity = this.employeeRepository
                    .findByFullName(clientImportRootDto.getEmployeeName())
                    .orElse(null);

            if (employeeEntity == null) {
                importResult.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            Client clientEntity = this.clientRepository.findByFullName(String.format("%s %s",
                    clientImportRootDto.getFirstName(),
                    clientImportRootDto.getLastName()))
                    .orElse(null);

            if (clientEntity != null){
                importResult.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            clientEntity = this.modelMapper.map(clientImportRootDto, Client.class);
            clientEntity.setFullName(String.format("%s %s",
                    clientImportRootDto.getFirstName(),
                    clientImportRootDto.getLastName()));

            if (!clientEntity.getEmployees().contains(employeeEntity)){
                clientEntity.getEmployees().add(employeeEntity);
            }

            this.clientRepository.saveAndFlush(clientEntity);

            importResult.append(
                    String.format(
                            SUCCESSFULLY_IMPORTED_MESSAGE,
                            clientEntity.getClass().getSimpleName(),
                            clientEntity.getFullName()))
            .append(System.lineSeparator());
        }
        return importResult.toString().trim();
    }

    @Override
    public String exportFamilyGuy() {
        Client client = this.clientRepository.extractTopClient()
                .stream().findFirst()
                .orElse(null);

        StringBuilder exportResult = new StringBuilder();

        exportResult
                .append(String.format("Full Name: %s", client.getFullName()))
                .append(System.lineSeparator())
                .append(String.format("Age: %d", client.getAge()))
                .append(System.lineSeparator())
                .append(String.format("Bank Account: %s", client.getBankAccount().getAccountNumber()))
                .append(System.lineSeparator());
        client.getBankAccount().getCards().forEach(c->{
            exportResult.append(String.format("\tCard Number: %s", c.getCardNumber()))
                    .append(System.lineSeparator())
                    .append(String.format("\tCard Status: %s", c.getCardStatus()))
                    .append(System.lineSeparator());

        });


        return exportResult.toString().trim();
    }
}
