package edu.icet.crm.repository;

import edu.icet.crm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    @Query("SELECT MAX(e.id) FROM User e")
    Integer findMaxId();
    User findByUsername(String username);
    List<User> findByJoinedOn(LocalDate localDate);
}
