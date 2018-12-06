package app.ccb.services;

import app.ccb.domain.dtos.carddtos.CardImportDto;
import app.ccb.domain.dtos.carddtos.CardImportRootDto;
import app.ccb.domain.entities.BankAccount;
import app.ccb.domain.entities.Card;
import app.ccb.repositories.BankAccountRepository;
import app.ccb.repositories.CardRepository;
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

import static app.ccb.constants.Constant.CARDS_XML_FILE_PATH;
import static app.ccb.constants.Constant.INCORRECT_DATA_MESSAGE;
import static app.ccb.constants.Constant.SUCCESSFULLY_IMPORTED_MESSAGE;

@Service
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final BankAccountRepository bankAccountRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository, BankAccountRepository bankAccountRepository, FileUtil fileUtil, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.cardRepository = cardRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean cardsAreImported() {
        return this.cardRepository.count() != 0;
    }

    @Override
    public String readCardsXmlFile() throws IOException {
        return this.fileUtil.readFile(CARDS_XML_FILE_PATH);
    }

    @Override
    public String importCards() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(CardImportRootDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        CardImportRootDto cardImportRootDto = (CardImportRootDto) unmarshaller
                .unmarshal(new File(CARDS_XML_FILE_PATH));

        StringBuilder importResult = new StringBuilder();

        for (CardImportDto cardImportDto : cardImportRootDto.getCardImportDtos()) {

            if (!validationUtil.isValid(cardImportDto)){
                importResult.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            BankAccount bankAccountEntity = this.bankAccountRepository
                    .findByAccountNumber(cardImportDto.getAccountNumber())
                    .orElse(null);

            if (bankAccountEntity == null){
                importResult.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            Card cardEntity = this.modelMapper.map(cardImportDto, Card.class);
            cardEntity.setBankAccount(bankAccountEntity);
            this.cardRepository.saveAndFlush(cardEntity);

            importResult.append(
                    String.format(
                            SUCCESSFULLY_IMPORTED_MESSAGE,
                            cardEntity.getClass().getSimpleName(),
                            cardEntity.getCardNumber()))
                    .append(System.lineSeparator());
        }
        return importResult.toString().trim();
    }
}
