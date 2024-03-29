package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.jsons.MechanicSeedDTO;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.repository.MechanicsRepository;
import softuni.exam.service.MechanicsService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class MechanicsServiceImpl implements MechanicsService {

    private static String MECHANICS_FILE_PATH = "src/main/resources/files/json/mechanics.json";
    private final MechanicsRepository mechanicRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public MechanicsServiceImpl(MechanicsRepository mechanicRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.mechanicRepository = mechanicRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.mechanicRepository.count() > 0;
    }

    @Override
    public String readMechanicsFromFile() throws IOException {
        return new String(Files.readAllBytes(Path.of(MECHANICS_FILE_PATH)));
    }

    @Override
    public String importMechanics() throws IOException {
        StringBuilder sb = new StringBuilder();
        MechanicSeedDTO[] mechanicSeedDTOS = this.gson.fromJson(readMechanicsFromFile(), MechanicSeedDTO[].class);

        for (MechanicSeedDTO mechanicSeedDTO : mechanicSeedDTOS) {
            Optional<Mechanic> optional = this.mechanicRepository.findByEmail(mechanicSeedDTO.getEmail());
            if (!this.validationUtil.isValid(mechanicSeedDTO) || optional.isPresent()) {
                sb.append("Invalid mechanic\n");
                continue;
            }

            Mechanic mechanic = this.modelMapper.map(mechanicSeedDTO, Mechanic.class);
            this.mechanicRepository.saveAndFlush(mechanic);
            sb.append(String.format("Successfully imported mechanic %s %s\n", mechanic.getFirstName(), mechanic.getLastName()));
        }

        return sb.toString();
    }
}
