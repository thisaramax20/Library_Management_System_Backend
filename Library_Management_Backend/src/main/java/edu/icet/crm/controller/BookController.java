package edu.icet.crm.controller;

import edu.icet.crm.dto.Book;
import edu.icet.crm.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
@CrossOrigin
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

    @GetMapping("/get-first-5")
    public List<Book> getFirstFive(){
        return bookService.getFirstFive();
    }

    @PutMapping("/update")
    public void update(@RequestBody Book book){
        bookService.update(book);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable String id){
        bookService.delete(id);
    }

    @GetMapping("/get-by-category/{category}")
    public List<Book> getByCategory(@PathVariable String category){
        return bookService.getByCategory(category);
    }

    @GetMapping("/get-by-state/{state}")
    public List<Book> getByState(@PathVariable String state){
        return bookService.getByState(state);
    }

    @GetMapping("/get-top-five")
    public List<Book> getTopFive(){
        return bookService.getTopChoiceFive();
    }

    @GetMapping("/get-by-id/{id}")
    public Book getById(@PathVariable String id){
        return bookService.getByBookId(id);
    }
}
