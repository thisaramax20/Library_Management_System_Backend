package edu.icet.crm.repository;

import edu.icet.crm.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepository extends JpaRepository<Admin,Integer> {
    @Query("SELECT MAX(e.id) FROM Admin e")
    Integer findMaxId();
    Admin findByUsername(String username);
}
