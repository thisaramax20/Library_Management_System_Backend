package edu.icet.crm.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.crm.dto.IssueBooks;
import edu.icet.crm.entity.Book;
import edu.icet.crm.entity.IssueBookId;
import edu.icet.crm.entity.User;
import edu.icet.crm.repository.BookRepository;
import edu.icet.crm.repository.IssueBooksRepository;
import edu.icet.crm.repository.UserRepository;
import edu.icet.crm.service.IssueBooksService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IssueBooksServiceImpl implements IssueBooksService {
    private final IssueBooksRepository issueBooksRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ObjectMapper mapper;
    private final String word = "ongoing";
    private final String word2 = "overdue";

    @Override
    @Transactional
    public void save(IssueBooks books) {
        edu.icet.crm.entity.IssueBooks issueBooks = mapper.convertValue(books, edu.icet.crm.entity.IssueBooks.class);
        User byUsername = userRepository.findByUsername(books.getUserId());
        Book byBookCode = bookRepository.findByBookCode(books.getBookId());
        if (byBookCode!=null && byUsername!=null){
            IssueBookId issueBookId = new IssueBookId(byUsername.getId(), byBookCode.getId(), LocalDate.now());
            issueBooks.setId(issueBookId);
            issueBooks.setUser(byUsername);
            issueBooks.setBook(byBookCode);
            issueBooks.setExpectedOn(LocalDate.now().plusDays(14));
            issueBooks.setStatus(word);
            issueBooksRepository.save(issueBooks);
            Integer countOfBorrowed = byBookCode.getCountOfBorrowed();
            if (countOfBorrowed!=null) byBookCode.setCountOfBorrowed(countOfBorrowed+1);
            else byBookCode.setCountOfBorrowed(1);
            byBookCode.setState("issued");
            bookRepository.save(byBookCode);
        }
    }

    @Override
    public List<IssueBooks> getAllOngoingRecords() {
        ArrayList<IssueBooks> issueBooks = new ArrayList<>();
        List<edu.icet.crm.entity.IssueBooks> ongoing = issueBooksRepository.findByStatus(word);
        if (!ongoing.isEmpty()) {
            ongoing.forEach(issueBooks1 -> {
                IssueBooks issueBooks2 = mapper.convertValue(issueBooks1, IssueBooks.class);
                issueBooks2.setBookId(issueBooks1.getBook().getBookCode());
                issueBooks2.setUserId(issueBooks1.getUser().getUsername());
                issueBooks2.setIssuedOn(issueBooks1.getId().getIssuedOn());
                issueBooks2.setBookTitle(issueBooks1.getBook().getTitle());
                issueBooks2.setUserName(issueBooks1.getUser().getName());
                issueBooks.add(issueBooks2);
            });
        }
        return issueBooks;
    }

    @Override
    public List<IssueBooks> getAllOverdueRecords() {
        ArrayList<IssueBooks> issueBooks = new ArrayList<>();
        List<edu.icet.crm.entity.IssueBooks> ongoing = issueBooksRepository.findByFineStatusAndStatus(word2,word);
        if (!ongoing.isEmpty()) {
            ongoing.forEach(issueBooks1 -> {
                IssueBooks issueBooks2 = mapper.convertValue(issueBooks1, IssueBooks.class);
                issueBooks2.setBookId(issueBooks1.getBook().getBookCode());
                issueBooks2.setUserId(issueBooks1.getUser().getUsername());
                issueBooks2.setIssuedOn(issueBooks1.getId().getIssuedOn());
                issueBooks.add(issueBooks2);
            });
        }
        return issueBooks;
    }

    @Override
    public Integer countOfOngoing() {
        List<IssueBooks> allOngoingRecords = getAllOngoingRecords();
        if (!allOngoingRecords.isEmpty()) return allOngoingRecords.size();
        return null;
    }

    @Override
    public Integer countOfOverdue() {
        List<IssueBooks> allOverdueRecords = getAllOverdueRecords();
        if (!allOverdueRecords.isEmpty()) return allOverdueRecords.size();
        return null;
    }

    @Override
    public void deleteIssueBookRecord(IssueBooks books) {
        User byUsername = userRepository.findByUsername(books.getUserId());
        Book byBookCode = bookRepository.findByBookCode(books.getBookId());
        Optional<edu.icet.crm.entity.IssueBooks> byId = issueBooksRepository.findById(new IssueBookId(
                byUsername.getId(), byBookCode.getId(), books.getIssuedOn())
        );
        byId.ifPresent(issueBooksRepository::delete);
    }

    @Override
    public void markRecordComplete(IssueBooks issueBooks) {
        User byUsername = userRepository.findByUsername(issueBooks.getUserId());
        Book byBookCode = bookRepository.findByBookCode(issueBooks.getBookId());
        Optional<edu.icet.crm.entity.IssueBooks> byId = issueBooksRepository.findById(new IssueBookId(
                byUsername.getId(), byBookCode.getId(), issueBooks.getIssuedOn())
        );
        if(byId.isPresent()) {
            edu.icet.crm.entity.IssueBooks issueBooks1 = byId.get();
            issueBooks1.setStatus("OK");
            issueBooksRepository.save(issueBooks1);
        }
    }

    @Override
    @Transactional
    @Scheduled(fixedRate = 86400000)
    public void updateFinesOnRecords() {
        LocalDate today = LocalDate.now();
        List<edu.icet.crm.entity.IssueBooks> byExpectedOnBefore = issueBooksRepository.
                findByExpectedOnBeforeAndStatus(today,word);
        for (edu.icet.crm.entity.IssueBooks lateBooks : byExpectedOnBefore){
            long overdueDays = today.toEpochDay() - lateBooks.getExpectedOn().toEpochDay();
            lateBooks.setFineStatus(word2);
            lateBooks.setFine(overdueDays*100.00);
            issueBooksRepository.save(lateBooks);
        }
    }

    @Override
    public List<IssueBooks> getAll() {
        ArrayList<IssueBooks> issueBooks = new ArrayList<>();
        List<edu.icet.crm.entity.IssueBooks> ongoing = issueBooksRepository.findAll();
        if (!ongoing.isEmpty()) {
            ongoing.forEach(issueBooks1 -> {
                IssueBooks issueBooks2 = mapper.convertValue(issueBooks1, IssueBooks.class);
                issueBooks2.setBookId(issueBooks1.getBook().getBookCode());
                issueBooks2.setUserId(issueBooks1.getUser().getUsername());
                issueBooks2.setIssuedOn(issueBooks1.getId().getIssuedOn());
                issueBooks.add(issueBooks2);
            });
        }
        return issueBooks;
    }
}
