package edu.icet.crm.controller;

import edu.icet.crm.dto.PreOrder;
import edu.icet.crm.service.PreOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pre-order")
@RequiredArgsConstructor
@CrossOrigin
public class PreOrderController {
    private final PreOrderService preOrderService;

    @PostMapping("/save")
    public void save(@RequestBody PreOrder preOrder){
        preOrderService.save(preOrder);
    }

    @DeleteMapping("/delete/{userId}/{bookId}")
    public void delete(@PathVariable String userId,@PathVariable String bookId){
        preOrderService.delete(userId, bookId);
    }

    @GetMapping("/get-all")
    public List<PreOrder> getAll(){
        return preOrderService.getAll();
    }

    @GetMapping("/get-by-userId/{id}")
    public List<PreOrder> getByUser(@PathVariable String id){
        return preOrderService.getByUserId(id);
    }
}
