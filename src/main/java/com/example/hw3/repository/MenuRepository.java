package com.example.hw3.repository;

import com.example.hw3.model.Menu;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MenuRepository {
    private final Map<Long, Menu> database = new HashMap<>();

    public MenuRepository() {
        database.put(1L, new Menu(1L, "Phở Bò", 55000));
    }

    public void save(Menu menu) {
        database.put(menu.getId(), menu);
    }
    
    public Menu findById(Long id) {
        return database.get(id);
    }
}
