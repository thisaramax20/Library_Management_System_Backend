package edu.icet.crm.service.custom;

import edu.icet.crm.dto.Admin;
import edu.icet.crm.service.SuperService;

import java.util.List;

public interface AdminService extends SuperService {
    void save(Admin admin);
    void update(Admin admin);
    void delete(String username);
    List<Admin> getAll();
}
