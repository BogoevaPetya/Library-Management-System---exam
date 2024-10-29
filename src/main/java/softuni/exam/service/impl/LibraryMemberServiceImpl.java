package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.jsons.LibraryMembersSeedDto;
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
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public LibraryMemberServiceImpl(LibraryMemberRepository libraryMemberRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.libraryMemberRepository = libraryMemberRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
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
        LibraryMembersSeedDto[] libraryMembersSeedDtos = this.gson.fromJson(readLibraryMembersFileContent(), LibraryMembersSeedDto[].class);

        for (LibraryMembersSeedDto libraryMembersSeedDto : libraryMembersSeedDtos) {
            Optional< LibraryMember> optionalLibraryMember = this.libraryMemberRepository.findAllByPhoneNumber(libraryMembersSeedDto.getPhoneNumber());

            if (!this.validationUtil.isValid(libraryMembersSeedDto) || optionalLibraryMember.isPresent()){
                sb.append(String.format("Invalid library member\n"));
                continue;
            }

            LibraryMember libraryMember = modelMapper.map(libraryMembersSeedDto, LibraryMember.class);
            libraryMemberRepository.saveAndFlush(libraryMember);

            sb.append(String.format("Successfully imported library member %s - %s\n", libraryMember.getFirstName(), libraryMember.getLastName()));
        }

        return sb.toString().trim();
    }
}
