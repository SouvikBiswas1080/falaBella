package com.example.falaBella.e_commerce.services;

import com.example.falaBella.e_commerce.model.Item;


public interface ItemService{

    public boolean addSupply(Long itemId, int quantity);

    public boolean reserveItem(Long itemId, int quantity);

    public boolean addItem(Item item);

    public boolean cancelReservation(Long itemId, int quantity);

    public int getAvailableQuantity(Long itemId);

    public int getReservedQuantity(Long itemId);
}
