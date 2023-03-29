package com.testtaskalex.market.controller;

import com.testtaskalex.market.dtos.ItemDto;
import com.testtaskalex.market.persistance.entities.Item;
import com.testtaskalex.market.persistance.repositories.ItemRepository;
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
    ItemRepository itemRepository;
    @Autowired
    ItemService itemService;

    @GetMapping
    public ResponseEntity<List<ItemDto>> getItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> returnItem(@PathVariable Long id) {
        return itemService.getItem(id);
    }


    @PostMapping
    // ToDo: Request body validation
    public ResponseEntity<ItemDto> createItem(@RequestBody Item item) {
        return itemService.createItem(item);
    }


    @PutMapping("/{id}")
    // ToDo: Request body validation
    public ResponseEntity<ItemDto> updateItem(@PathVariable Long id, @RequestBody Item item) {
        return itemService.updateItem(id, item);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteItem(@PathVariable Long id) {
        return itemService.deleteItem(id);
    }

}
