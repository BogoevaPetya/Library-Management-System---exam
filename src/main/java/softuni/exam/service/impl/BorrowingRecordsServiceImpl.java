package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xmls.BorrowingDto;
import softuni.exam.models.dto.xmls.BorrowingRootDto;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        JAXBContext context = JAXBContext.newInstance(BorrowingRootDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        BorrowingRootDto borrowingRootDto = (BorrowingRootDto) unmarshaller.unmarshal(new File(FILE_PATH));

        for (BorrowingDto borrowingDto : borrowingRootDto.getBorrowingDtoList()) {

            String title = borrowingDto.getBook().getTitle();
            Optional<Book> optionalBook = this.bookRepository.findAllByTitle(title);

            long memberID = borrowingDto.getMember().getId();
            Optional<LibraryMember> optionalLibraryMember = this.libraryMemberRepository.findMemberById(memberID);

            if (!this.validationUtil.isValid(borrowingDto) || optionalBook.isEmpty() || optionalLibraryMember.isEmpty()) {
                sb.append("Invalid borrowing record\n");
                continue;
            }

            BorrowingRecord borrowingRecord = modelMapper.map(borrowingDto, BorrowingRecord.class);
            borrowingRecord.setBook(optionalBook.get());
            borrowingRecord.setLibraryMember(optionalLibraryMember.get());

            this.borrowingRecordRepository.saveAndFlush(borrowingRecord);
            sb.append(String.format("Successfully imported borrowing record %s - %s\n", borrowingRecord.getBook().getTitle(), borrowingRecord.getBorrowDate()));

        }
        return sb.toString();

    }

    @Override
    public String exportBorrowingRecords() {
        StringBuilder sb = new StringBuilder();

        List<BorrowingRecord> records = this.borrowingRecordRepository
                .findAllByBorrowDateBeforeOrderByBorrowDateDesc(LocalDate.parse("2021-09-01"))
                .stream()
                .filter(record -> record.getBook().getGenre().toString().equals("SCIENCE_FICTION"))
                .collect(Collectors.toList());

        records.forEach(record -> {
            sb.append(String.format("Book title: %s\n" +
                                    "*Book author: %s\n" +
                                    "**Date borrowed: %s\n" +
                                    "***Borrowed by: %s %s",
                            record.getBook().getTitle(),
                            record.getBook().getAuthor(),
                            record.getBorrowDate().toString(),
                            record.getLibraryMember().getFirstName(),
                            record.getLibraryMember().getLastName()))
                    .append(System.lineSeparator());
        });

        return sb.toString();
    }
}
