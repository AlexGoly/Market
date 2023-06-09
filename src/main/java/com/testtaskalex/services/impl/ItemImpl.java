package com.testtaskalex.services.impl;

import com.testtaskalex.constants.Constants;
import com.testtaskalex.dtos.ItemDto;
import com.testtaskalex.dtos.OrderDto;
import com.testtaskalex.dtos.resources.ItemResource;
import com.testtaskalex.exceptions.ResourceNotFoundException;
import com.testtaskalex.mappers.ItemMapper;
import com.testtaskalex.mappers.OrderMapper;
import com.testtaskalex.persistance.entities.Item;
import com.testtaskalex.persistance.entities.Order;
import com.testtaskalex.persistance.repositories.ItemRepository;
import com.testtaskalex.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ItemImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public ResponseEntity<ItemDto> getItem(Long id) {
        Item item = itemRepository
                .findById(id).orElseThrow(() ->
                        new ResourceNotFoundException(
                                String.format(Constants.NOT_FOUND_ITEM_BY_ID, id)));
        ItemDto itemDto = itemMapper.toDto(item);
        return new ResponseEntity<>(itemDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ItemDto>> getAllItems() {
        List<Item> items = itemRepository.findAll();
        if (items.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<ItemDto> allItems = items.stream()
                .map((Item item) -> itemMapper.toDto(item)
                )
                .collect(Collectors.toList());
        return new ResponseEntity<>(allItems, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ItemDto> createItem(ItemResource itemBody) {
        Item item = new Item();
        item.setPrice(itemBody.getPrice());
        item.setName(itemBody.getName());
        Item savedItem = itemRepository.save(item);
        ItemDto itemDto = itemMapper.toDto(savedItem);
        return new ResponseEntity<>(itemDto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ItemDto> updateItem(Long id, ItemResource itemBody) {
        Item item = itemRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(
                        String.format(Constants.NOT_FOUND_ITEM_BY_ID, id)));

        item.setName(itemBody.getName());
        item.setPrice(itemBody.getPrice());
        Item savedItem = itemRepository.save(item);
        return new ResponseEntity<>(itemMapper.toDto(savedItem), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteItem(Long id) {
        itemRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<List<OrderDto>> getItemOrders(Long itemId) {
        Item item = itemRepository
                .findById(itemId).orElseThrow(() ->
                        new ResourceNotFoundException(
                                String.format(Constants.NOT_FOUND_ITEM_BY_ID, itemId)));
        Set<Order> itemOrders = item.getOrders();
        if (itemOrders.isEmpty()) {
            throw new ResourceNotFoundException("Nobody order item :: " + itemId);
        }
        List<OrderDto> itemOrdersList = itemOrders.stream()
                .map((Order order) -> orderMapper.toDto(order)
                )
                .collect(Collectors.toList());
        return new ResponseEntity<>(itemOrdersList, HttpStatus.OK);

    }
}
