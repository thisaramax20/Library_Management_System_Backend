package edu.icet.crm.service.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.crm.dto.Admin;
import edu.icet.crm.repository.AdminRepository;
import edu.icet.crm.service.custom.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final ObjectMapper mapper;

    @Override
    public void save(Admin admin) {
        edu.icet.crm.entity.Admin admin1 = mapper.convertValue(admin, edu.icet.crm.entity.Admin.class);
        admin1.setPassword(admin.getNic());
        Integer maxId = adminRepository.findMaxId();
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
            byUsername.setDob(admin.getDob());
            byUsername.setName(admin.getName());
            byUsername.setNic(admin.getNic());
            byUsername.setAddress(admin.getAddress());
            byUsername.setPassword(admin.getPassword());
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

}
