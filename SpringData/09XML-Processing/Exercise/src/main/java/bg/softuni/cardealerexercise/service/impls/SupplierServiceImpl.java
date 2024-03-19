package bg.softuni.cardealerexercise.service.impls;

import bg.softuni.cardealerexercise.data.entities.Supplier;
import bg.softuni.cardealerexercise.data.repositories.SupplierRepository;
import bg.softuni.cardealerexercise.service.SupplierService;
import bg.softuni.cardealerexercise.service.dtos.exports.SupplierLocalDTO;
import bg.softuni.cardealerexercise.service.dtos.exports.SupplierLocalRootDTO;
import bg.softuni.cardealerexercise.service.dtos.imports.SupplierSeedDTO;
import bg.softuni.cardealerexercise.service.dtos.imports.SupplierSeedRootDTO;
import bg.softuni.cardealerexercise.util.ValidationUtil;
import bg.softuni.cardealerexercise.util.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    private static final String FILE_IMPORT_PATH = "src/main/resources/xml/imports/suppliers.xml";
    private static final String FILE_EXPORT_LOCAL_PATH = "src/main/resources/xml/exports/local-supplier.xml";
    private final SupplierRepository supplierRepository;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public SupplierServiceImpl(SupplierRepository supplierRepository, XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.supplierRepository = supplierRepository;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedSupplier() throws JAXBException {
        if (this.supplierRepository.count() == 0) {
            SupplierSeedRootDTO supplierSeedRootDto = xmlParser.parse(SupplierSeedRootDTO.class, FILE_IMPORT_PATH);
            for (SupplierSeedDTO supplierSeedDTO : supplierSeedRootDto.getSupplierSeedDtoList()) {
                if (!this.validationUtil.isValid(supplierSeedDTO)) {
                    System.out.println("Invalid supplier data");

                    continue;
                }

                Supplier supplier = this.modelMapper.map(supplierSeedDTO, Supplier.class);
                this.supplierRepository.saveAndFlush(supplier);
            }
        }

        System.out.println();
    }

    @Override
    public void exportLocalSuppliers() throws JAXBException {
        List<SupplierLocalDTO> supplierLocalDTOS = this.supplierRepository.findAllByIsImporter(false)
                .stream()
                .map(s -> {
                    SupplierLocalDTO dto = this.modelMapper.map(s, SupplierLocalDTO.class);
                    dto.setPartsCount(s.getParts().size());
                    return dto;
                })
                .collect(Collectors.toList());

        SupplierLocalRootDTO supplierLocalRootDTO = new SupplierLocalRootDTO();
        supplierLocalRootDTO.setSupplierLocalDTOList(supplierLocalDTOS);

        this.xmlParser.exportToFile(SupplierLocalRootDTO.class, supplierLocalRootDTO, FILE_EXPORT_LOCAL_PATH);
    }
}
