package edu.icet.crm.controller;

import edu.icet.crm.dto.IssueBooks;
import edu.icet.crm.service.IssueBooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/issue-books")
public class IssueBooksController {
    private final IssueBooksService issueBooksService;

    @PostMapping("/save")
    public void save(@RequestBody IssueBooks books){
        issueBooksService.save(books);
    }

    @GetMapping("/get-ongoing")
    public List<IssueBooks> getOngoing(){
        return issueBooksService.getAllOngoingRecords();
    }

    @GetMapping("/get-all")
    public List<IssueBooks> getAll(){
        return issueBooksService.getAll();
    }

    @PostMapping("/mark-received")
    public void markReceived(@RequestBody IssueBooks books){
        issueBooksService.markRecordComplete(books);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody IssueBooks books){
        issueBooksService.deleteIssueBookRecord(books);
    }
}
