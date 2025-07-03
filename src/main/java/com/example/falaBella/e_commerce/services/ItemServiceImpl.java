package com.example.falaBella.e_commerce.services;

import com.example.falaBella.e_commerce.model.Item;
import com.example.falaBella.e_commerce.repository.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepo itemRepo;

    @Override
    public boolean addSupply(Long itemId, int quantity) {
        if (itemRepo.findById(itemId).isPresent()) {
            itemRepo.findById(itemId).ifPresent(item -> {
                item.setTotalQuantity(item.getTotalQuantity() + quantity);
                itemRepo.save(item);
            });
            return true;
        } else return false;
    }

    @Override
    public boolean reserveItem(Long itemId, int quantity) {
        if (itemRepo.findById(itemId).isPresent()) {
            itemRepo.findById(itemId).ifPresent(item -> {
                    item.setReservedQuantity(item.getReservedQuantity() + quantity);
                    itemRepo.save(item);
            });
            return true;
        } else return false;
    }

    @Override
    public boolean addItem(Item item) {
        if (itemRepo.findById(item.getItemId()).isEmpty()) {
            itemRepo.save(item);
            return true;
        } else {
            return false; // Item already exists
        }
    }

    @Override
    @Cacheable(value = "availableQuantity", key = "#itemId")
    public boolean cancelReservation(Long itemId, int quantity) {
        if (itemRepo.findById(itemId).isPresent()) {
            itemRepo.findById(itemId).ifPresent(item -> {
                    item.setReservedQuantity(item.getReservedQuantity() - quantity);
                    itemRepo.save(item);
            });
            return true;
        } else return false;
    }

    @Override
    @Cacheable(value = "availableQuantity", key = "#itemId")
    public int getAvailableQuantity(Long itemId) {
        return itemRepo.findById(itemId)
                .map(item -> item.getTotalQuantity() - item.getReservedQuantity())
                .orElse(-1); // Return -1 if item not found
    }

    @Override
    public int getReservedQuantity(Long itemId) {
        System.out.println(itemRepo.findById(itemId).get().getReservedQuantity());
        return itemRepo.findById(itemId)
                .map(Item::getReservedQuantity)
                .orElse(-1); // Return -1 if item not found
    }
}
