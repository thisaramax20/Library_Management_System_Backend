package edu.icet.crm.controller;

import edu.icet.crm.dto.IssueBooks;
import edu.icet.crm.service.IssueBooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/issue-books")
public class IssueBooksController {
    private final IssueBooksService issueBooksService;

    @PostMapping("/save")
    public void save(@RequestBody IssueBooks books){
        issueBooksService.save(books);
    }
}
