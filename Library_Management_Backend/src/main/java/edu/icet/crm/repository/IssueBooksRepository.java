package edu.icet.crm.repository;

import edu.icet.crm.entity.IssueBookId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueBooksRepository extends JpaRepository<edu.icet.crm.entity.IssueBooks, IssueBookId> {
}
