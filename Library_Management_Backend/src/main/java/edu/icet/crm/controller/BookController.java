package edu.icet.crm.controller;

import edu.icet.crm.dto.Book;
import edu.icet.crm.dto.BookByCategoryCount;
import edu.icet.crm.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
@CrossOrigin
public class BookController {
    private final BookService bookService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBook(@RequestParam(value = "image",required = false) MultipartFile image, @RequestParam("title") String title,
                        @RequestParam("author") String author,
                        @RequestParam("category") String category,
                        @RequestParam("isbn") String isbn){
        byte[] imageData;
        if (image!=null){
            try {
                imageData = image.getBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else imageData = null;
        bookService.save(new Book(null,null,
                title,isbn,category,null,null,imageData,null,author,null));
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

    @GetMapping("/get-count-by-category")
    public List<BookByCategoryCount> getCountByCategory(){
        return bookService.getCountOfBooks();
    }
}
