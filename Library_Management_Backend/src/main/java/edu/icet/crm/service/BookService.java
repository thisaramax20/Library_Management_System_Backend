package edu.icet.crm.service;

import edu.icet.crm.dto.Book;
import java.util.List;

public interface BookService {
    boolean save(Book book);
    void update(Book book);
    void delete(String bookCode);
    List<Book> getAll();
    List<Book> getFirstFive();
    List<Book> getByCategory(String category);
    List<Book> getByState(String state);
    List<Book> getTopChoiceFive();
    Book getByBookId(String id);
}
