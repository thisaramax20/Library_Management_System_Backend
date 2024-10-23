package edu.icet.crm.service.impl;

import edu.icet.crm.dto.PreOrder;
import edu.icet.crm.entity.Book;
import edu.icet.crm.entity.User;
import edu.icet.crm.repository.BookRepository;
import edu.icet.crm.repository.PreOrderRepository;
import edu.icet.crm.repository.UserRepository;
import edu.icet.crm.service.PreOrderService;
import edu.icet.crm.util.EmailSending;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PreOrderServiceImpl implements PreOrderService {
    private final PreOrderRepository preOrderRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final EmailSending emailSending;

    @Override
    public void save(PreOrder preOrder) {
        User byUsername = userRepository.findByUsername(preOrder.getUserId());
        Book byBookCode = bookRepository.findByBookCode(preOrder.getBookId());
        if (byBookCode==null || byUsername==null) return;
        preOrderRepository.save(new edu.icet.crm.entity.PreOrder(null,
                byUsername,
                byBookCode,
                LocalDateTime.now()
        ));
        emailSending.sendPreOrderEmail(byUsername.getEmail(),byBookCode.getTitle());
    }

    @Override
    public List<PreOrder> getAll() {
        ArrayList<PreOrder> preOrders = new ArrayList<>();
        preOrderRepository.findAll().forEach(preOrder -> {
            preOrders.add(new PreOrder(preOrder.getId(),
                    preOrder.getUser().getUsername(),
                    preOrder.getBook().getBookCode(),
                    preOrder.getOrderTime()
            ));
        });
        return preOrders;
    }

    @Override
    public void delete(String userId, String bookId) {
        User byUsername = userRepository.findByUsername(userId);
        Book byBookCode = bookRepository.findByBookCode(bookId);
        edu.icet.crm.entity.PreOrder byUserIdAndBookId = preOrderRepository.
                findByUserIdAndBookId(byUsername.getId(), byBookCode.getId());
        if (byUserIdAndBookId!=null) preOrderRepository.delete(byUserIdAndBookId);
    }
}
