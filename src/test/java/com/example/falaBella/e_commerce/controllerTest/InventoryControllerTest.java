package com.example.falaBella.e_commerce.controllerTest;

import com.example.falaBella.e_commerce.controller.InventoryController;
import com.example.falaBella.e_commerce.services.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class InventoryControllerTest {

    @Mock
    private ItemService itemService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        InventoryController inventoryController = new InventoryController(itemService);
        mockMvc = MockMvcBuilders.standaloneSetup(inventoryController).build();
    }

    @Test
    void testSupplyItem() throws Exception {
        Long itemId = 1L;
        int quantity = 10;

        when(itemService.addSupply(itemId, quantity)).thenReturn(true);

        mockMvc.perform(post("/inventory/supply/{itemId}/{quantity}", itemId, quantity))
                .andExpect(status().isOk())
                .andExpect(content().string("Supply added successfully"));

        verify(itemService, times(1)).addSupply(itemId, quantity);
    }


    @Test
    void testReserveItem() throws Exception {
        Long itemId = 1L;
        int quantity = 5;

        when(itemService.getAvailableQuantity(itemId)).thenReturn(10);
        when(itemService.reserveItem(itemId, quantity)).thenReturn(true);

        mockMvc.perform(post("/inventory/reserve/{itemId}/{quantity}", itemId, quantity))
                .andExpect(status().isOk())
                .andExpect(content().string("Item reserved successfully"));

        verify(itemService, times(1)).reserveItem(itemId, quantity);
    }

    @Test
    void testGetAvailableQuantity() throws Exception {
        Long itemId = 1L;

        when(itemService.getAvailableQuantity(itemId)).thenReturn(40);

        mockMvc.perform(get("/inventory/available/{itemId}", itemId))
                .andExpect(status().isOk())
                .andExpect(content().string("Available quantity for item ID 1: 40"));

        verify(itemService, times(1)).getAvailableQuantity(itemId);
    }

    @Test
    void testCancelReservation() throws Exception {
        Long itemId = 1L;
        int quantity = 5;

        when(itemService.getReservedQuantity(itemId)).thenReturn(10);
        when(itemService.cancelReservation(itemId, quantity)).thenReturn(true);

        mockMvc.perform(post("/inventory/cancel/{itemId}/{quantity}", itemId, quantity))
                .andExpect(status().isOk())
                .andExpect(content().string("Reservation cancelled successfully"));

        verify(itemService, times(1)).cancelReservation(itemId, quantity);
    }
}