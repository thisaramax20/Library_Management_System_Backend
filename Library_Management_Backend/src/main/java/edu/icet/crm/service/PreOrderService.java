package edu.icet.crm.service;

import edu.icet.crm.dto.PreOrder;

import java.util.List;

public interface PreOrderService {
    void save(PreOrder preOrder);
    List<PreOrder> getAll();
    void delete(String userId,String bookId);
    List<PreOrder> getByUserId(String userId);
}
