package edu.icet.crm.service;

import edu.icet.crm.dto.Admin;
import edu.icet.crm.dto.Author;
import edu.icet.crm.dto.Book;

import java.util.List;

public interface AuthorService {
    void save(Author author);
    void update(Author author);
    void delete(String authorId);
    List<Author> getAll();
    List<Author> getFirstFive();
    List<Book> getAllBooksByAuthor(String authorId);
}
