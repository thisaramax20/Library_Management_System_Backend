package edu.icet.crm.controller;

import edu.icet.crm.dto.Author;
import edu.icet.crm.dto.Book;
import edu.icet.crm.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
@CrossOrigin
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public void addAuthor(@RequestBody Author author){
        authorService.save(author);
    }

    @GetMapping("/get-all")
    public List<Author> getAll(){
        return authorService.getAll();
    }

    @GetMapping("/get-first-five")
    public List<Author> getFirstFive(){
        return authorService.getFirstFive();
    }

    @GetMapping("/get-all-books-by-author/{id}")
    public List<Book> getAllBooksByAuthor(@PathVariable String id){
        return authorService.getAllBooksByAuthor(id);
    }

    @DeleteMapping("/delete-by-id/{id}")
    public void deleteById(@PathVariable String id){
        authorService.delete(id);
    }

    @PutMapping("/update")
    public void update(@RequestBody Author author){
        authorService.update(author);
    }

    @GetMapping("/get-by-id/{id}")
    public Author getById(@PathVariable String id){
        return authorService.getById(id);
    }
}
