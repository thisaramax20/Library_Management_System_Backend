package edu.icet.crm.repository;

import edu.icet.crm.entity.UserLoginActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface UserLoginActivityRepository extends JpaRepository<UserLoginActivity,Integer> {
    List<UserLoginActivity> findByDate(LocalDate localDate);
}
