package edu.icet.crm.service;

import edu.icet.crm.dto.Admin;

import java.util.HashMap;
import java.util.List;

public interface AdminService {
    void save(Admin admin);
    void update(Admin admin);
    void delete(String username);
    List<Admin> getAll();
    HashMap<String,String> validateAdminLogin(String username, String password);
    Admin getById(String username);
}
