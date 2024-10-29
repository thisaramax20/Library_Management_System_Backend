package edu.icet.crm.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.crm.dto.Author;
import edu.icet.crm.dto.Book;
import edu.icet.crm.repository.AuthorRepository;
import edu.icet.crm.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final ObjectMapper mapper;

    @Override
    public void save(Author author) {
        edu.icet.crm.entity.Author author1 = mapper.convertValue(author, edu.icet.crm.entity.Author.class);
        Integer maximumId = authorRepository.getMaximumId();
        if (maximumId!=null){
            author1.setAuthorId("AT-"+ (maximumId+1));
        }else {
            author1.setAuthorId("AT-"+1);
        }
        authorRepository.save(author1);
    }

    @Override
    public void update(Author author) {
        edu.icet.crm.entity.Author byAuthorId = authorRepository.findByAuthorId(author.getAuthorId());
        if (byAuthorId!=null) {
            author.setId(byAuthorId.getId());
            authorRepository.save(mapper.convertValue(author,edu.icet.crm.entity.Author.class));
        }
    }

    @Override
    public void delete(String authorId) {
        edu.icet.crm.entity.Author byAuthorId = authorRepository.findByAuthorId(authorId);
        if (byAuthorId!=null) authorRepository.delete(byAuthorId);
    }

    @Override
    public List<Author> getAll() {
        ArrayList<Author> authors = new ArrayList<>();
        authorRepository.findAll().forEach(author -> {
            authors.add(mapper.convertValue(author, Author.class));
        });
        return authors;
    }

    @Override
    public List<Author> getFirstFive() {
        ArrayList<Author> authors = new ArrayList<>();
        authorRepository.findAll(PageRequest.of(0,5)).getContent().forEach(author -> {
            authors.add(mapper.convertValue(author, Author.class));
        });
        return authors;
    }

    @Override
    public List<Book> getAllBooksByAuthor(String authorId) {
        edu.icet.crm.entity.Author byAuthorId = authorRepository.findByAuthorId(authorId);
        ArrayList<Book> books = new ArrayList<>();
        if (byAuthorId!=null) byAuthorId.getBooks().forEach(book -> books.add(mapper.convertValue(book, Book.class)));
        return books;
    }
}
