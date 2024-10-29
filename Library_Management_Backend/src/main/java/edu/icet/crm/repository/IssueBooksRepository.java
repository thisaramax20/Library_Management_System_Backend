package edu.icet.crm.repository;

import edu.icet.crm.entity.IssueBookId;
import edu.icet.crm.entity.IssueBooks;
import edu.icet.crm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface IssueBooksRepository extends JpaRepository<edu.icet.crm.entity.IssueBooks, IssueBookId> {
    List<IssueBooks> findByExpectedOnBeforeAndStatus(LocalDate localDate,String status);
    List<IssueBooks> findByStatus(String status);
    List<IssueBooks> findByFineStatusAndStatus(String fineStatus,String status);
    Integer countByUser(User user);
}
