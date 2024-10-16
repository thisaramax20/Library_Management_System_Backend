package edu.icet.crm.repository;

import edu.icet.crm.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Integer> {
}
