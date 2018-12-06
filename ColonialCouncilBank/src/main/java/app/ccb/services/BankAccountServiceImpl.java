package app.ccb.services;

import app.ccb.domain.dtos.bankaccountdtos.BankAccountImportDto;
import app.ccb.domain.dtos.bankaccountdtos.BankAccountImportRootDto;
import app.ccb.domain.entities.BankAccount;
import app.ccb.domain.entities.Client;
import app.ccb.repositories.BankAccountRepository;
import app.ccb.repositories.ClientRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;

import static app.ccb.constants.Constant.BANK_ACCOUNTS_XML_FILE_PATH;
import static app.ccb.constants.Constant.INCORRECT_DATA_MESSAGE;
import static app.ccb.constants.Constant.SUCCESSFULLY_IMPORTED_MESSAGE;

@Service
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final ClientRepository clientRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, ClientRepository clientRepository, FileUtil fileUtil, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.clientRepository = clientRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean bankAccountsAreImported() {
        return this.bankAccountRepository.count() != 0;
    }

    @Override
    public String readBankAccountsXmlFile() throws IOException {
        return this.fileUtil.readFile(BANK_ACCOUNTS_XML_FILE_PATH);
    }

    @Override
    public String importBankAccounts() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(BankAccountImportRootDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        BankAccountImportRootDto bankAccountImportRootDto = (BankAccountImportRootDto) unmarshaller
                .unmarshal(new File(BANK_ACCOUNTS_XML_FILE_PATH));

        StringBuilder importResult = new StringBuilder();

        for (BankAccountImportDto bankAccountImportDto : bankAccountImportRootDto.getBankAccountImportDtos()) {
            if (!validationUtil.isValid(bankAccountImportDto)){
                importResult.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            Client clientEntity = this.clientRepository.findByFullName(bankAccountImportDto.getClient())
                    .orElse(null);
            if (clientEntity == null){
                importResult.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            BankAccount bankAccountEntity = this.modelMapper.map(bankAccountImportDto, BankAccount.class);
            bankAccountEntity.setClient(clientEntity);
            this.bankAccountRepository.saveAndFlush(bankAccountEntity);

            importResult.append(
                    String.format(
                            SUCCESSFULLY_IMPORTED_MESSAGE,
                            bankAccountEntity.getClass().getSimpleName(),
                            bankAccountEntity.getAccountNumber()))
                    .append(System.lineSeparator());
        }
        return importResult.toString().trim();
    }
}
