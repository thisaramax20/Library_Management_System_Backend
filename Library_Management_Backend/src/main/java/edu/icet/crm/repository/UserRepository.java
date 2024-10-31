package edu.icet.crm.repository;

import edu.icet.crm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    @Query("SELECT MAX(e.id) FROM User e")
    Integer findMaxId();
    User findByUsername(String username);
    List<User> findByJoinedOn(LocalDate localDate);
    @Query("SELECT COUNT(u) FROM User u WHERE MONTH(u.joinedOn) = :month AND YEAR(u.joinedOn) = :year")
    Integer countJoinedOnByPastMonths(@Param("month") int month,@Param("year") int year);
}
