package bg.softuni.cardealerexercise.service.impls;

import bg.softuni.cardealerexercise.data.entities.Part;
import bg.softuni.cardealerexercise.data.entities.Supplier;
import bg.softuni.cardealerexercise.data.repositories.PartRepository;
import bg.softuni.cardealerexercise.data.repositories.SupplierRepository;
import bg.softuni.cardealerexercise.service.PartService;
import bg.softuni.cardealerexercise.service.dtos.imports.PartSeedDTO;
import bg.softuni.cardealerexercise.service.dtos.imports.PartSeedRootDTO;
import bg.softuni.cardealerexercise.util.ValidationUtil;
import bg.softuni.cardealerexercise.util.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class PartServiceImpl implements PartService {

    private static final String FILE_IMPORT_PATH = "src/main/resources/xml/imports/parts.xml";
    private final PartRepository partRepository;
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public PartServiceImpl(PartRepository partRepository, SupplierRepository supplierRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.partRepository = partRepository;
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seedPart() throws JAXBException {
        if (this.partRepository.count() == 0) {
            PartSeedRootDTO partSeedRootDTO = this.xmlParser.parse(PartSeedRootDTO.class, FILE_IMPORT_PATH);
            for (PartSeedDTO partSeedDTO : partSeedRootDTO.getPartSeedDTOList()) {
                if (!this.validationUtil.isValid(partSeedDTO)) {
                    System.out.println("Invalid data");

                    continue;
                }

                Part part = this.modelMapper.map(partSeedDTO, Part.class);
                part.setSupplier(getRandomSupplier());

                this.partRepository.saveAndFlush(part);
            }
        }

        System.out.println();
    }

    private Supplier getRandomSupplier() {
        return this.supplierRepository
                .findById(ThreadLocalRandom.current().nextLong(1, this.supplierRepository.count() + 1))
                .get();
    }
}
