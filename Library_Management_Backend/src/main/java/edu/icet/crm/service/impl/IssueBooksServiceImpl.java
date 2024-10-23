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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class IssueBooksServiceImpl implements IssueBooksService {
    private final IssueBooksRepository issueBooksRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ObjectMapper mapper;

    @Override
    public void save(IssueBooks books) {
        edu.icet.crm.entity.IssueBooks issueBooks = mapper.convertValue(books, edu.icet.crm.entity.IssueBooks.class);
        User byUsername = userRepository.findByUsername(books.getUserId());
        Book byBookCode = bookRepository.findByBookCode(books.getBookId());
        System.out.println(byBookCode.getId());
        if (byBookCode!=null && byUsername!=null){
            IssueBookId issueBookId = new IssueBookId(byUsername.getId(), byBookCode.getId(), LocalDate.now());
            issueBooks.setId(issueBookId);
            issueBooks.setUser(byUsername);
            issueBooks.setBook(byBookCode);
            issueBooks.setExpectedOn(LocalDate.now().plusDays(14));
            issueBooksRepository.save(issueBooks);
        }
    }
}
