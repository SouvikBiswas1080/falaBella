package com.example.falaBella.e_commerce.controller;


import com.example.falaBella.e_commerce.exception.ItemNotFoundException;
import com.example.falaBella.e_commerce.model.Item;
import com.example.falaBella.e_commerce.services.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final ItemService itemService;

    public InventoryController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/supply/{itemId}/{quantity}")
    public ResponseEntity<?> supplyItem(@PathVariable Long itemId,@PathVariable int quantity) {
        if(itemService.addSupply(itemId, quantity)){
            return ResponseEntity.ok("Supply added successfully");
        }else {
            throw new ItemNotFoundException("There are no such item with id: " + itemId);
        }
    }

    @PostMapping("/addItem")
    public ResponseEntity<?> addItem(@RequestBody Item item) {
        item.setReservedQuantity(0);
        if(itemService.addItem(item)){
            return ResponseEntity.ok("Item added successfully");
        }else {
            return new ResponseEntity<>("Item already exists with id: " + item.getItemId(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/reserve/{itemId}/{quantity}")
    public ResponseEntity<?> reserveItem(@PathVariable Long itemId,@PathVariable int quantity) {
        if(itemId == null || quantity <= 0 || itemService.getAvailableQuantity(itemId) < quantity) {
            throw new IllegalArgumentException("Invalid item ID or quantity");
        }
        if(itemService.reserveItem(itemId, quantity)){
            return ResponseEntity.ok("Item reserved successfully");
        }else {
            throw new ItemNotFoundException("There are no such item with id: " + itemId);
        }
    }

    @GetMapping("/available/{itemId}")
    public ResponseEntity<?> getAvailableQuantity(@PathVariable Long itemId) {
        if(itemId == null) {
            throw new IllegalArgumentException("Item ID cannot be null");
        }
        int availableQuantity = itemService.getAvailableQuantity(itemId);
        if(availableQuantity >= 0) {
            return ResponseEntity.ok("Available quantity for item ID " + itemId + ": " + availableQuantity);
        } else {
            throw new ItemNotFoundException("There are no such item with id: " + itemId);
        }
    }

    @PostMapping("/cancel/{itemId}/{quantity}")
    public ResponseEntity<?> cancelReservation(@PathVariable Long itemId,@PathVariable int quantity) {
        if(itemId == null || quantity <= 0 || itemService.getReservedQuantity(itemId)< quantity) {
            throw new IllegalArgumentException("Invalid item ID or quantity");
        }
        if(itemService.cancelReservation(itemId, quantity)){
            return ResponseEntity.ok("Reservation cancelled successfully");
        }else {
            throw new ItemNotFoundException("There are no such item with id: " + itemId);
        }
    }
}
