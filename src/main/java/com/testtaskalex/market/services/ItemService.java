package com.testtaskalex.market.services;

import com.testtaskalex.market.dtos.ItemDto;
import com.testtaskalex.market.dtos.ItemResource;
import com.testtaskalex.market.dtos.OrderDto;
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
