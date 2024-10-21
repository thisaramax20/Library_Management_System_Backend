package edu.icet.crm.repository;

import edu.icet.crm.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthorRepository extends JpaRepository<Author,Integer> {
    Author findByAuthorId(String id);
    @Query("SELECT MAX(e.id) FROM Author e")
    Integer getMaximumId();
}
