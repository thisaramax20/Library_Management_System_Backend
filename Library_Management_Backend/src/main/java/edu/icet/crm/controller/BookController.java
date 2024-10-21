package edu.icet.crm.controller;

import edu.icet.crm.dto.Book;
import edu.icet.crm.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    @PostMapping("/save")
    public void addBook(@RequestBody Book book){
        bookService.save(book);
    }

    @GetMapping("/get-all")
    public List<Book> getAll(){
        return bookService.getAll();
    }
}
