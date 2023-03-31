package com.testtaskalex.market.controller;

import com.testtaskalex.market.dtos.ItemDto;
import com.testtaskalex.market.dtos.ItemResource;
import com.testtaskalex.market.services.ItemService;
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

    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> returnItem(@PathVariable Long id) {
        return itemService.getItem(id);
    }


    @PostMapping
    public ResponseEntity<ItemDto> createItem(@RequestBody ItemResource itemBody) {
        return itemService.createItem(itemBody);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ItemDto> updateItem(@PathVariable Long id, @RequestBody ItemResource itemBody) {
        return itemService.updateItem(id, itemBody);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteItem(@PathVariable Long id) {
        return itemService.deleteItem(id);
    }

}
