package edu.icet.crm.controller;

import edu.icet.crm.dto.Author;
import edu.icet.crm.dto.Book;
import edu.icet.crm.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping("/save")
    public void addAuthor(@RequestBody Author author){
        authorService.save(author);
    }

    @GetMapping("/get-all")
    public List<Author> getAll(){
        return authorService.getAll();
    }

    @GetMapping("/get-all-books-by-author/{id}")
    public List<Book> getAllBooksByAuthor(@PathVariable String id){
        return authorService.getAllBooksByAuthor(id);
    }

    @DeleteMapping("/delete-by-id/{id}")
    public void deleteById(@PathVariable String id){
        authorService.delete(id);
    }
}
