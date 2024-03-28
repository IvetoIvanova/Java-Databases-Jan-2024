package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xmls.BorrowingRecordRootDTO;
import softuni.exam.models.dto.xmls.BorrowingRecordSeedDTO;
import softuni.exam.models.entity.Book;
import softuni.exam.models.entity.BorrowingRecord;
import softuni.exam.models.entity.LibraryMember;
import softuni.exam.repository.BookRepository;
import softuni.exam.repository.BorrowingRecordRepository;
import softuni.exam.repository.LibraryMemberRepository;
import softuni.exam.service.BorrowingRecordsService;
import softuni.exam.util.ValidationUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class BorrowingRecordsServiceImpl implements BorrowingRecordsService {

    private static final String FILE_PATH = "src/main/resources/files/xml/borrowing-records.xml";
    private final BorrowingRecordRepository borrowingRecordRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final BookRepository bookRepository;
    private final LibraryMemberRepository libraryMemberRepository;

    public BorrowingRecordsServiceImpl(BorrowingRecordRepository borrowingRecordRepository, ModelMapper modelMapper, ValidationUtil validationUtil, BookRepository bookRepository, LibraryMemberRepository libraryMemberRepository) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.bookRepository = bookRepository;
        this.libraryMemberRepository = libraryMemberRepository;
    }

    @Override
    public boolean areImported() {
        return this.borrowingRecordRepository.count() > 0;
    }

    @Override
    public String readBorrowingRecordsFromFile() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));
    }

    @Override
    public String importBorrowingRecords() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext jaxbContext = JAXBContext.newInstance(BorrowingRecordRootDTO.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        BorrowingRecordRootDTO borrowingRecordRootDTO = (BorrowingRecordRootDTO) unmarshaller.unmarshal(new File(FILE_PATH));

        for (BorrowingRecordSeedDTO borrowingRecordSeedDTO : borrowingRecordRootDTO.getBorrowingRecordSeedDTOs()) {
            Optional<Book> bookOptional = this.bookRepository.findByTitle(borrowingRecordSeedDTO.getBook().getTitle());
            Optional<LibraryMember> libraryMemberOptional = this.libraryMemberRepository.findById(borrowingRecordSeedDTO.getMember().getId());

            if (!this.validationUtil.isValid(borrowingRecordSeedDTO) || libraryMemberOptional.isEmpty() || bookOptional.isEmpty()) {
                sb.append("Invalid borrowing record\n");
                continue;
            }

            BorrowingRecord borrowingRecord = this.modelMapper.map(borrowingRecordSeedDTO, BorrowingRecord.class);
            borrowingRecord.setBook(bookOptional.get());
            borrowingRecord.setMember(libraryMemberOptional.get());
            this.borrowingRecordRepository.saveAndFlush(borrowingRecord);

            sb.append(String.format("Successfully imported borrowing record %s - %s\n",
                    borrowingRecord.getBook().getTitle(), borrowingRecord.getBorrowDate()));
        }

        return sb.toString();
    }

    @Override
    public String exportBorrowingRecords() {
        return null;
    }
}
