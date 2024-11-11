package edu.icet.crm.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.crm.dto.Admin;
import edu.icet.crm.entity.UserLoginActivity;
import edu.icet.crm.repository.AdminRepository;
import edu.icet.crm.service.AdminService;
import edu.icet.crm.util.EncryptPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final ObjectMapper mapper;
    private final EncryptPassword encryptPassword = EncryptPassword.getInstance();

    @Override
    public void save(Admin admin) {
        edu.icet.crm.entity.Admin admin1 = mapper.convertValue(admin, edu.icet.crm.entity.Admin.class);
        Integer maxId = adminRepository.findMaxId();
        admin1.setPassword(encryptPassword.hashingPassword(admin.getNic()));
        if (maxId != null) {
            admin1.setUsername("AD-" + ++maxId);
        } else {
            admin1.setUsername("AD-" + 1);
        }
        adminRepository.save(admin1);
    }

    @Override
    public void update(Admin admin) {
        edu.icet.crm.entity.Admin byUsername = adminRepository.findByUsername(admin.getUsername());
        if (byUsername!=null){
            byUsername.setNic(admin.getNic());
            byUsername.setDob(admin.getDob());
            byUsername.setName(admin.getName());
            byUsername.setAddress(admin.getAddress());
            adminRepository.save(byUsername);
        }
    }

    @Override
    public void delete(String username) {
        edu.icet.crm.entity.Admin byUsername = adminRepository.findByUsername(username);
        if (byUsername!=null){
            adminRepository.delete(byUsername);
        }
    }

    @Override
    public List<Admin> getAll() {
        List<edu.icet.crm.entity.Admin> all = adminRepository.findAll();
        ArrayList<Admin> admins = new ArrayList<>();
        all.forEach(admin -> admins.add(mapper.convertValue(admin,Admin.class)));
        return admins;
    }

    @Override
    public HashMap<String, String> validateAdminLogin(String username, String password) {
        HashMap<String, String> hashMap = new HashMap<>();
        edu.icet.crm.entity.Admin byUsername = adminRepository.findByUsername(username);
        if(byUsername!=null){
            if (encryptPassword.checkPassword(password,byUsername.getPassword())){
                hashMap.put("auth","yes");
                hashMap.put("type","AD");
                hashMap.put("name", byUsername.getName());
                return hashMap;
            }else {
                hashMap.put("auth","no");
                hashMap.put("type","AD");
                hashMap.put("message","password incorrect");
                return hashMap;
            }
        }else {
            hashMap.put("auth","no");
            hashMap.put("type","AD");
            hashMap.put("message","user not found");
            return hashMap;
        }
    }

    @Override
    public Admin getById(String username) {
        return mapper.convertValue(adminRepository.findByUsername(username), Admin.class);
    }
}
