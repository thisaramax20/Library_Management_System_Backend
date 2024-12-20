package edu.icet.crm.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.crm.dto.Book;
import edu.icet.crm.dto.BookByCategoryCount;
import edu.icet.crm.entity.Author;
import edu.icet.crm.repository.AuthorRepository;
import edu.icet.crm.repository.BookRepository;
import edu.icet.crm.service.BookService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final ObjectMapper mapper;
    private final AuthorRepository authorRepository;
    private static final String imagePrefix = "data:image/jpeg;base64,";

    @Override
    @Transactional
    public boolean save(Book book) {
        edu.icet.crm.entity.Book book1 = mapper.convertValue(book, edu.icet.crm.entity.Book.class);
        Integer highestId = bookRepository.getHighestId();
        if (highestId != null) {
            book1.setBookCode("BK-" + (highestId+1));
        } else {
            book1.setBookCode("BK-" + 1);
        }
        Author byAuthorId = authorRepository.findByAuthorId(book.getAuthorId());
        if (byAuthorId!=null) {
            book1.setAuthor(byAuthorId);
            book1.setState("available");

            if(byAuthorId.getBooks()==null) byAuthorId.setBooks(new HashSet<>());
            byAuthorId.getBooks().add(book1);
            authorRepository.save(byAuthorId);
        }
        return true;
    }

    @Override
    public void update(Book book) {
        edu.icet.crm.entity.Book byBookCode = bookRepository.findByBookCode(book.getBookCode());
        if (byBookCode!=null) {
            byBookCode.setTitle(book.getTitle());
            byBookCode.setCategory(book.getCategory());
            Author author = byBookCode.getAuthor();
            if (author!=null) author.getBooks().add(byBookCode);
            bookRepository.save(byBookCode);
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
            book1.setAuthorName(book.getAuthor().getName());
            books.add(book1);
        });
        return books;
    }

    @Override
    public List<Book> getFirstFive() {
        List<Book> books = new ArrayList<>();
        bookRepository.findAll(PageRequest.of(0,5)).getContent().forEach(book -> {
            Book book1 = mapper.convertValue(book, Book.class);
            book1.setAuthorName(book.getAuthor().getName());
            if (book.getImageData()!=null) {
                String base64Image = Base64.getEncoder().encodeToString(book.getImageData());
                String base64DataUrl = imagePrefix + base64Image;
                book1.setSrc(base64DataUrl);
            }
            books.add(book1);
        });
        return books;
    }

    @Override
    public List<Book> getByCategory(String category) {
        ArrayList<Book> books = new ArrayList<>();
        bookRepository.findByCategory(category).forEach(book -> {
            Book book1 = mapper.convertValue(book, Book.class);
            book1.setAuthorName(book.getAuthor().getName());
            if (book.getImageData()!=null) {
                String base64Image = Base64.getEncoder().encodeToString(book.getImageData());
                String base64DataUrl = imagePrefix + base64Image;
                book1.setSrc(base64DataUrl);
            }
            books.add(book1);
        });
        return books;
    }

    @Override
    public List<Book> getByState(String state) {
        ArrayList<Book> books = new ArrayList<>();
        bookRepository.findByState(state).forEach(book -> {
            Book book1 = mapper.convertValue(book, Book.class);
            book1.setAuthorName(book.getAuthor().getName());
            if (book.getImageData()!=null) {
                String base64Image = Base64.getEncoder().encodeToString(book.getImageData());
                String base64DataUrl = imagePrefix + base64Image;
                book1.setSrc(base64DataUrl);
            }
            books.add(book1);
        });
        return books;
    }

    @Override
    public List<Book> getTopChoiceFive() {
        ArrayList<Book> books = new ArrayList<>();
        bookRepository.findAll(PageRequest.of(
                0,5,
                Sort.by(Sort.Direction.DESC,"countOfBorrowed"))).
                forEach(book -> {
                    Book book1 = mapper.convertValue(book, Book.class);
                    if (book.getImageData()!=null) {
                        String base64Image = Base64.getEncoder().encodeToString(book.getImageData());
                        String base64DataUrl = imagePrefix + base64Image;
                        book1.setSrc(base64DataUrl);
                    }
                    books.add(book1);
                });
        return books;
    }

    @Override
    public Book getByBookId(String id) {
        edu.icet.crm.entity.Book byBookCode = bookRepository.findByBookCode(id);
        if (byBookCode!=null) {
            Book book = mapper.convertValue(byBookCode, Book.class);
            book.setAuthorName(byBookCode.getAuthor().getName());
            return book;
        }
        return null;
    }

    @Override
    public List<BookByCategoryCount> getCountOfBooks() {
        ArrayList<BookByCategoryCount> bookByCategoryCounts = new ArrayList<>();
        bookByCategoryCounts.add(new BookByCategoryCount("Education",getByCategory("Education").size()));
        bookByCategoryCounts.add(new BookByCategoryCount("Fiction",getByCategory("Fiction").size()));
        bookByCategoryCounts.add(new BookByCategoryCount("Social",getByCategory("Social").size()));
        return bookByCategoryCounts;
    }
}
