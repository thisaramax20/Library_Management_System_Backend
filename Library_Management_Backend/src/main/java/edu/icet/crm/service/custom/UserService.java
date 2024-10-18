package edu.icet.crm.service.custom;

import edu.icet.crm.dto.User;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    void save(User user);
    void update(User user);
    void delete(String username);
    List<User> getAll();
    List<User> getAllByJoinedDate(LocalDate localDate);
    boolean validateUserLogin(String username,String password);
}