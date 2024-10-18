package edu.icet.crm.service.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.crm.dto.Admin;
import edu.icet.crm.dto.User;
import edu.icet.crm.entity.UserLoginActivity;
import edu.icet.crm.repository.UserLoginActivityRepository;
import edu.icet.crm.repository.UserRepository;
import edu.icet.crm.service.custom.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserLoginActivityRepository userLoginActivityRepository;
    private final ObjectMapper mapper;

    @Override
    public void save(User user) {
        edu.icet.crm.entity.User user1 = mapper.convertValue(user, edu.icet.crm.entity.User.class);
        user1.setPassword(user.getNic());
        Integer maxId = userRepository.findMaxId();
        if (maxId != null) {
            user1.setUsername("US-" + ++maxId);
        } else {
            user1.setUsername("AD-" + 1);
        }
        userRepository.save(user1);
    }

    @Override
    public void update(User user) {
        edu.icet.crm.entity.User byUsername = userRepository.findByUsername(user.getUsername());
        if (byUsername!=null){
            byUsername.setDob(user.getDob());
            byUsername.setName(user.getName());
            byUsername.setNic(user.getNic());
            byUsername.setAddress(user.getAddress());
            byUsername.setPassword(user.getPassword());
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
        all.forEach(user -> users.add(mapper.convertValue(user,User.class)));
        return users;
    }

    @Override
    public List<User> getAllByJoinedDate(LocalDate localDate) {
        List<edu.icet.crm.entity.User> all = userRepository.findByJoinedDate(localDate);
        ArrayList<User> users = new ArrayList<>();
        all.forEach(user -> users.add(mapper.convertValue(user,User.class)));
        return users;
    }

    @Override
    public boolean validateUserLogin(String username, String password) {
        edu.icet.crm.entity.User byUsername = userRepository.findByUsername(username);
        if(byUsername.getPassword().equals(password)){
            userLoginActivityRepository.save(new UserLoginActivity(null,username,LocalDate.now()));
            return true;
        }else return false;
    }

    @Override
    public int getUserVisitedCount(LocalDate localDate) {
        return userLoginActivityRepository.findByDate(localDate).size();
    }
}
