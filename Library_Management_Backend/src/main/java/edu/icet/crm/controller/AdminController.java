package edu.icet.crm.controller;

import edu.icet.crm.dto.Admin;
import edu.icet.crm.service.ServiceFactory;
import edu.icet.crm.service.custom.AdminService;
import edu.icet.crm.service.custom.impl.AdminServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
//@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/save")
    public void saveAdmin(@RequestBody Admin admin){
        adminService.save(admin);
    }

    @PutMapping("/update")
    public void updateAdmin(@RequestBody Admin admin){
        adminService.update(admin);
    }

    @DeleteMapping("/delete/{username}")
    public void deleteAdmin(@PathVariable String username){
        adminService.delete(username);
    }

    @GetMapping("/get-all")
    public List<Admin> getAll(){
        return adminService.getAll();
    }
}
