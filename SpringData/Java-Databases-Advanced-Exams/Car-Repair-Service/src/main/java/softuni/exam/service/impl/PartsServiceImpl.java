package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.jsons.PartSeedDTO;
import softuni.exam.models.entity.Part;
import softuni.exam.repository.PartsRepository;
import softuni.exam.service.PartsService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class PartsServiceImpl implements PartsService {

    private static String PARTS_FILE_PATH = "src/main/resources/files/json/parts.json";
    private final PartsRepository partRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public PartsServiceImpl(PartsRepository partRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.partRepository = partRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.partRepository.count() > 0;
    }

    @Override
    public String readPartsFileContent() throws IOException {
        return new String(Files.readAllBytes(Path.of(PARTS_FILE_PATH)));
    }

    @Override
    public String importParts() throws IOException {
        StringBuilder sb = new StringBuilder();
        PartSeedDTO[] partSeedDTOS = this.gson.fromJson(readPartsFileContent(), PartSeedDTO[].class);

        for (PartSeedDTO partSeedDTO : partSeedDTOS) {
            Optional<Part> optional = this.partRepository.findByPartName(partSeedDTO.getPartName());
            if (!this.validationUtil.isValid(partSeedDTO) || optional.isPresent()) {
                sb.append("Invalid part\n");
                continue;
            }

            Part part = this.modelMapper.map(partSeedDTO, Part.class);
            this.partRepository.saveAndFlush(part);
            sb.append(String.format("Successfully imported part %s - %s\n", part.getPartName(), part.getPrice()));
        }

        return sb.toString();
    }
}
