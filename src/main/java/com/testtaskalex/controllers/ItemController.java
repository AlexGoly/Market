package com.testtaskalex.controllers;

import com.testtaskalex.dtos.ItemDto;
import com.testtaskalex.dtos.OrderDto;
import com.testtaskalex.dtos.resources.ItemResource;
import com.testtaskalex.services.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public ResponseEntity<List<ItemDto>> getItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/{itemId}/orders")
    public ResponseEntity<List<OrderDto>> getItemOrders(@PathVariable Long itemId) {
        return itemService.getItemOrders(itemId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> returnItem(@PathVariable Long id) {
        return itemService.getItem(id);
    }


    @PostMapping
    public ResponseEntity<ItemDto> createItem(@Valid @RequestBody ItemResource itemBody) {
        return itemService.createItem(itemBody);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ItemDto> updateItem(@PathVariable Long id,
                                              @Valid @RequestBody ItemResource itemBody) {
        return itemService.updateItem(id, itemBody);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteItem(@PathVariable Long id) {
        return itemService.deleteItem(id);
    }

}
