package edu.icet.crm.controller;

import edu.icet.crm.dto.Admin;
import edu.icet.crm.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
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

    @PostMapping("/validate-login")
    public Map<String,String> validateLogin(@RequestParam("username") String username,
                                            @RequestParam("password") String password ){
        return adminService.validateAdminLogin(username,password);
    }

    @GetMapping("/get-by-id/{username}")
    public Admin getById(@PathVariable String username){
        return adminService.getById(username);
    }
}
