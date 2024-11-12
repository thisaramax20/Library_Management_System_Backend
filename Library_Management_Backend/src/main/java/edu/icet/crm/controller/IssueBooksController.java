package edu.icet.crm.controller;

import edu.icet.crm.dto.BookCheckoutCountByCategory;
import edu.icet.crm.dto.IssueBooks;
import edu.icet.crm.dto.TotalFineByMonth;
import edu.icet.crm.service.IssueBooksService;
import edu.icet.crm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/issue-books")
@CrossOrigin
public class IssueBooksController {
    private final IssueBooksService issueBooksService;
    private final UserService userService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.ACCEPTED)
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

    @GetMapping("/get-overdue")
    public List<IssueBooks> getAllOverdue(){
        return issueBooksService.getAllOverdueRecords();
    }

    @PostMapping("/mark-received")
    public void markReceived(@RequestBody IssueBooks books){
        issueBooksService.markRecordComplete(books);
    }

    @PostMapping("/delete")
    public void delete(@RequestBody IssueBooks books){
        issueBooksService.deleteIssueBookRecord(books);
    }

    @GetMapping("/get-counts")
    public Map<String,Integer> getOngoingCount(){
        HashMap<String, Integer> map = new HashMap<>();
        map.put("borrowed",issueBooksService.countOfOngoing());
        map.put("overdue",issueBooksService.countOfOverdue());
        map.put("totalVisitors", userService.getUserVisitedCount(LocalDate.now()));
        return map;
    }

    @GetMapping("/get-checkout-count-by-category")
    public List<BookCheckoutCountByCategory> getCheckoutCountByCategory(){
        return issueBooksService.getCheckoutCountByCategory();
    }

    @GetMapping("/total-fine-by-month")
    public List<TotalFineByMonth> getTotalFineBiMonthly(){
        return issueBooksService.getTotalFineByMonth();
    }
}
