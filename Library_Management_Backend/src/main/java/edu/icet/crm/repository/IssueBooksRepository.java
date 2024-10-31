package edu.icet.crm.repository;

import edu.icet.crm.entity.IssueBookId;
import edu.icet.crm.entity.IssueBooks;
import edu.icet.crm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IssueBooksRepository extends JpaRepository<edu.icet.crm.entity.IssueBooks, IssueBookId> {
    List<IssueBooks> findByExpectedOnBeforeAndStatus(LocalDate localDate,String status);
    List<IssueBooks> findByStatus(String status);
    List<IssueBooks> findByFineStatusAndStatus(String fineStatus,String status);
    Integer countByUser(User user);
    @Query("SELECT COUNT(i) FROM IssueBooks i JOIN i.book b WHERE i.id.issuedOn = :date AND b.category = :category")
    Integer countOfBooksCheckOutByCategory(@Param("date") LocalDate date,@Param("category") String category);
    @Query("SELECT SUM(i.fine) FROM IssueBooks i WHERE MONTH(i.id.issuedOn) = :month AND YEAR(i.id.issuedOn) = :year")
    Double totalFineForMonth(@Param("month") int month,@Param("year") int year);
}
