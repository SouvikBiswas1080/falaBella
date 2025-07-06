package com.example.falaBella.e_commerce.serviceTest;

import com.example.falaBella.e_commerce.model.Item;
import com.example.falaBella.e_commerce.repository.ItemRepo;
import com.example.falaBella.e_commerce.services.ItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ItemServiceImplTest {

    @Mock
    private ItemRepo itemRepo;

    @InjectMocks
    private ItemServiceImpl itemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddSupply() {
        Long itemId = 1L;
        int quantity = 10;
        Item mockItem = new Item(itemId, "Test Item", 50, 0);

        when(itemRepo.findById(itemId)).thenReturn(Optional.of(mockItem));

        boolean result = itemService.addSupply(itemId, quantity);

        assertTrue(result);
        verify(itemRepo, times(1)).save(mockItem);
        assertEquals(60, mockItem.getTotalQuantity());
    }

    @Test
    void testReserveItem() {
        Long itemId = 1L;
        int quantity = 5;
        Item mockItem = new Item(itemId, "Test Item", 50, 0);

        when(itemRepo.findById(itemId)).thenReturn(Optional.of(mockItem));

        boolean result = itemService.reserveItem(itemId, quantity);

        assertTrue(result);
        verify(itemRepo, times(1)).save(mockItem);
        assertEquals(5, mockItem.getReservedQuantity());
    }

    @Test
    void testAddItem() {
        Item newItem = new Item(2L, "New Item", 100, 0);

        when(itemRepo.findById(newItem.getItemId())).thenReturn(Optional.empty());

        boolean result = itemService.addItem(newItem);

        assertTrue(result);
        verify(itemRepo, times(1)).save(newItem);
    }

    @Test
    void testCancelReservation() {
        Long itemId = 1L;
        int quantity = 5;
        Item mockItem = new Item(itemId, "Test Item", 50, 10);

        when(itemRepo.findById(itemId)).thenReturn(Optional.of(mockItem));

        boolean result = itemService.cancelReservation(itemId, quantity);

        assertTrue(result);
        verify(itemRepo, times(1)).save(mockItem);
        assertEquals(5, mockItem.getReservedQuantity());
    }

    @Test
    void testGetAvailableQuantity() {
        Long itemId = 1L;
        Item mockItem = new Item(itemId, "Test Item", 50, 10);

        when(itemRepo.findById(itemId)).thenReturn(Optional.of(mockItem));

        int availableQuantity = itemService.getAvailableQuantity(itemId);

        assertEquals(40, availableQuantity);
        verify(itemRepo, times(1)).findById(itemId);
    }

    @Test
    void testGetReservedQuantity() {
        Long itemId = 1L;
        Item mockItem = new Item(itemId, "Test Item", 50, 10);

        when(itemRepo.findById(itemId)).thenReturn(Optional.of(mockItem));

        int reservedQuantity = itemService.getReservedQuantity(itemId);

        assertEquals(10, reservedQuantity);
    }
}