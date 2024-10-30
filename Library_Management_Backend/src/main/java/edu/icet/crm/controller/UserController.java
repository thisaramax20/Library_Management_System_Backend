package edu.icet.crm.controller;
import edu.icet.crm.dto.User;
import edu.icet.crm.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    private final UserServiceImpl userService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveUser(@RequestBody User user){
        userService.save(user);
    }

    @DeleteMapping("/delete/{username}")
    public void deleteUser(@PathVariable String username){
        userService.delete(username);
    }

    @PutMapping("/update")
    public void updateUser(@RequestBody User user){
        userService.update(user);
    }

    @GetMapping("/get-all")
    public List<User> getAll(){
        return userService.getAll();
    }

    @GetMapping("/get-first-5")
    public List<User> getFirstFive(){
        return userService.getFirstFive();
    }

    @GetMapping("/get-by-joinedDate/{localDate}")
    public List<User> getByJoinedDate(@PathVariable LocalDate localDate){
        return userService.getAllByJoinedDate(localDate);
    }

    @GetMapping("/get-by-username/{username}")
    public User getByUsername(@PathVariable String username){
        return userService.getByUserName(username);
    }
}
