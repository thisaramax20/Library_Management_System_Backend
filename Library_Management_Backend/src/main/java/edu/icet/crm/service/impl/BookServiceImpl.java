package edu.icet.crm.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.crm.dto.Book;
import edu.icet.crm.entity.Author;
import edu.icet.crm.repository.AuthorRepository;
import edu.icet.crm.repository.BookRepository;
import edu.icet.crm.service.BookService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final ObjectMapper mapper;
    private final AuthorRepository authorRepository;

    @Override
    @Transactional
    public boolean save(Book book) {
        edu.icet.crm.entity.Book book1 = mapper.convertValue(book, edu.icet.crm.entity.Book.class);
        Integer highestId = bookRepository.getHighestId();
        if (highestId != null) {
            book1.setBookCode("BK-" + ++highestId);
        } else {
            book1.setBookCode("BK-" + 1);
        }
        Author byAuthorId = authorRepository.findByAuthorId(book.getAuthorId());
        if (byAuthorId!=null) {
            book1.setAuthor(byAuthorId);

            if(byAuthorId.getBooks()==null) byAuthorId.setBooks(new HashSet<>());
            byAuthorId.getBooks().add(book1);
            authorRepository.save(byAuthorId);
            bookRepository.save(book1);
        }
        return true;
    }

    @Override
    public void update(Book book) {
        edu.icet.crm.entity.Book byBookCode = bookRepository.findByBookCode(book.getBookCode());
        if (byBookCode!=null) {
            book.setId(byBookCode.getId());
            bookRepository.save(mapper.convertValue(book,edu.icet.crm.entity.Book.class));
        }
    }

    @Override
    @Transactional
    public void delete(String bookCode) {
        edu.icet.crm.entity.Book byBookCode = bookRepository.findByBookCode(bookCode);
        if (byBookCode!=null) {
            authorRepository.findByAuthorId(byBookCode.getAuthor().getAuthorId())
                    .getBooks().remove(byBookCode);
            bookRepository.delete(byBookCode);
        }
    }

    @Override
    public List<Book> getAll() {
        List<Book> books = new ArrayList<>();
        bookRepository.findAll().forEach(book -> {
            Book book1 = mapper.convertValue(book, Book.class);
            book1.setAuthorId(book.getAuthor().getAuthorId());
            books.add(book1);
        });
        return books;
    }
}
