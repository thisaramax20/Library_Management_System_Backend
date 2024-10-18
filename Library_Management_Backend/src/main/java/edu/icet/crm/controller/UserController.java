package edu.icet.crm.controller;
import edu.icet.crm.dto.User;
import edu.icet.crm.service.custom.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @PostMapping("/save")
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

    @GetMapping("/get-count-visited/{localDate}")
    public int getCountVisited(@PathVariable LocalDate localDate){
        return userService.getUserVisitedCount(localDate);
    }
}
