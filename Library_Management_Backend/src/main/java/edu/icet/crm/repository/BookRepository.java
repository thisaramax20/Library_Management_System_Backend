package edu.icet.crm.repository;

import edu.icet.crm.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {
    Book findByBookCode(String code);
    @Query("SELECT MAX(e.id) FROM Book e")
    Integer getHighestId();
    List<Book> findByCategory(String category);
    List<Book> findByState(String state);
}
