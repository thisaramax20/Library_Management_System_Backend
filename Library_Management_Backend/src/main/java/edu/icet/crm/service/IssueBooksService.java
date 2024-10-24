package edu.icet.crm.service;

import edu.icet.crm.dto.IssueBooks;

import java.util.List;

public interface IssueBooksService {
    void save(IssueBooks books);
    List<IssueBooks> getAllOngoingRecords();
    void deleteIssueBookRecord(IssueBooks books);
    void markRecordComplete(IssueBooks issueBooks);
    void updateFinesOnRecords();
    List<IssueBooks> getAll();
}
