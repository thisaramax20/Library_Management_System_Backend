package edu.icet.crm.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.crm.dto.User;
import edu.icet.crm.dto.UserJoinedCountForPastMonths;
import edu.icet.crm.dto.UserLoginCountForPastDays;
import edu.icet.crm.entity.UserLoginActivity;
import edu.icet.crm.repository.IssueBooksRepository;
import edu.icet.crm.repository.UserLoginActivityRepository;
import edu.icet.crm.repository.UserRepository;
import edu.icet.crm.service.UserService;
import edu.icet.crm.util.EncryptPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserLoginActivityRepository userLoginActivityRepository;
    private final IssueBooksRepository issueBooksRepository;
    private final ObjectMapper mapper;
    private final EncryptPassword encryptPassword = EncryptPassword.getInstance();

    @Override
    public void save(User user) {
        edu.icet.crm.entity.User user1 = mapper.convertValue(user, edu.icet.crm.entity.User.class);
        user1.setPassword(encryptPassword.hashingPassword(user.getNic()));
        user1.setJoinedOn(LocalDate.now());
        Integer maxId = userRepository.findMaxId();
        if (maxId != null) {
            user1.setUsername("US-" + ++maxId);
        } else {
            user1.setUsername("US-" + 1);
        }
        userRepository.save(user1);
    }

    @Override
    public void update(User user) {
        edu.icet.crm.entity.User byUsername = userRepository.findByUsername(user.getUsername());
        if (byUsername!=null){
            user.setId(byUsername.getId());
            userRepository.save(mapper.convertValue(user,edu.icet.crm.entity.User.class));
        }
    }

    @Override
    public void delete(String username) {
        edu.icet.crm.entity.User byUsername = userRepository.findByUsername(username);
        if (byUsername!=null){
            userRepository.delete(byUsername);
        }
    }

    @Override
    public List<User> getAll() {
        List<edu.icet.crm.entity.User> all = userRepository.findAll();
        ArrayList<User> users = new ArrayList<>();
        all.forEach(user -> {
            User user1 = mapper.convertValue(user, User.class);
            user1.setCountOfBooksIssued(issueBooksRepository.countByUser(user));
            users.add(user1);
        });
        return users;
    }

    @Override
    public List<User> getFirstFive() {
        List<edu.icet.crm.entity.User> users = userRepository.findAll(PageRequest.of(0, 5))
                .getContent();
        ArrayList<User> users1 = new ArrayList<>();
        users.forEach(user -> {
            User user1 = mapper.convertValue(user, User.class);
            user1.setCountOfBooksIssued(issueBooksRepository.countByUser(user));
            users1.add(user1);
        });
        return users1;
    }

    @Override
    public List<User> getAllByJoinedDate(LocalDate localDate) {
        List<edu.icet.crm.entity.User> all = userRepository.findByJoinedOn(localDate);
        ArrayList<User> users = new ArrayList<>();
        all.forEach(user -> users.add(mapper.convertValue(user,User.class)));
        return users;
    }

    @Override
    public HashMap<String,String> validateUserLogin(String username, String password) {
        HashMap<String, String> hashMap = new HashMap<>();
        edu.icet.crm.entity.User byUsername = userRepository.findByUsername(username);
        if(byUsername!=null){
            if (encryptPassword.checkPassword(password,byUsername.getPassword())){
                hashMap.put("auth","yes");
                hashMap.put("type","US");
                hashMap.put("name", byUsername.getName());
                userLoginActivityRepository.save(new UserLoginActivity(null,username,LocalDate.now()));
                return hashMap;
            }else {
                hashMap.put("auth","no");
                hashMap.put("type","US");
                hashMap.put("message","password incorrect");
                return hashMap;
            }
        }else {
          hashMap.put("auth","no");
          hashMap.put("type","US");
          hashMap.put("message","user not found");
          return hashMap;
        }
    }

    @Override
    public int getUserVisitedCount(LocalDate localDate) {
        return userLoginActivityRepository.findByDate(localDate).size();
    }

    @Override
    public User getByUserName(String username) {
        edu.icet.crm.entity.User byUsername = userRepository.findByUsername(username);
        if (byUsername!=null) return mapper.convertValue(byUsername,User.class);
        return null;
    }

    @Override
    public List<UserLoginCountForPastDays> getPastLoginCount() {
        LocalDate date = LocalDate.now();
        ArrayList<UserLoginCountForPastDays> count = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            LocalDate previous = date.minusDays(i);
            count.add(new UserLoginCountForPastDays(previous,userLoginActivityRepository.findByDate(previous).size()));
        }
        return count;
    }

    @Override
    public List<UserJoinedCountForPastMonths> getPastJoinedOnCount() {
        LocalDate date = LocalDate.now();
        ArrayList<UserJoinedCountForPastMonths> count = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            LocalDate previous = date.minusMonths(i);
            int month = previous.getMonthValue();
            int year = previous.getYear();
            count.add(new UserJoinedCountForPastMonths(previous.getMonth().name(),
                    userRepository.countJoinedOnByPastMonths(month,year)));
        }
        return count;
    }
}
