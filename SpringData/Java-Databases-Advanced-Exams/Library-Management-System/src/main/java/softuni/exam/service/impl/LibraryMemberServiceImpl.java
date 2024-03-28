package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.jsons.LibraryMemberSeedDTO;
import softuni.exam.models.entity.LibraryMember;
import softuni.exam.repository.LibraryMemberRepository;
import softuni.exam.service.LibraryMemberService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class LibraryMemberServiceImpl implements LibraryMemberService {

    private static final String FILE_PATH = "src/main/resources/files/json/library-members.json";
    private final LibraryMemberRepository libraryMemberRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public LibraryMemberServiceImpl(LibraryMemberRepository libraryMemberRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.libraryMemberRepository = libraryMemberRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.libraryMemberRepository.count() > 0;
    }

    @Override
    public String readLibraryMembersFileContent() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));
    }

    @Override
    public String importLibraryMembers() throws IOException {
        StringBuilder sb = new StringBuilder();
        LibraryMemberSeedDTO[] libraryMemberSeedDTOS = this.gson.fromJson(readLibraryMembersFileContent(), LibraryMemberSeedDTO[].class);

        for (LibraryMemberSeedDTO libraryMemberSeedDTO : libraryMemberSeedDTOS) {
            Optional<LibraryMember> optional = this.libraryMemberRepository.findByPhoneNumber(libraryMemberSeedDTO.getPhoneNumber());
            if (!this.validationUtil.isValid(libraryMemberSeedDTO) || optional.isPresent()) {
                sb.append("Invalid library member\n");
                continue;
            }

            LibraryMember libraryMember = this.modelMapper.map(libraryMemberSeedDTO, LibraryMember.class);
            this.libraryMemberRepository.saveAndFlush(libraryMember);
            sb.append(String.format("Successfully imported library member %s - %s\n", libraryMember.getFirstName(), libraryMember.getLastName()));
        }

        return sb.toString();
    }
}
