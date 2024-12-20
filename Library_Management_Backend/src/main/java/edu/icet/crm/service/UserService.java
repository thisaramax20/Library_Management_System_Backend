package edu.icet.crm.service;

import edu.icet.crm.dto.User;
import edu.icet.crm.dto.UserJoinedCountForPastMonths;
import edu.icet.crm.dto.UserLoginCountForPastDays;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public interface UserService {
    void save(User user);
    void update(User user);
    void delete(String username);
    List<User> getAll();
    List<User> getFirstFive();
    List<User> getAllByJoinedDate(LocalDate localDate);
    HashMap<String,String> validateUserLogin(String username, String password);
    int getUserVisitedCount(LocalDate localDate);
    User getByUserName(String username);
    List<UserLoginCountForPastDays> getPastLoginCount();
    List<UserJoinedCountForPastMonths> getPastJoinedOnCount();

}
