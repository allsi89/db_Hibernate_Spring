package app.ccb.services;

import app.ccb.domain.dtos.BranchImportRootDto;
import app.ccb.domain.entities.Branch;
import app.ccb.repositories.BranchRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static app.ccb.constants.Constant.BRANCHES_JSON_FILE_PATH;
import static app.ccb.constants.Constant.INCORRECT_DATA_MESSAGE;
import static app.ccb.constants.Constant.SUCCESSFULLY_IMPORTED_MESSAGE;

@Service
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;


    @Autowired
    public BranchServiceImpl(BranchRepository branchRepository, FileUtil fileUtil, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson) {
        this.branchRepository = branchRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public Boolean branchesAreImported() {
        return this.branchRepository.count() != 0;
    }

    @Override
    public String readBranchesJsonFile() throws IOException {
        return this.fileUtil.readFile(BRANCHES_JSON_FILE_PATH);
    }

    @Override
    public String importBranches(String branchesJson) {
        StringBuilder importResult = new StringBuilder();
        BranchImportRootDto[] branchImportRootDtos =
                this.gson.fromJson(branchesJson, BranchImportRootDto[].class);

        for (BranchImportRootDto branchImportRootDto : branchImportRootDtos) {
            if (!this.validationUtil.isValid(branchImportRootDto)){
                importResult.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            Branch branchEntity = this.modelMapper.map(branchImportRootDto, Branch.class);
            this.branchRepository.saveAndFlush(branchEntity);

            importResult.append(String.format(SUCCESSFULLY_IMPORTED_MESSAGE,
                    branchEntity.getClass().getSimpleName(), branchEntity.getName()))
                    .append(System.lineSeparator());
        }

        return importResult.toString().trim();
    }
}
