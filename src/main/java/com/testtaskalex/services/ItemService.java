package com.testtaskalex.services;

import com.testtaskalex.dtos.ItemDto;
import com.testtaskalex.dtos.OrderDto;
import com.testtaskalex.dtos.resources.ItemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ItemService {

    ResponseEntity<ItemDto> getItem(Long id);

    ResponseEntity<List<ItemDto>> getAllItems();

    ResponseEntity<ItemDto> createItem(ItemResource itemResource);

    ResponseEntity<ItemDto> updateItem(Long id, ItemResource itemResource);

    ResponseEntity<HttpStatus> deleteItem(Long id);

    ResponseEntity<List<OrderDto>> getItemOrders(Long itemId);
}
