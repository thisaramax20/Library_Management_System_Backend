package edu.icet.crm.service.impl;

import edu.icet.crm.repository.PreOrderRepository;
import edu.icet.crm.service.PreOrderCleanUp;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PreOrderCleanUpImpl implements PreOrderCleanUp {
    private final PreOrderRepository preOrderRepository;

    @Override
    @Scheduled(fixedRate = 86400000)
    @Transactional
    public void deleteRecords() {
        LocalDateTime localDateTime = LocalDateTime.now().minusHours(48);
            preOrderRepository.deleteByOrderTimeBefore(localDateTime);
    }
}
