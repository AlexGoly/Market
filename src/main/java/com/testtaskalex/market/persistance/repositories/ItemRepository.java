package com.testtaskalex.market.persistance.repositories;

import com.testtaskalex.market.persistance.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
