package edu.icet.crm.repository;

import edu.icet.crm.entity.PreOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface PreOrderRepository extends JpaRepository<PreOrder,Integer> {
    void deleteByOrderTimeBefore(LocalDateTime dateTime);
    PreOrder findByUserIdAndBookId(Integer userId,Integer bookId);
    List<PreOrder> findByUserId(Integer userId);
}
